package com.mohamed.entities;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@Entity
public class BookLending extends PanacheEntity {
    private Date creationDate;
    private Date dueDate;
    private Date returnDate;
    @OneToOne
    private BookItem bookItemBarcode;
    @OneToOne
    private Member memberId;
}
