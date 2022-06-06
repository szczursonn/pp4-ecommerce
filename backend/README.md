# pp4-ecommerce
## Java Springboot backend
### secrets
1. Create secrets.yaml in src/main/resources
2. Set values:
   1. payuclientid - OAuth2 Client ID from PayU
   2. payuclientsecret - OAuth2 Client Secret from PayU
   3. payuposid - POS ID from PayU
### api routes:
- GET /api/products - get list of all available products
- GET /api/products/{productId} - get product by id
- GET /api/sales/offer - get cart
- POST /api/sales/offer/{productId}?quantity={quantity} - add product to cart (overwrites if already in cart)
- DELETE /api/sales/offer/{productId} - remove product from cart
- POST /api/sales/purchase (req.body = CustomerInfo) - make a purchase, returns PaymentData, 409 if cart is empty
- GET /api/contacts - get list of inquiries
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