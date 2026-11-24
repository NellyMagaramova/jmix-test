package com.company.jmixtest.bank.service;

import com.company.jmixtest.bank.entity.BankCard;
import com.company.jmixtest.bank.repo.BankCardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CardService {
    private final BankCardRepository repository;

    public CardService(BankCardRepository repository) {
        this.repository = repository;
    }

    public record OwnerInfo(Long id, String fio) { }
    public record CardInfo(Long id, String number, LocalDate validatyPeriod,
                           Boolean status, Long balance, OwnerInfo user) { }

    private String normalize(String number) {
        if (number == null || !number.matches("[0-9]{1,32}")) {
            throw new IllegalArgumentException("Номер карты должен содержать от 1 до 32 цифр");
        }
        return number;
    }

    private BankCard find(String number, boolean lock) {
        return repository.findByNumber(number, lock)
                .orElseThrow(() -> new CardNotFoundException("Карта не найдена"));
    }

    private CardInfo info(BankCard card) {
        var owner = card.getUser();
        return new CardInfo(card.getId(), card.getNumber(), card.getValidatyPeriod(),
                card.getStatus(), card.getBalance(),
                owner == null ? null : new OwnerInfo(owner.getId(), owner.getFio()));
    }

    @Transactional(readOnly = true)
    public CardInfo findCard(String number) {
        return info(find(normalize(number), false));
    }

    @Transactional(readOnly = true)
    public List<CardInfo> listCards() {
        return repository.findAll().stream().map(this::info).toList();
    }

    public CardInfo sendBalance(String number1, String number2, long value) {
        String from = normalize(number1);
        String to = normalize(number2);
        if (value <= 0) {
            throw new IllegalArgumentException("Сумма должна быть положительным целым числом");
        }
        if (from.equals(to)) {
            throw new IllegalArgumentException("Выберите две разные карты");
        }
        // Встречные переводы получают блокировки в одинаковом порядке.
        BankCard first = find(from.compareTo(to) < 0 ? from : to, true);
        BankCard second = find(from.compareTo(to) < 0 ? to : from, true);
        BankCard source = first.getNumber().equals(from) ? first : second;
        BankCard target = first.getNumber().equals(to) ? first : second;
        if (!Boolean.TRUE.equals(source.getStatus()) || !Boolean.TRUE.equals(target.getStatus())) {
            throw new IllegalArgumentException("Перевод недоступен для заблокированной карты");
        }
        if (source.getBalance() == null || target.getBalance() == null) {
            throw new IllegalStateException("В базе не задан баланс карты");
        }
        if (source.getBalance() < value) {
            throw new IllegalArgumentException("Недостаточно средств");
        }
        long nextBalance;
        try {
            nextBalance = Math.addExact(target.getBalance(), value);
        } catch (ArithmeticException error) {
            throw new IllegalArgumentException("Превышен максимальный баланс получателя");
        }
        source.setBalance(source.getBalance() - value);
        target.setBalance(nextBalance);
        return info(target);
    }

    public CardInfo blockCard(String number) {
        return setActive(number, false);
    }

    public CardInfo unblockCard(String number) {
        return setActive(number, true);
    }

    private CardInfo setActive(String number, boolean active) {
        BankCard card = find(normalize(number), true);
        card.setStatus(active);
        return info(card);
    }

    public static class CardNotFoundException extends RuntimeException {
        public CardNotFoundException(String message) {
            super(message);
        }
    }
}
