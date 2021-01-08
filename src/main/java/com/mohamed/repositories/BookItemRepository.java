package com.mohamed.repositories;

import com.mohamed.entities.BookItem;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class BookItemRepository implements PanacheRepository<BookItem> {

    public Optional<BookItem> findByTitle(String title) {
    return BookItem.find("title",title).singleResultOptional();
    }
}
