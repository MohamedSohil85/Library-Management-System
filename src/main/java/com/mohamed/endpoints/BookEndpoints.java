package com.mohamed.endpoints;

import com.github.javafaker.Faker;
import com.mohamed.entities.Author;
import com.mohamed.entities.BookItem;
import com.mohamed.exceptions.ResourceNotFound;
import com.mohamed.models.BookFormat;
import com.mohamed.models.BookStatus;
import com.mohamed.repositories.AuthorRepository;
import com.mohamed.repositories.BookItemRepository;
import com.mohamed.repositories.BookRepository;
import com.mohamed.repositories.CatalogRepository;
import io.quarkus.panache.common.Parameters;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Path("/api")
public class BookEndpoints {

    @Inject
    BookItemRepository bookItemRepository;
    @Inject
    CatalogRepository catalogRepository;
    @Inject
    BookRepository bookRepository;
    @Inject
    AuthorRepository authorRepository;

    @Path("/saveBookItemByCatalogId/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
   @Transactional

    public Response saveNewBookItem(@PathParam("id")Long id){
        return catalogRepository.findByIdOptional(id).map(catalog -> {
            BookItem bookItem=new BookItem();
            Faker faker=new Faker();
            String publisher=faker.book().publisher();


            String title=faker.book().title();
            bookItem.setBarcode("5455666522");
            bookItem.setTitle(title);
            bookItem.setReferenceOnly(true);
            bookItem.setSubject("IT-Technology");
            bookItem.setIsbn("28938888");
            bookItem.setFormat(BookFormat.EBOOK);
            bookItem.setStatus(BookStatus.AVAILABLE);
            bookItem.setLanguage("ENG");
            bookItem.setNumberOfPages(faker.number().numberBetween(200,500));
            bookItem.setPublisher(publisher);
            bookItem.setPublicationDate(faker.date().between(new Date(1990),new Date(2020)));
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
    @Path("/addAuthorToBookById/{authorId}/Book/{bookId}")
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response saveAuthorToBook(@PathParam("authorId")Long authorId,@PathParam("bookId")Long bookId){
        return bookItemRepository.findByIdOptional(bookId).map(bookItem -> {
            Optional<Author>optionalAuthor=authorRepository.findByIdOptional(authorId);
            Author author=optionalAuthor.get();
            if(optionalAuthor.isPresent()){
                author.getBookList().add(bookItem);
                bookItem.setAuthor(author);
                bookItemRepository.persist(bookItem);
            }
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }

}
