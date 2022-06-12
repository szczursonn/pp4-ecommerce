import { useState } from "react"
import { useQuery, useQueryClient } from "react-query"
import { Link, useNavigate } from "react-router-dom"
import API from "../api"
import CartItemCard from "./CartItemCard"
import styles from './CartPage.module.scss'
import FullScreenModal from "./FullScreenModal"
import Price from "./Price"

const CartPage = () => {

    const [firstName, setFirstName] = useState('')
    const [lastName, setLastName] = useState('')
    const [email, setEmail] = useState('')
    const [selectingPayment, setSelectingPayment] = useState(false)
    const [purchaseDisabled, setPurchaseDisabled] = useState(false)
    const [purchaseError, setPurchaseError] = useState<string | null>(null)

    const queryClient = useQueryClient()

    const { data: offer, error, isLoading } = useQuery('offer', API.getOffer, {
            staleTime: Infinity
        }
    )

    const formValid = (firstName.length>0 && lastName.length>0 && email.length>0)

    const makePurchase = async () => {
        setPurchaseDisabled(true)
        try {
            const paymentData = await API.getPaymentData({
                customerInfo: {firstName, lastName, email},
                checksum: offer!.checksum
            })
            window.location.href = paymentData.url
        } catch (err) {
            if (err instanceof Error && err.message==='checksum mismatch') {
                setPurchaseError('Offer has changed')
                queryClient.invalidateQueries('offer')
                setSelectingPayment(false)
            } else {
                setPurchaseError(`Error: ${err}`)
            }
        }
        setPurchaseDisabled(false)
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
                            {
                                !selectingPayment ?
                                <div className={styles.container}>
                                    <div className={styles['products-container']}>
                                        {offer!.items.map(item=><CartItemCard key={item.product.id} item={item}/>)}
                                    </div>
                                    <div className={styles["controls-container"]}>
                                        <span className="total">Total: <Price price={offer!.total} /></span>
                                        <button className={styles['buy-btn']} onClick={()=>setSelectingPayment(true)}>BUY</button>
                                        <Link className={styles['continue-shopping']} to='/'>CONTINUE SHOPPING</Link>
                                    </div>
                                </div>
                                :
                                <div className={styles['container-container']}>
                                    <button disabled={purchaseDisabled} onClick={()=>setSelectingPayment(false)}>Go back</button>
                                    <div className={styles['customerinfo-container']}>
                                        <form onSubmit={e=>{e.preventDefault()}}>
                                            <p>First name</p>
                                            <input className={styles.textinput} value={firstName} onChange={e=>setFirstName(e.currentTarget.value)} type='text' />
                                            <p>Last name</p>
                                            <input className={styles.textinput} value={lastName} onChange={e=>setLastName(e.currentTarget.value)} type='text' />
                                            <p>Email</p>
                                            <input className={styles.textinput} value={email} onChange={e=>setEmail(e.currentTarget.value)} type='email' />
                                        </form>
                                    </div>
                                    <div className={styles['delivery-container']}>
                                        <p>DELIVERY PLACEHOLDER</p>
                                        <button>inpost paczkomaty</button>
                                        <button>inpost kurier</button>
                                        <button>inpost paczkomaty</button>
                                    </div>
                                    <div className={styles['pay-container']}>
                                        <button className={styles['pay-btn']} onClick={makePurchase} disabled={purchaseDisabled || !formValid}>PAY WITH PAYU</button>
                                    </div>
                                </div>
                            }
                            </>
                        }
                    </>
                }
            </>
    }
    {purchaseError !== null && <FullScreenModal message={purchaseError} onClose={()=>setPurchaseError(null)}/>}
</div>
}

export default CartPage