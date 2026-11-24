package com.company.jmixtest.bank.repo;

import com.company.jmixtest.bank.entity.BankCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BankCardRepository {
    @PersistenceContext
    private EntityManager entityManager;

    public Optional<BankCard> findByNumber(String number, boolean lock) {
        var query = entityManager.createQuery(
                "select card from bank_BankCard card where card.number = :number", BankCard.class)
                .setParameter("number", number);
        if (lock) {
            query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
            // Читаем актуальный остаток после получения блокировки, минуя общий кэш.
            query.setHint("eclipselink.refresh", true);
        }
        return query.getResultList().stream().findFirst();
    }

    public List<BankCard> findAll() {
        return entityManager.createQuery(
                "select card from bank_BankCard card left join fetch card.user order by card.number", BankCard.class)
                .getResultList();
    }
}
