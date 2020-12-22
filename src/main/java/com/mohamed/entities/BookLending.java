package com.mohamed.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class BookLending extends PanacheEntity {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date creationDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date dueDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date returnDate;
    @OneToMany
    private List<BookItem> bookItems;
    @OneToOne
    private LibraryCard libraryCard;
}
