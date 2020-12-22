package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.RolesValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role extends PanacheEntity {
    @RolesValue
    private String role;
    @ManyToOne
    private Account account;
}
