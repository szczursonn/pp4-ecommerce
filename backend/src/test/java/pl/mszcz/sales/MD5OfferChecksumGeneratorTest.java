package pl.mszcz.sales;

import org.junit.jupiter.api.Test;
import pl.mszcz.productcatalog.ProductData;
import pl.mszcz.sales.cart.MD5OfferChecksumGenerator;
import pl.mszcz.sales.cart.Offer;
import pl.mszcz.sales.cart.OfferChecksumGenerator;
import pl.mszcz.sales.cart.OfferItem;
import pl.mszcz.sales.exceptions.CantGenerateOfferChecksumException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MD5OfferChecksumGeneratorTest {

    @Test
    void itAllowsToGenerateChecksumForEmptyOffer() {
        OfferChecksumGenerator gen = thereIsChecksumGenerator();
        Offer offer = thereIsEmptyOffer();

        String checksum = assertDoesNotThrow(()->gen.getChecksum(offer));
        assertNotNull(checksum);
    }

    @Test
    void itAllowsToGenerateChecksumForOfferWithItems() {
        OfferChecksumGenerator gen = thereIsChecksumGenerator();
        Offer offer = thereIsOfferWithItems();

        String checksum = assertDoesNotThrow(()->gen.getChecksum(offer));
        assertNotNull(checksum);
    }

    @Test
    void itGeneratesSameChecksumForSameObject() throws CantGenerateOfferChecksumException {
        OfferChecksumGenerator gen = thereIsChecksumGenerator();
        Offer offer1 = thereIsOfferWithItems();
        Offer offer2 = thereIsOfferWithItems();

        String checksum1 = gen.getChecksum(offer1);
        String checksum2 = gen.getChecksum(offer2);

        assertEquals(checksum1, checksum2);
    }

    @Test
    void itGeneratesDifferentChecksumForDifferentOffers() throws CantGenerateOfferChecksumException {
        OfferChecksumGenerator gen = thereIsChecksumGenerator();
        Offer offer1 = thereIsEmptyOffer();
        Offer offer2 = thereIsOfferWithItems();

        String checksum1 = gen.getChecksum(offer1);
        String checksum2 = gen.getChecksum(offer2);

        assertNotEquals(checksum1, checksum2);
    }

    private OfferChecksumGenerator thereIsChecksumGenerator() {
        return new MD5OfferChecksumGenerator();
    }

    private Offer thereIsEmptyOffer() {
        return new Offer(new ArrayList<>());
    }

    private Offer thereIsOfferWithItems() {
        return new Offer(new ArrayList<OfferItem>(List.of(
                new OfferItem(new ProductData(1L, "lego set 1", BigDecimal.TEN), 2),
                new OfferItem(new ProductData(5L, "giga produkcik", BigDecimal.valueOf(12.53)), 36)
            )
        ));
    }
}
