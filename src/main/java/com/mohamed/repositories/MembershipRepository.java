package com.mohamed.repositories;

import com.mohamed.entities.Member;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Parameters;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@ApplicationScoped
public class MembershipRepository implements PanacheRepository<Member> {

    public Optional<Member> findByIdOptional(Long id) {
        return Member.findByIdOptional(id);
    }
    public Optional<Member>findByNameAndEmail(String name,String email){
        return Member.find("name = :name and email = :email", Parameters.with("name",name).and("email",email)).singleResultOptional();
    }
    public Optional<Member>findByToken(String token){
        return Member.find("token",token).singleResultOptional();
    }


}
