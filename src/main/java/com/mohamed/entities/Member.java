package com.mohamed.entities;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.quarkus.security.jpa.RolesValue;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Setter
@Getter
@NoArgsConstructor
@DiscriminatorValue(value = "Member")
public class Member extends Account{
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "Europa/Berlin")
    private Date dateOfMembership;
}
