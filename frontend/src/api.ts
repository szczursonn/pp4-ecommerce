import { Offer, validateOffer } from "./types/Offer"
import { PaymentData, validatePaymentData } from "./types/PaymentData"
import { ProductData, validateProduct } from "./types/ProductData"

const BASE_URL = 'http://localhost:8080'

const addProductToCart = async (productId: number | string, quantity: number = 1) => {
    const res = await fetch(`${BASE_URL}/api/sales/offer/${productId}?quantity=${quantity}`, {
        method: 'POST'
    })
    if (!res.ok) throw new Error(res.statusText)
}

const removeProductFromCart = async (productId: number | string) => {
    const res = await fetch(`${BASE_URL}/api/sales/offer/${productId}`, {
        method: 'DELETE'
    })

    if (!res.ok) throw new Error(res.statusText)
}

const getOffer = async (): Promise<Offer> => {
    const res = await fetch(`${BASE_URL}/api/sales/offer`)
    if (!res.ok) throw new Error(res.statusText)
    const data = await res.json()
    if (!validateOffer(data)) throw new Error('Response validation failed')
    return data as Offer
}

const getPaymentData = async (firstName: string, lastName: string, email: string): Promise<PaymentData> => {
    const res = await fetch(`${BASE_URL}/api/sales/purchase`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            firstName,
            lastName,
            email
        })
    })

    if (!res.ok) throw new Error(res.statusText)
    const data = await res.json()
    if (!validatePaymentData(data)) throw new Error('Response validation failed')
    return data as PaymentData
}

const getProducts = async (): Promise<ProductData[]> => {
    const res = await fetch(`${BASE_URL}/api/products`)
    if (!res.ok) throw new TypeError(res.statusText)
    const data = await res.json()
    if (!(data instanceof Array) || !data.every(validateProduct)) throw new Error('Response validation failed')
    return data as ProductData[]
}

const getProductById = async (productId: number | string): Promise<ProductData> => {
    const res = await fetch(`${BASE_URL}/api/products/${productId}`)
    if (!res.ok) throw new TypeError(res.statusText)
    const data = await res.json()
    if (!validateProduct(data)) throw new Error('Response validation failed')
    
    return data as ProductData
}

const generateNewProduct = async () => {
    await fetch(`${BASE_URL}/api/products/generate`, {
        method: 'POST'
    })
}

export default {
    addProductToCart,
    removeProductFromCart,
    getOffer,
    getPaymentData,
    getProducts,
    getProductById,
    generateNewProduct
}