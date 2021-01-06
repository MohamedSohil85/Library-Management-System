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

@Path("/api")
public class RackEndpoints {
    @Inject
    RackRepository rackRepository;
    @Inject
    BookItemRepository bookItemRepository;

    @Path("/addBookToRack/{bookId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveBookToRack(Rack rack, @PathParam("bookId")Long id){
        return bookItemRepository.findByIdOptional(id).map(bookItem -> {

            Faker faker=new Faker();
            rack.setNumber(5);
            rack.setLocationIdentifier(faker.number().digit());
            rack.getBookItems().add(bookItem);
            bookItem.setPlacedAt(rack);
            rackRepository.persist(rack);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }

}
