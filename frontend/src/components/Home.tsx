import { useEffect, useState } from 'react';
import './Home.scss'
import { ProductData, validateProduct } from '../types/ProductData';
import { ProductCard } from './ProductCard';

const Home = () => {

    const [products, setProducts] = useState<ProductData[]>([])
    const [error, setError] = useState<string | null>(null)
    const [loading, setLoading] = useState(false)

    const fetchProducts = async () => {
        setLoading(true)

        try {
            const res = await fetch('http://localhost:8080/api/products')
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (!(data instanceof Array) || !data.every(validateProduct)) throw new Error('Response validation failed')
            setProducts(data)
        } catch (err) {
            if (err instanceof TypeError) {
                console.error(`Failed to fetch: ${err}`)
            } else if (err instanceof SyntaxError) {
                console.error(`Failed to parse JSON: ${err}`)
            } else {
                console.error(`Failed to validate products`)
            }
            setError('Failed to load products')
        }
        
        setLoading(false)
    }

    useEffect(()=>{
        fetchProducts()
    }, [])

    return <div className="Home">
        <button onClick={fetchProducts}>load products</button>
        {
            loading ?
                <p>
                    loading...
                </p>
            :
                <>
                    {error ?
                        <p>
                            {error}
                        </p>
                    :
                        <div className='products-container'>
                            {products.map(product=><ProductCard key={`product-${product.id}`} product={product}/>)}
                        </div>
                    }
                </>
        }
    </div>
}

export default Home