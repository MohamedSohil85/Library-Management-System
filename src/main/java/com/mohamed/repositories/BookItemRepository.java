package com.mohamed.repositories;

import com.mohamed.entities.BookItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class BookItemRepository implements PanacheRepository<BookItem> {
}
