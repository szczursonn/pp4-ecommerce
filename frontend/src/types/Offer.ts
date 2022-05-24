import { ProductData, validateProduct } from "./ProductData"

export type Offer = {
    items: CartItem[],
    total: number
}

export type CartItem = {
    product: ProductData
    quantity: number
}

export const validateCartItem = (item: any): boolean => {
    if (
        typeof item === 'object' &&
        typeof item.quantity === 'number' &&
        validateProduct(item.product)
    ) return true
    return false
}

export const validateOffer = (offer: any): boolean => {
    if (
        typeof offer === 'object' &&
        typeof offer.total === 'number' &&
        offer.items instanceof Array &&
        offer.items.every(validateCartItem)
    ) return true
    return false
}