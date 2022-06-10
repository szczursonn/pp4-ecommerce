import { useState } from "react"
import { useQuery, useQueryClient } from "react-query"
import { useParams } from "react-router-dom"
import API from "../api"
import Price from "./Price"
import styles from './ProductPage.module.scss'
import QuantitySelector from "./QuantitySelector"
import idConverter from '../utils/ProductIdConverter'

const ProductPage = () => {

    const [quantity, setQuantity] = useState(1)
    const [success, setSuccess] = useState(false)
    const [adding, setAdding] = useState(false)
    const { id } = useParams()
    const queryClient = useQueryClient()

    const realId = idConverter.toRealId(id || '')
    
    const { data: product, error, isLoading } = useQuery(`product-${realId}`, async () => {
            const product = await API.getProductById(realId)
            
            return product
        }, {
            staleTime: Infinity
        }
    )

    if (product !== undefined && id !== idConverter.toFakeId(realId, product.name)) {
        window.history.replaceState(null, '', `/products/${idConverter.toFakeId(product.id, product.name)}`)
    }

    const addToCart = async () => {
        setAdding(true)
        setSuccess(false)
        try {
            await API.addProductToCart(realId, quantity)
            setSuccess(true)
            queryClient.invalidateQueries('offer')
        } catch (err) {}
        setAdding(false)
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

export default ProductPage