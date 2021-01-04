package com.mohamed.endpoints;

import com.github.javafaker.Faker;
import com.mohamed.entities.BookItem;
import com.mohamed.exceptions.ResourceNotFound;
import com.mohamed.models.BookFormat;
import com.mohamed.models.BookStatus;
import com.mohamed.repositories.BookItemRepository;
import com.mohamed.repositories.CatalogRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;


public class BookEndpoints {

    @Inject
    BookItemRepository bookItemRepository;
    @Inject
    CatalogRepository catalogRepository;
    
    @Path("/saveBookItemByCatalogId/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional

    public Response saveNewBookItem(@PathParam("id")Long id, @Valid BookItem bookItem){
        return catalogRepository.findByIdOptional(id).map(catalog -> {

            Faker faker=new Faker();
            String publisher=faker.book().publisher();
            String barcode=faker.code().ean13();
            String isbn=faker.code().isbn13();
            String title=faker.book().title();
            bookItem.setBarcode(barcode);
            bookItem.setTitle(title);
            bookItem.setReferenceOnly(true);
            bookItem.setSubject("IT-Technology");
            bookItem.setIsbn(isbn);
            bookItem.setFormat(BookFormat.EBOOK);
            bookItem.setStatus(BookStatus.AVAILABLE);
            bookItem.setLanguage("ENG");
            bookItem.setNumberOfPages(faker.number().numberBetween(200,500));
            bookItem.setPublisher(publisher);
            catalog.getBookList().add(bookItem);
            bookItem.setCatalog(catalog);
            bookItemRepository.persist(bookItem);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }

    //TODO load all stored Books

    @GET
    @Path("/BookItems")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookItem> loadAllBookItems() throws ResourceNotFound {
        List<BookItem>bookItems=bookItemRepository.listAll(Sort.ascending("title"));
        if (bookItems.isEmpty()){
            throw new ResourceNotFound("Object not found !");
        }
        return bookItems;

    }
    //TODO find Books By Search Key
    @GET
    @Path("/findBookBySearch/{keyword}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<BookItem>findBookItemsBySearchkey(@PathParam("keyword")String keyword)throws ResourceNotFound{
        List<BookItem>bookItems=bookItemRepository
                .stream("from BookItem where title like CONCAT(:keyword,'%')", Sort.descending("title"), Parameters.with("keyword",keyword))
                .collect(Collectors.toList());

        if(bookItems.isEmpty()){
            throw new ResourceNotFound("Objects not found !");
        }
        return bookItems;
    }

}
