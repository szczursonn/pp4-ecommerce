import { useState } from "react"
import { useQuery, useQueryClient } from "react-query"
import { useParams } from "react-router-dom"
import api from "../api"
import Price from "./Price"
import styles from './ProductPage.module.scss'
import QuantitySelector from "./QuantitySelector"

const toRealId = (fakeId: string): string => {
    const x = fakeId.split('-')
    return x[x.length-1]
}
const toFakeId = (id: string, name: string): string => encodeURIComponent(name.toLowerCase().replace(/ /g, '-')) + '-' + id

const ProductPage = () => {

    const [quantity, setQuantity] = useState(1)
    const [success, setSuccess] = useState(false)
    const [adding, setAdding] = useState(false)
    const { id } = useParams()
    const queryClient = useQueryClient()

    const realId = toRealId(id || '')

    const { data: product, error, isLoading } = useQuery(`product-${realId}`, async () => {
            const product = await api.getProductById(realId)
            window.history.replaceState(null, '', `/products/${toFakeId(product.id.toString(), product.name)}`)
            return product
        }, {
            staleTime: Infinity
        }
    )

    const addToCart = async () => {
        setAdding(true)
        setSuccess(false)
        try {
            await api.addProductToCart(realId, quantity)
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