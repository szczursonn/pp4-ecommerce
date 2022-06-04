import { useState } from "react"
import { useQuery } from "react-query"
import { Link } from "react-router-dom"
import { Offer, validateOffer } from "../types/Offer"
import CartItemCard from "./CartItemCard"
import './CartPage.scss'
import Price from "./Price"

const CartPage = () => {

    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const [showPaymentMenu, setShowPaymentMenu] = useState(false)
    const [purchaseDisabled, setPurchaseDisabled] = useState(false)

    const { data: offer, error, isLoading } = useQuery('offer', async () => {
            const res = await fetch('http://localhost:8080/api/sales/offer')
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (!validateOffer(data)) throw new Error('Response validation failed')
            return data as Offer
        }, {
            staleTime: Infinity
        }
    )

    const makePurchase = async () => {
        try {
            const res = await fetch('http://localhost:8080/api/sales/purchase', {
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
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (typeof data.url === 'string') return  window.location.href = data.url
            else throw new Error('Response validation failed')
        } catch (err) {

        }
    }

    return <div className="CartPage">
    {
        isLoading ?
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
                        :   <>
                                <div className="container-container">
                                    <div className='products-container'>
                                        {offer!.items.map(item=><CartItemCard key={item.product.id} item={item}/>)}
                                    </div>
                                    <div className="controls-container">
                                        <span className="total">Total: <Price price={offer!.total} /></span>
                                        <button className="buy-button" onClick={()=>setShowPaymentMenu(true)} disabled={purchaseDisabled}>BUY</button>
                                        <Link className="continue-shopping" to='/'>CONTINUE SHOPPING</Link>
                                    </div>
                                </div>
                                {showPaymentMenu && 
                                    <div className="payment-container">
                                        <form onSubmit={e=>{e.preventDefault();makePurchase()}}>
                                            <p>First name</p>
                                            <input value={firstName} onChange={e=>setFirstName(e.currentTarget.value)} type='text' />
                                            <p>Last name</p>
                                            <input value={lastName} onChange={e=>setLastName(e.currentTarget.value)} type='text' />
                                            <p>Email</p>
                                            <input value={email} onChange={e=>setEmail(e.currentTarget.value)} type='email' />
                                            <input type='submit' />
                                        </form>
                                    </div>
                                }
                            </>
                        }
                    </>
                }
            </>
    }
</div>
}

export default CartPage