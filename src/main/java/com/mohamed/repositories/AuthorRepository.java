package com.mohamed.repositories;

import com.mohamed.entities.Author;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthorRepository implements PanacheRepository<Author> {
}
