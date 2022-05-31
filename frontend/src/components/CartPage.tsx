import { useState } from "react"
import { useQuery, useQueryClient } from "react-query"
import { Link } from "react-router-dom"
import { CartItem, Offer, validateOffer } from "../types/Offer"
import './CartPage.scss'
import Price from "./Price"
import QuantitySelector from "./QuantitySelector"

const CartItemCard = ({item}: {item: CartItem}) => {

    const queryClient = useQueryClient()

    const removeFromCart = async () => {
        setDisabled(true)

        try {
            const res = await fetch(`http://localhost:8080/api/sales/offer/${item.product.id}`, {
                method: 'DELETE'
            })
            if (res.ok) queryClient.invalidateQueries('offer')
        } catch(e) {
            
        }

        setDisabled(false)
    }

    const [disabled, setDisabled] = useState(false)

    return <div className={`cartitem-card ${disabled ? 'disabled' : ''}`} key={item.product.id}>
        <div className="image-container">
            <img className="image" alt={item.product.name} src={item.product.imageUrl!}/>
        </div>
        <Link className="name" to={`/products/${item.product.id}`}>{item.product.name}</Link>
        <QuantitySelector quantity={item.quantity} onChange={()=>{}}/>
        <p>{item.quantity}</p>
        <div className="price-container">
            <Price price={item.quantity*item.product.price!}/>
            <p className="price-breakdown">{item.quantity}x{item.product.price!.toFixed(2)} zł</p>
        </div>
        
        <button onClick={removeFromCart}>remove</button>
    </div>
}

const CartPage = () => {

    const queryClient = useQueryClient()

    const { data: offer, error, isLoading, isFetching } = useQuery('offer', async () => {
            const res = await fetch('http://localhost:8080/api/sales/offer')
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (!validateOffer(data)) throw new Error('Response validation failed')
            return data as Offer
        }, {
            staleTime: Infinity
        }
    )

    return <div className="CartPage">
    <button onClick={()=>queryClient.invalidateQueries('offer')}>reload offer</button>
    {
        isLoading || isFetching ?
            <p>
                loading...
            </p>
        :
            <>
                {error ?
                    <p>
                        Failed to load products
                    </p>
                :
                    <>
                        {offer!.items.length === 0 ?
                            <p>Cart is empty, go add something!</p>
                        :
                            <div className='products-container'>
                                {offer!.items.map(item=><CartItemCard key={item.product.id} item={item}/>)}
                                <span>total: {offer!.total.toFixed(2)}zł</span>
                            </div>
                        }
                    </>
                }
            </>
    }
</div>
}



export default CartPage