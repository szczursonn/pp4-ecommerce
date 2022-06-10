import { useState } from "react"
import { useQuery, useQueryClient } from "react-query"
import { useNavigate, useParams } from "react-router-dom"
import API from "../api"
import Price from "./Price"
import styles from './ProductPage.module.scss'
import QuantitySelector from "./QuantitySelector"
import idConverter from '../utils/ProductIdConverter'

const ProductPage = () => {

    const [quantity, setQuantity] = useState(1)
    const [adding, setAdding] = useState(false)
    const { id } = useParams()
    const queryClient = useQueryClient()
    const navigate = useNavigate()

    const realId = idConverter.toRealId(id || '')
    
    const { data: product, error, isLoading } = useQuery(`product-${realId}`, () => API.getProductById(realId), {
            staleTime: Infinity
        }
    )

    if (product !== undefined && id !== idConverter.toFakeId(realId, product.name)) {
        window.history.replaceState(null, '', `/products/${idConverter.toFakeId(product.id, product.name)}`)
    }

    const addToCart = async () => {
        setAdding(true)
        try {
            await API.addProductToCart(realId, quantity)
            queryClient.invalidateQueries('offer')
            navigate('/cart')
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
                                </div>
                            </div>
                        </div>
                    }
                </>
        }
    </div>
}

export default ProductPage