import styles from './ProductCard.module.scss'
import { ProductData } from "../types/ProductData"
import { useNavigate } from 'react-router-dom'
import Price from './Price'
import { useState } from 'react'
import idConverter from '../utils/ProductIdConverter';

const FALLBACK_IMG = '/logo512.png'

const ProductCard = ({product}: {product: ProductData}) => {

    const [imageLoading, setImageLoading] = useState(true)
    const navigate = useNavigate()

    const productUrl = `/products/${idConverter.toFakeId(product.id, product.name)}`

    return <div className={styles.container} onClick={()=>navigate(productUrl)}>
        <div className={styles.inner}>
            <div className={styles['image-container']}>
                <img
                    style={imageLoading ? {display: 'none'} : {}} 
                    src={product.imageUrl || 'fgdiyhuyfdfuygdfuydsuygusfyj'} 
                    alt={product.name}
                    onError={e=>e.currentTarget.src=FALLBACK_IMG}
                    onLoad={()=>setImageLoading(false)}
                />
                <div className={styles.spinner} style={imageLoading ? {} : {display: 'none'}}></div>
            </div>
            <div className={styles['info-container']}>
                <Price price={product.price}/>
                <a className={styles.name} href={productUrl}>{product.name}</a>
            </div>
        </div>
    </div>
}

export default ProductCard