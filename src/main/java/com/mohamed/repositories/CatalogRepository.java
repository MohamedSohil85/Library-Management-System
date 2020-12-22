package com.mohamed.repositories;

import com.mohamed.entities.Catalog;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CatalogRepository implements PanacheRepository<Catalog> {
}
