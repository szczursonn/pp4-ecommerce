import './ProductCard.scss'
import { ProductData } from "../types/ProductData"
import { useNavigate } from 'react-router-dom'

const FALLBACK_IMG = '/logo512.png'

export const ProductCard = ({product}: {product: ProductData}) => {

    const navigate = useNavigate()

    return <div className='ProductCard' onClick={()=>navigate(`/products/${product.id}`)}>
        <div className='image-container'>
            <img className='image' src={product.imageUrl!} alt={product.name} onError={e=>e.currentTarget.src=FALLBACK_IMG}/>
        </div>
        <div className='info-container'>
            <div className='price-container'>
                {product.price ?
                <>
                    <span className='major'>{product.price.toFixed(2).split('.')[0]+','}</span>
                    <span className='minor'>{product.price.toFixed(2).split('.')[1]} zł</span>
                </>
                :
                <>
                    <span className='unavailable'>Price Unavailable</span>
                </>
                }
            </div>
            <div className='name'>{product.name}</div>
        </div>
    </div>
}
