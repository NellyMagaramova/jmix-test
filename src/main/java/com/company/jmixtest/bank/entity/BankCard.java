package com.company.jmixtest.bank.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;



import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

@JmixEntity
@Entity(name = "bank_BankCard")
@Table(name = "\"BankCard\"")
public class BankCard {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "\"id\"")
    private Long id;
    @Column(name = "\"number\"", nullable = false, unique = true)
    private String number;

    @Column(name = "\"validaty_period\"")
    private LocalDate validatyPeriod;

    @Column(name = "\"status\"")

    private Boolean status;

    @Column(name = "\"balance\"")

    private Long balance;

    @ManyToOne( fetch = FetchType.EAGER)
    @JoinColumn(name = "\"user_id\"", referencedColumnName = "\"id\"")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BankCard bankCard = (BankCard) o;
        return id != null && id.equals(bankCard.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return  number;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNumber() { return number; }
    public void setNumber(String number) { this.number = number; }
    public LocalDate getValidatyPeriod() { return validatyPeriod; }
    public void setValidatyPeriod(LocalDate validatyPeriod) { this.validatyPeriod = validatyPeriod; }
    public Boolean getStatus() { return status; }
    public void setStatus(Boolean status) { this.status = status; }
    public Long getBalance() { return balance; }
    public void setBalance(Long balance) { this.balance = balance; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
}
