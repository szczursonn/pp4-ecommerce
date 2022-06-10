import { useState } from "react"
import { useQuery } from "react-query"
import { Link } from "react-router-dom"
import API from "../api"
import CartItemCard from "./CartItemCard"
import styles from './CartPage.module.scss'
import Price from "./Price"

const CartPage = () => {

    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const [showPaymentMenu, setShowPaymentMenu] = useState(false)
    const [purchaseDisabled, setPurchaseDisabled] = useState(false)

    const { data: offer, error, isLoading } = useQuery('offer', API.getOffer, {
            staleTime: Infinity
        }
    )

    const makePurchase = async () => {
        try {
            const paymentData = await API.getPaymentData(firstName, lastName, email)
            window.location.href = paymentData.url
        } catch (err) {

        }
    }

    return <div className={styles['page-container']}>
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
                                <div className={styles.container}>
                                    <div className={styles['products-container']}>
                                        {offer!.items.map(item=><CartItemCard key={item.product.id} item={item}/>)}
                                    </div>
                                    <div className={styles["controls-container"]}>
                                        <span className="total">Total: <Price price={offer!.total} /></span>
                                        <button className={styles['buy-btn']} onClick={()=>setShowPaymentMenu(true)} disabled={purchaseDisabled}>BUY</button>
                                        <Link className={styles['continue-shopping']} to='/'>CONTINUE SHOPPING</Link>
                                    </div>
                                </div>
                                {showPaymentMenu && 
                                    <div className={styles['payment-container']}>
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