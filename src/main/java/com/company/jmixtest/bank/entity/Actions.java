package com.company.jmixtest.bank.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import java.util.Objects;

@JmixEntity
@Entity(name = "bank_Actions")
@Table(name = "\"Actions\"")
public class Actions {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "\"id\"")
    private Long id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"type_id\"", referencedColumnName = "\"id\"")
    private TypeAction typeAction;

    @Column(name = "\"summ\"")

    private Long summ;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"card_id\"", referencedColumnName = "\"id\"")
    private BankCard bankCard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actions action = (Actions) o;
        return id != null && id.equals(action.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }




    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public TypeAction getTypeAction() { return typeAction; }
    public void setTypeAction(TypeAction typeAction) { this.typeAction = typeAction; }
    public Long getSumm() { return summ; }
    public void setSumm(Long summ) { this.summ = summ; }
    public BankCard getBankCard() { return bankCard; }
    public void setBankCard(BankCard bankCard) { this.bankCard = bankCard; }
}
