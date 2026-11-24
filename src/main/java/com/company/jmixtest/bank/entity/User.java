package com.company.jmixtest.bank.entity;

import io.jmix.core.metamodel.annotation.JmixEntity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;


import java.util.Objects;

@JmixEntity
@Entity(name = "bank_User")
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "\"id\"")
    private Long id;
    @Column(name = "\"role_id\"")
    private Long roleId;
    @Column(name = "\"fio\"")
    private String fio;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "\"role_id\"", referencedColumnName = "\"id\"", insertable = false, updatable = false)
    private Roles role;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
                return  fio;
    }



    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRoleId() { return roleId; }
    public void setRoleId(Long roleId) { this.roleId = roleId; }
    public String getFio() {
       return fio;
    }
    public void setFio(String fio) { this.fio = fio; }
    public Roles getRole() { return role; }
    public void setRole(Roles role) { this.role = role; }
}
