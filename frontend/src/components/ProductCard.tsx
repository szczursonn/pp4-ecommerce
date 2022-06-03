import './ProductCard.scss'
import { ProductData } from "../types/ProductData"
import { useNavigate } from 'react-router-dom'
import Price from './Price'
import { useState } from 'react'

const FALLBACK_IMG = '/logo512.png'

export const ProductCard = ({product}: {product: ProductData}) => {

    const [imageLoading, setImageLoading] = useState(true)
    const navigate = useNavigate()

    return <div className='ProductCard' onClick={()=>navigate(`/products/${product.id}`)}>
        <div className='inner'>
            <div className='image-container'>
                <img className='image' 
                    style={imageLoading ? {display: 'none'} : {}} 
                    src={product.imageUrl || 'fgdiyhuyfdfuygdfuydsuygusfyj'} 
                    alt={product.name}
                    onError={e=>e.currentTarget.src=FALLBACK_IMG}
                    onLoad={()=>setImageLoading(false)}
                />
                <div className='lds-dual-ring' style={imageLoading ? {} : {display: 'none'}}></div>
            </div>
            <div className='info-container'>
                <Price price={product.price}/>
                <div className='name'>{product.name}</div>
            </div>
        </div>
    </div>
}
