package com.mohamed.endpoints;

import com.mohamed.entities.Catalog;
import com.mohamed.exceptions.ResourceNotFound;
import com.mohamed.repositories.CatalogRepository;
import io.quarkus.panache.common.Sort;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
@Path("/api")
public class CatalogEndpoints {

    @Inject
    CatalogRepository catalogRepository;

    //TODO create Catalog
    @Path("/Catalog")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveNewCatalog(@Valid Catalog catalog){
        catalogRepository.persist(catalog);
        return Response.status(Response.Status.CREATED).build();
    }
    @Path("/Catalogs")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Catalog> loadCatalogs() throws ResourceNotFound{
        List<Catalog>catalogs=catalogRepository.listAll(Sort.descending("catalogName"));
        if (catalogs.isEmpty()){
            throw new ResourceNotFound("Object not found !");
        }
        return catalogs;
    }
}
