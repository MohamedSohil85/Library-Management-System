package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;
@Getter
@Setter
@NoArgsConstructor

@Entity
public class Rack extends PanacheEntity {
    private int number;
    private String locationIdentifier;
    @OneToMany
    private List<BookItem> bookItems;
}
