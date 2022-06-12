package pl.mszcz.sales.cart;

import pl.mszcz.sales.exceptions.CantGenerateOfferChecksumException;

import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// https://stackoverflow.com/a/29128896
public class MD5OfferChecksumGenerator implements OfferChecksumGenerator {
    public String getChecksum(Offer offer) throws CantGenerateOfferChecksumException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {

            oos.writeObject(offer);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(baos.toByteArray());

            baos.close();
            oos.close();

            return DatatypeConverter.printHexBinary(digest);
        } catch (IOException | NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new CantGenerateOfferChecksumException();
        }
    }
}
