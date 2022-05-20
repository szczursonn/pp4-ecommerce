export type ProductData = {
    id: number
    name: string
    imageUrl: string | null
    price: number | null
    published: boolean
}

export const validateProduct = (product: any): boolean => {
    if (
        typeof product === 'object' &&
        typeof product.id === 'number' &&
        typeof product.name === 'string' &&
        (typeof product.imageUrl === 'string' || product.imageUrl === null) &&
        (typeof product.price === 'number' || product.price === null) &&
        typeof product.published === 'boolean'
        ) return true
    return false
}