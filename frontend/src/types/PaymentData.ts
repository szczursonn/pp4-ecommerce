export type PaymentData = {
    paymentId: string,
    purchaseId: number,
    url: string
}

export const validatePaymentData = (p: any): boolean => {
    if (
        typeof p === 'object' &&
        p !== null &&
        typeof p.paymentId === 'string' &&
        typeof p.purchaseId === 'number' &&
        typeof p.url === 'string'
    ) return true
    return false
}