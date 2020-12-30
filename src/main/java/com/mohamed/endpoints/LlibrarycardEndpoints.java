package com.mohamed.endpoints;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.EAN13Writer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.mohamed.entities.LibraryCard;
import com.mohamed.entities.Member;
import com.mohamed.repositories.BookItemRepository;
import com.mohamed.repositories.LibraryCardRepository;
import com.mohamed.repositories.MembershipRepository;
import io.quarkus.panache.common.Parameters;
import lombok.NonNull;

import java.awt.image.BufferedImage;
import java.net.URL;
import java.nio.file.Path;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@javax.ws.rs.Path("/api")
public class LlibrarycardEndpoints {

    @Inject
    LibraryCardRepository libraryCardRepository;
    @Inject
    MembershipRepository membershipRepository;
    @Inject
    BookItemRepository bookItemRepository;


    public String  qrGenerator(@Valid Member data ) throws WriterException, IOException {
        String name=data.getName();

        String qcodePath = "C:\\Users\\Mimo\\Desktop\\LibraryManagementSystem\\src\\main\\resources\\images"+name+"-QRCode.png";
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode("Name of Member:"+name+ "\n"+"User-Token :"+data.getToken()+"\n"+"User-Status :"+data.getStatus()+"\n"+"Date of Membership :"+data.getDateOfMembership()+"\n"
             , BarcodeFormat.QR_CODE, 350, 350);
        Path path = FileSystems.getDefault().getPath(qcodePath);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        return name+ "-QRCode.png";

    }
    @POST
    @javax.ws.rs.Path("/Generate-QRcode-ByMemberId/{id}")
    @Produces("image/png")
    @Transactional
    public Response barcodeGenerator(@PathParam("id")Long id,LibraryCard libraryCard) throws WriterException, IOException {
        return membershipRepository.findByIdOptional(id).map(member -> {
            member.setLibraryCard(libraryCard);
            libraryCard.setMember(member);
            QRCodeWriter qrCodeWriter = new QRCodeWriter();


            BitMatrix bitMatrix = null;
            try {
                bitMatrix = qrCodeWriter.encode("Member :"+member.getName()+"\n"+"User-Token :"+member.getToken()+"\n"+"User-Status :"+member.getStatus()+"\n"+"Date of Membership :"+member.getDateOfMembership()+"\n"+"Library :"+member.getLibrary().getLibraryName(), BarcodeFormat.QR_CODE,350,350);
            } catch (WriterException e) {
                e.printStackTrace();
            }
            ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
            try {
                MatrixToImageWriter.writeToStream(bitMatrix,"PNG",pngOutputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            byte[] pngData = pngOutputStream.toByteArray();

            libraryCard.setImages(pngData);
            libraryCard.setRegistratedAt(new Date());
            libraryCardRepository.persist(libraryCard);

            return Response.ok(pngData).build();


        }).orElse(Response.noContent().build());
    }

    @POST
    @javax.ws.rs.Path("/LibraryCardByMemberId/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response creatLibraryCard(@PathParam("id")Long id,LibraryCard libraryCard) {
        return membershipRepository.findByIdOptional(id).map(member -> {
            member.setLibraryCard(libraryCard);
            libraryCard.setMember(member);

            try {
                String QR_Generator=qrGenerator(member);
                libraryCard.setUrlImage(QR_Generator);
            } catch (WriterException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
             libraryCard.setRegistratedAt(new Date());
           libraryCardRepository.persist(libraryCard);
            return Response.status(Response.Status.CREATED).build();
        }).orElse(Response.noContent().build());
    }
    @GET
    @javax.ws.rs.Path("/QR-CodesById/{id}")
    @Produces("image/png")
   public Optional<LibraryCard>getImagesById(@PathParam("id")Long id){
        return LibraryCard.find("SELECT images FROM LibraryCard WHERE id = :id",Parameters.with("id",id)).singleResultOptional();
    }
    
}
