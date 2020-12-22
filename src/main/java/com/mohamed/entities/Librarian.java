package com.mohamed.entities;

import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@DiscriminatorValue(value = "Librarian")
public class Librarian extends Account {
}
