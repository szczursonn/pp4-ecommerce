package pl.mszcz.sales.cart;

import pl.mszcz.sales.exceptions.CantGenerateOfferChecksumException;

public interface OfferChecksumGenerator {
    String getChecksum(Offer offer) throws CantGenerateOfferChecksumException;
}
