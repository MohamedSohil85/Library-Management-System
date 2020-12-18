package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;

@Entity
public class Address extends PanacheEntity {
    private String streetAddress;
    private String city;
    private String state;
    private String zipCode;
    private String country;
}
