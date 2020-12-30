package com.mohamed.repositories;

import com.mohamed.entities.LibraryCard;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LibraryCardRepository implements PanacheRepository<LibraryCard> {
}
