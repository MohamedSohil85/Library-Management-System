package com.mohamed.repositories;

import com.mohamed.entities.Librarian;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LibrarianRepository implements PanacheRepository<Librarian> {
}
