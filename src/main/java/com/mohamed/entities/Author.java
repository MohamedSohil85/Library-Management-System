package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
public class Author extends PanacheEntity {
    private String name;
    @OneToMany
    private List<Book>bookList;
}
