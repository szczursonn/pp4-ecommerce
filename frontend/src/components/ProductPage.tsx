import { useState } from "react"
import { useQuery, useQueryClient } from "react-query"
import { useParams } from "react-router-dom"
import { ProductData, validateProduct } from "../types/ProductData"
import Price from "./Price"
import styles from './ProductPage.module.scss'
import QuantitySelector from "./QuantitySelector"

const toRealId = (fakeId: string): string => {
    const x = fakeId.split('-')
    return x[x.length-1]
}
const toFakeId = (id: string, name: string): string => encodeURIComponent(name.toLowerCase().replace(/ /g, '-')) + '-' + id

export const ProductPage = () => {

    const [quantity, setQuantity] = useState(1)
    const [success, setSuccess] = useState(false)
    const [adding, setAdding] = useState(false)
    const { id } = useParams()
    const queryClient = useQueryClient()

    const realId = toRealId(id || '')

    const { data: product, error, isLoading } = useQuery(`product-${realId}`, async () => {
            const res = await fetch(`http://localhost:8080/api/products/${realId}`)
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (!validateProduct(data)) throw new Error('Response validation failed')
            window.history.replaceState(null, '', `/products/${toFakeId(data.id, data.name)}`)
            return data as ProductData
        }, {
            staleTime: Infinity
        }
    )

    const addToCart = () => {
        setAdding(true)
        setSuccess(false)
        fetch(`http://localhost:8080/api/sales/offer/${realId}?quantity=${quantity}`, {
            method: 'POST'
        })
        .then(()=>{
            setSuccess(true)
            queryClient.invalidateQueries('offer')
        })
        .catch(()=>{})
        .finally(()=>{
            setAdding(false)
        })
    }

    return <div className="ProductPage">
        {
            isLoading ? 
                <p>loading...</p>
            :
                <>
                    {
                    error ?
                        <p>Error loading product</p>
                    :
                        <div className={styles.container}>
                            <div className={styles['image-container']}>
                                <img src={product!.imageUrl || ''} alt={product!.name} onError={e=>e.currentTarget.src='/logo512.png'}/>
                            </div>
                            <div className={styles['info-container']}>
                                <p className={styles['product-name']}>{product!.name}</p>
                                <Price price={product!.price}/>
                                <div className="controls">
                                    <QuantitySelector quantity={quantity} onChange={setQuantity}/>
                                    <button className={styles['add-btn']} onClick={addToCart} disabled={adding}>ADD TO CART</button>
                                    {success && <p>✔️</p>}
                                </div>
                            </div>
                        </div>
                    }
                </>
        }
    </div>
}