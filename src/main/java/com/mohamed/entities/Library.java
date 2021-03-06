package com.mohamed.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
public class Library extends PanacheEntity {

    private String libraryName;
    private String univercity;
    @OneToOne
    private Address address;
    @OneToOne
    @JsonIgnore
    private Member member;
}
