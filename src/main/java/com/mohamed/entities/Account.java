package com.mohamed.entities;
import com.mohamed.models.AccountStatus;
import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@UserDefinition
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account extends PanacheEntity {
    private String name;

    private String email;
    private String phone;
    @Username
    private String username;
    @Password
    private String password;
    private AccountStatus status;
    @OneToOne
    private LibraryCard libraryCard;
    @OneToMany
    private List<BookItem>bookItems;
    @OneToOne
    private Address address;
}
