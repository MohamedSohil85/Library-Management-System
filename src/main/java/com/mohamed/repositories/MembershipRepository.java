package com.mohamed.repositories;

import com.mohamed.entities.Member;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MembershipRepository implements PanacheRepository<Member> {
}
