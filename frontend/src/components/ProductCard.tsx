import './ProductCard.scss'
import { ProductData } from "../types/ProductData"
import { useNavigate } from 'react-router-dom'
import Price from './Price'

const FALLBACK_IMG = '/logo512.png'

export const ProductCard = ({product}: {product: ProductData}) => {

    const navigate = useNavigate()

    return <div className='ProductCard' onClick={()=>navigate(`/products/${product.id}`)}>
        <div className='inner'>
            <div className='image-container'>
                <img className='image' src={product.imageUrl || 'fgdiyhuyfdfuygdfuydsuygusfyj'} alt={product.name} onError={e=>e.currentTarget.src=FALLBACK_IMG}/>
            </div>
            <div className='info-container'>
                <Price price={product.price}/>
                <div className='name'>{product.name}</div>
            </div>
        </div>
    </div>
}
