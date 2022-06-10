import { FontAwesomeIcon } from "@fortawesome/react-fontawesome"
import { faTrashAlt } from "@fortawesome/free-solid-svg-icons"
import QuantitySelector from "./QuantitySelector"
import FullScreenModal from "./FullScreenModal"
import { useQueryClient } from "react-query"
import { useEffect, useState } from "react"
import { CartItem } from "../types/Offer"
import { Link } from "react-router-dom"
import Price from "./Price"
import styles from './CartItemCard.module.scss'

const FALLBACK_IMG = '/logo512.png'

const CartItemCard = ({item}: {item: CartItem}) => {

    const [error, setError] = useState<string | null>(null)
    const [disabled, setDisabled] = useState(false)

    const queryClient = useQueryClient()

    /**
     * Disabled is set to false only after offer gets re-fetched in parent (CartItem)
     */
    useEffect(()=>{
        setDisabled(false)
    }, [item.quantity])

    const updateQuantity = async (newQuantity: number) => {
        setDisabled(true)
        try {
            const res = await fetch(`http://localhost:8080/api/sales/offer/${item.product.id}?quantity=${newQuantity}`, {
                method: 'POST'
            })

            if (res.ok) queryClient.invalidateQueries('offer')
            else throw new Error()
        } catch (e) {
            setError(`Error: unable to update quantity of "${item.product.name}"`)
            setDisabled(false)
        }
    }

    const removeFromCart = async () => {
        setDisabled(true)
        try {
            const res = await fetch(`http://localhost:8080/api/sales/offer/${item.product.id}`, {
                method: 'DELETE'
            })
            
            if (res.ok) queryClient.invalidateQueries('offer')
            else throw new Error()
        } catch(e) {
            setError(`Error: Unable to delete "${item.product.name}" from cart`)
            setDisabled(false)
        }
    }

    return <div className={styles.container} key={item.product.id}>
        {disabled && <div className={styles.overlay}><div className={styles.spinner}></div></div>}
        <div className={styles['image-container']}>
            <img alt={item.product.name} src={item.product.imageUrl!} onError={e=>e.currentTarget.src=FALLBACK_IMG}/>
        </div>
        <Link className={styles.name} to={`/products/${item.product.id}`}>{item.product.name}</Link>
        <QuantitySelector quantity={item.quantity} disabled={disabled} onChange={(quantity)=>updateQuantity(quantity)}/>
        <div>
            <Price price={item.quantity*item.product.price}/>
            <p className={styles["price-breakdown"]}>{item.quantity}x{item.product.price.toFixed(2)} zł</p>
        </div>
        
        <button className={styles['remove-btn']} onClick={removeFromCart}>
            <FontAwesomeIcon icon={faTrashAlt}/>
        </button>
        {error && <FullScreenModal message={error} onClose={()=>setError(null)}/>}
    </div>
}

export default CartItemCard