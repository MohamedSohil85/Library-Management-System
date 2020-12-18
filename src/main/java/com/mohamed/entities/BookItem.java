package com.mohamed.entities;

import com.mohamed.models.BookFormat;
import com.mohamed.models.BookStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class BookItem extends Book{

    private String barcode;
    private boolean isReferenceOnly;
    private Date borrowed;
    private Date dueDate;
    private BookFormat format;
    private BookStatus status;
    private Date publicationDate;
    @ManyToOne
    private Rack placedAt;

}
