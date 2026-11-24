package com.company.jmixtest.bank.service;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.function.Supplier;

@Service
public class BankFormsService {
    private final CardService cards;

    public BankFormsService(CardService cards) {
        this.cards = cards;
    }

    public record Card(String number, String fio, boolean active, BigDecimal balance) { }

    private Card display(CardService.CardInfo card) {
        System.out.println("display()" +  card.user().fio());
        return new Card(card.number(), card.user() == null ? "" : card.user().fio(),
                Boolean.TRUE.equals(card.status()), BigDecimal.valueOf(card.balance()));
    }

    private String normalize(String number) {
        String value = number == null ? "" : number.replaceAll("[\\s-]", "");
        if (!value.matches("[0-9]{1,32}")) {
            throw new BankException("Введите номер карты: от 1 до 32 цифр");
        }
        return value;
    }

    private <T> T perform(Supplier<T> operation) {
        try {
            return operation.get();
        } catch (CardService.CardNotFoundException | IllegalArgumentException error) {
            throw new BankException(error.getMessage());
        } catch (DataAccessException | TransactionException error) {
            throw new BankException("Не удалось выполнить операцию. Повторите попытку.");
        }
    }

    public Card card(String number) {
        return perform(() -> display(cards.findCard(normalize(number))));
    }

    public List<Card> cards() {
        return perform(() -> cards.listCards().stream().map(this::display).toList());
    }

    public Card active(String number, boolean active) {
        return perform(() -> display(active ? cards.unblockCard(normalize(number))
                : cards.blockCard(normalize(number))));
    }

    public void transfer(String from, String to, BigDecimal amount) {
        if (amount == null) {
            throw new BankException("Укажите сумму перевода");
        }
        long value;
        try {
            value = amount.longValueExact();
        } catch (ArithmeticException error) {
            throw new BankException("Сумма должна быть положительным целым числом");
        }
        perform(() -> cards.sendBalance(normalize(from), normalize(to), value));
    }

    public static String money(BigDecimal amount) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.forLanguageTag("ru-RU"));
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        return format.format(amount) + " ₽";
    }

    public static class BankException extends RuntimeException {
        public BankException(String message) {
            super(message);
        }
    }
}
