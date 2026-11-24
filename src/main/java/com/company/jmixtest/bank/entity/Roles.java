package com.company.jmixtest.bank.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;


import jakarta.persistence.*;

import java.util.Objects;


@JmixEntity
@Entity(name = "bank_Roles")
@Table(name = "\"Roles\"")
public class Roles {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "\"id\"")
    private Long id;

    @Column(name = "\"name\"")

    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Roles role = (Roles) o;
        return id != null && id.equals(role.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return name;
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
