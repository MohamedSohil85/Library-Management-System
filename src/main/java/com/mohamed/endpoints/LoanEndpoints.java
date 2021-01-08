package com.mohamed.endpoints;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.mohamed.entities.BookItem;
import com.mohamed.entities.BookLending;
import com.mohamed.models.BookStatus;
import com.mohamed.models.Constants;
import com.mohamed.repositories.BookItemRepository;
import com.mohamed.repositories.LoanRepository;
import com.mohamed.repositories.MembershipRepository;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

@Path("/api")
public class LoanEndpoints {

    @Inject
    LoanRepository loanRepository;
    @Inject
    BookItemRepository bookItemRepository;
    @Inject
    MembershipRepository membershipRepository;


    @Path("/loan/Book/{title}/Member/{token}")
    @POST
    @Transactional
    @Produces(MediaType.APPLICATION_JSON)
    public Response loanBook(@PathParam("title")String title,@PathParam("token")String  memberToken ){
        return membershipRepository.findByToken(memberToken).map(member -> {
            LocalDate localDate=LocalDate.now();
            BookLending loan=new BookLending();
            Optional<BookItem>bookItemOptional=bookItemRepository.findByTitle(title);
            BookItem bookItem=bookItemOptional.get();
            if(bookItem.getStatus().equals(BookStatus.LOANED) || bookItem.getStatus().equals(BookStatus.LOST))
            {
                return Response.ok("Book is loaned or lost!").build();
            }
            bookItem.setStatus(BookStatus.LOANED);
            bookItem.setBorrowed(new Date());
            bookItem.setReturnDate(localDate.plusDays(Constants.MAX_LENDING_DAYS));
            bookItem.setDueDate(localDate.plusDays(Constants.MAX_LENDING_DAYS));
            loan.setDueDate(localDate.plusDays(Constants.MAX_LENDING_DAYS));
            loan.setReturnDate(localDate.plusDays(Constants.MAX_LENDING_DAYS));
            loan.setMember(member);
            loan.setBookItems(bookItem);
            try {
                qrGenerator(bookItem);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            loanRepository.persist(loan);
            return Response.status(Response.Status.CREATED).build();

        }).orElse(Response.noContent().build());
    }
    public String  qrGenerator(@Valid BookItem bookItem ) throws WriterException, IOException {
        String name= bookItem.getTitle();

        String qcodePath = "C:\\Users\\Mimo\\Desktop\\LibraryManagementSystem\\src\\main\\resources\\images\\"+name+"-QRCode.png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode("title :"+name+ "\n"+"isbn :"+bookItem.getIsbn()+"\n"+"Book-Status :"+bookItem.getStatus()+"\n"+"Date of Publicate :"+bookItem.getPublicationDate()+"\n"+"Borrowed Date :"+bookItem.getBorrowed()
                +"\n"+bookItem.getDueDate()+"\n", BarcodeFormat.QR_CODE, 350, 350);
        java.nio.file.Path path = FileSystems.getDefault().getPath(qcodePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return "\\images\\"+name+ "-QRCode.png";

    }

    @POST
    @Path("/returnBookItem/{title}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response returnBookByTitle(@PathParam("title")String title){
        return bookItemRepository.findByTitle(title).map(bookItem -> {
            LocalDate localDate=LocalDate.now();
            bookItem.setStatus(BookStatus.AVAILABLE);
            bookItem.setReturnDate(localDate);
            bookItem.setDueDate(null);
            bookItem.setBorrowed(null);
            bookItemRepository.persist(bookItem);
            return Response.ok().build();
        }).orElse(Response.noContent().build());
    }


}
