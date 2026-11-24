package com.company.jmixtest.bank.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;


import jakarta.persistence.*;

import java.util.Objects;

@JmixEntity
@Entity(name = "bank_TypeAction")
@Table(name = "\"TypeAction\"")
public class TypeAction {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "\"id\"")
    private Long id;

    @Column(name = "\"denotation\"")

    private String denotation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypeAction category = (TypeAction) o;
        return id != null && id.equals(category.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return denotation;
    }


    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDenotation() { return denotation; }
    public void setDenotation(String denotation) { this.denotation = denotation; }
}
