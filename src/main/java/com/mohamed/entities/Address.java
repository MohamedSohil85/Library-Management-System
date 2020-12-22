package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Entity
@Getter
@Setter
public class Address extends PanacheEntity {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
