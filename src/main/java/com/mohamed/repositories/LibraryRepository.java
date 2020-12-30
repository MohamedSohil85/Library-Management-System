package com.mohamed.repositories;

import com.mohamed.entities.Library;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LibraryRepository implements PanacheRepository<Library> {
}
