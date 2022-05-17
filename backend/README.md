#pp4-ecommerce
## Java Springboot backend
### api routes:
- GET /api/products - returns list of products
- GET /api/sales/offer - returns current cart
- POST /api/sales/offer/{productId} - add product to cart (overwrites if already in cart)
- DELETE /api/sales/offer/{productId} - removes product from cart
- GET /api/contacts - get list of inquiries
- POST /api/contacts - add new inquiry