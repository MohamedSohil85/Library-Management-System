package com.mohamed.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class LibraryCard extends PanacheEntity {

    private String barCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date registratedAt;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date expired;
    @OneToOne
    private Account account;
}
