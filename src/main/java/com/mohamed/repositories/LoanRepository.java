package com.mohamed.repositories;

import com.mohamed.entities.BookLending;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoanRepository implements PanacheRepository<BookLending> {

}
