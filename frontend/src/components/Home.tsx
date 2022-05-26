import './Home.scss'
import { ProductData, validateProduct } from '../types/ProductData';
import { ProductCard } from './ProductCard';
import { useQuery, useQueryClient } from 'react-query';
import { useEffect } from 'react';

const Home = () => {

    const queryClient = useQueryClient()

    const { data: products, error, isLoading } = useQuery('products', async () => {
            const res = await fetch('http://localhost:8080/api/products')
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (!(data instanceof Array) || !data.every(validateProduct)) throw new Error('Response validation failed')
            return data as ProductData[]
        }, {
            staleTime: Infinity
        }
    )
    
    useEffect(()=>{
        if (error) {
            if (error instanceof TypeError) {
                console.error(`Failed to fetch: ${error}`)
            } else if (error instanceof SyntaxError) {
                console.error(`Failed to parse JSON: ${error}`)
            } else {
                console.error(`Failed to validate products`)
            }
        }
    }, [error])

    return <div className="Home">
        <p className='products-title'>PRODUKTY</p>
        {
            isLoading ?
                <p>
                    loading...
                </p>
            :
                <>
                    {error ?
                        <>
                            <p> 
                                Failed to load products
                            </p>
                            <button onClick={()=>queryClient.invalidateQueries('products')}>load products</button>
                        </>
                    :
                        <div className='products-container'>
                            {products!.map(product=><ProductCard key={`product-${product.id}`} product={product}/>)}
                        </div>
                    }
                </>
        }
    </div>
}

export default Home