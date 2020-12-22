package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
@Getter
@Setter
@NoArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Book extends PanacheEntity {

    private String isbn;
    private String title;
    private String subject;
    private String publisher;
    private String language;
    private int numberOfPages;
    @ManyToOne
    private Catalog catalog;
    @ManyToOne
    private Author author;
}
