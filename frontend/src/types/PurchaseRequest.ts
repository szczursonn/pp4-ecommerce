import { CustomerInfo } from "./CustomerInfo"

export type PurchaseRequest = {
    customerInfo: CustomerInfo,
    checksum: string
}