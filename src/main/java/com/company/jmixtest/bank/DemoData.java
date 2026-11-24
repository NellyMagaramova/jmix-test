package com.company.jmixtest.bank;

import com.company.jmixtest.bank.entity.BankCard;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@ConditionalOnProperty(name = "bank.demo.enabled", havingValue = "true")
public class DemoData implements CommandLineRunner {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        List<BankCard> cards = entityManager.createQuery(
                "select c from bank_BankCard c left join fetch c.user order by c.number",
                BankCard.class).getResultList();

        System.out.println("Загружено карт: " + cards.size());
    }
}
