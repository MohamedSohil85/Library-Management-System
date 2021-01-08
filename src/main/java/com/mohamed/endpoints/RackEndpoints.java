package com.mohamed.endpoints;

import com.github.javafaker.Faker;
import com.mohamed.entities.Rack;
import com.mohamed.repositories.BookItemRepository;
import com.mohamed.repositories.RackRepository;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

@Path("/api")
public class RackEndpoints {
    @Inject
    RackRepository rackRepository;
    @Inject
    BookItemRepository bookItemRepository;

    @Path("/createRack")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response addRack(){
        Faker faker=new Faker();

        Rack rack=new Rack();
        rack.setLocationIdentifier(faker.number().digit());
        rack.setNumber(faker.number().randomDigitNotZero());
        return Response.status(Response.Status.CREATED).build();
    }


    @Path("/addBookToRack/Book/{bookId}/Rack/{rackId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveBookToRack( @PathParam("bookId")Long id,@PathParam("rackId")Long rackId){
        return bookItemRepository.findByIdOptional(id).map(bookItem -> {
            Optional<Rack>rackOptional=rackRepository.findByIdOptional(rackId);
            Rack rack=rackOptional.get();
            if(!rackOptional.isPresent()){

               return Response.noContent().build();
            }
            bookItem.setPlacedAt(rack);
            rack.getBookItems().add(bookItem);
            rackRepository.persist(rack);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }

}
