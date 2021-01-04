package com.mohamed.endpoints;

import com.mohamed.entities.Author;
import com.mohamed.repositories.AuthorRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/api")
public class AuthorEndpoint {


    @Inject
    AuthorRepository authorRepository;

    @Path("/Author")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveNewAuthor(@Valid Author author){
        authorRepository.persist(author);
        return Response.ok(author).build();
    }
    @Path("/Authors")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response loadAllAuthors(){
        List<Author>authors=authorRepository.listAll();
        if(authors.isEmpty()){
            return Response.noContent().build();
        }
        return Response.ok(authors).build();
    }
    @Path("/AuthorByName/{name}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAuthorByName(@PathParam("name")String name){
        List<Author>authors=authorRepository.listAll().stream().filter(author -> author.getName().startsWith(name)).collect(Collectors.toList());
        return Response.ok(authors).build();

    }
}
