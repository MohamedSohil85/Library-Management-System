package com.mohamed.endpoints;


import com.github.javafaker.Faker;
import com.mohamed.entities.*;
import com.mohamed.exceptions.ResourceNotFound;
import com.mohamed.models.AccountStatus;
import com.mohamed.repositories.*;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import io.quarkus.panache.common.Sort;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Date;
import java.util.List;
import java.util.UUID;


@Path("/api")
public class AccountEndpoints {
    @Inject
    LibraryRepository libraryRepository;
    @Inject
    AddressRepository addressRepository;
    @Inject
    MembershipRepository membershipRepository;
    @Inject
    Mailer mailer;
    @Inject
    LibraryCardRepository cardRepository;
    @Inject
    RoleRepository roleRepository;

    @Path("/Members")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("Member")
    public Response loadMembers(){

        List<Member> accountList=membershipRepository.listAll(Sort.descending("name"));
        if (accountList.isEmpty()){
            return Response.noContent().build();
        }
        return Response.ok(accountList).build();
    }
    @Path("/Member")
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Transactional
    public Response createNewMember(){
        Faker faker=new Faker();
        List<Member> accountList=membershipRepository.listAll(Sort.descending("name"));
       Member member=new Member();
        String token=UUID.randomUUID().toString();
        member.setToken(token);
        member.setDateOfMembership(new Date());
        member.setStatus(AccountStatus.INACTIVE);
        member.setName(faker.name().fullName());
        member.setPhone(faker.phoneNumber().phoneNumber());
        member.setUsername(faker.name().username());
        member.setEmail(faker.internet().emailAddress());

       //nd(Mail.withText(member.getEmail(),"Confirmation from System","Please click the following Link to continue registration :\n" +
             //   " http://localhost:8080/api/MemberByToken?token="+member.getToken()));
         membershipRepository.persist(member);
     return Response.ok(member).build();

    }
    @Path("/MemberByToken")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response memberToken(@QueryParam("token")String token,@Valid Member member){
      return   membershipRepository.findByToken(token).map(existMember ->{
        String password= member.getPassword();
        String encodePassword= BcryptUtil.bcryptHash(password);
        existMember.setPassword(encodePassword);
        existMember.setStatus(AccountStatus.ACTIVE);
        membershipRepository.persist(existMember);
        return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }
    @Path("/LibraryByMemberId/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createLibrary(@Valid Library library,@PathParam("id")Long id){
       return membershipRepository.findByIdOptional(id).map(member -> {
            library.setMember(member);
            member.setLibrary(library);
            libraryRepository.persist(library);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());

    }
    @Path("/AddressByMemberId/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createAddress( @PathParam("id")Long id){
        return membershipRepository.findByIdOptional(id).map(member -> {
            Address address=new Address();
            Faker faker= new Faker();
            address.setCity(faker.address().city());
            address.setCountry(faker.address().country());
            address.setState(faker.address().state());
            address.setZipCode(faker.address().zipCode());
            address.setStreetAddress(faker.address().streetAddress());
            member.setAddress(address);
            addressRepository.persist(address);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());

    }
    @Path("/RoleByMemberId/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response saveRole(@PathParam("id")Long id,@Valid Role role){
        return membershipRepository.findByIdOptional(id).map(member -> {
            role.setAccount(member);
            member.getRoles().add(role);
            roleRepository.persist(role);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }
    @Path("/LibraryAddress/{id}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Library saveAddressById(@PathParam("id")Long id, @Valid Address address) throws ResourceNotFound {
        Library library= libraryRepository.findByIdOptional(id).stream().findAny().orElseThrow(() -> new ResourceNotFound("Object not found"));
        library.setAddress(address);
        addressRepository.persist(address);
        return library;

    }
    @Path("/DeleteAllMemebers")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteAllMemebers() throws ResourceNotFound{
        List<Member> accountList=membershipRepository.listAll(Sort.descending("name"));

        if(accountList.isEmpty()){
            throw new ResourceNotFound("No Object !");
        }
        membershipRepository.deleteAll();
        return Response.ok().build();
    }


}
