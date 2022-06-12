import { Offer, validateOffer } from "./Offer"

export type OfferResponse = {
    offer: Offer,
    checksum: string
}

export const validateOfferResponse = (o: any) => {
    if (
        typeof o === 'object' &&
        o !== null &&
        validateOffer(o.offer, true) &&
        typeof o.checksum === 'string'
    ) return true
    return false
}