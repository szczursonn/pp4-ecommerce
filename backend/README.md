# pp4-ecommerce
## Java Springboot backend
### secrets
1. Create secrets.yaml in src/main/resources
2. Set values:
   1. ecommerce.clientid - OAuth2 Client ID from PayU
   2. ecommerce.clientsecret - OAuth2 Client Secret from PayU
   3. ecommerce.posid - POS ID from PayU
   4. ecommerce.redirecturi - where to redirect after payment is done
#### Sample secrets.yaml
```
ecommerce:
  client-id: "123456"
  client-secret: "d9a4536ba53v4f51605366a5d3f47"
  pos-id: "654321"
  redirect-uri: "http://localhost:3000/paymentCallback"
```
### api routes:
- GET /api/products - get list of all available products, returns ProductData[]
- GET /api/products/{productId} - get product by id, returns ProductData
- POST /api/products/generate - generate mock product
- GET /api/sales/offer - get cart, returns OfferResponse
- POST /api/sales/offer/{productId}?quantity={quantity} - add product to cart (overwrites if already in cart), quantity is optional (defaults to 1)
- DELETE /api/sales/offer/{productId} - remove product from cart
- POST /api/sales/purchase (req.body = PurchaseRequest) - make a purchase, returns PaymentData, 400 if cart is empty, 409 if offer checksum is invalid
- GET /api/contacts - get list of inquiries, returns Inquiry[]
- POST /api/contacts (req.body = Inquiry) - add a new inquiry

### json entities:
- ProductData  
```
{
  id: number
  name: string
  imageUrl: string | null
  price: number
  archived: boolean
}
```
- OfferResponse
```
{
	offer: Offer,
	checksum: string
}
```
- Offer  
```
{
    items: CartItem[],
    total: number
}
```
- CartItem  
```
{
    product: ProductData
    quantity: number
}
```
- PurchaseRequest
```
{
	customerInfo: CustomerInfo,
	checksum: string
}
```
- CustomerInfo  
```
{
    firstName: string,
    lastName: string,
    email: string
}
```
- PaymentData  
```
{
    paymentId: string,
    purchaseId: number,
    url: string
}
```
- Inquiry
```
{
    id: number,
    name: string,
    email: string,
    subject: string,
    description: string
}
```
