package com.mohamed.endpoints;


import com.mohamed.repositories.AccountRepository;
import com.mohamed.repositories.LibrarianRepository;
import com.mohamed.repositories.MembershipRepository;
import lombok.Getter;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class AccountEndpoints {

    @Inject
    LibrarianRepository librarianRepository;
    @Inject
    MembershipRepository membershipRepository;

    @Path("/Members")
    @GET
    @RolesAllowed("Librerian")
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadMembers(){

    }

}
