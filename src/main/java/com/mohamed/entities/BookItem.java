package com.mohamed.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mohamed.models.BookFormat;
import com.mohamed.models.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DiscriminatorValue(value = "BookItem")
public class BookItem extends Book{

    private String barcode;
    private boolean isReferenceOnly;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date borrowed;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private LocalDate dueDate;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private LocalDate returnDate;
    @Enumerated(EnumType.STRING)
    private BookFormat format;
    @Enumerated(EnumType.STRING)
    private BookStatus status;
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date publicationDate;
    @ManyToOne
    private Rack placedAt;

}
