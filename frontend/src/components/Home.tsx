import styles from './Home.module.scss'
import ProductCard from './ProductCard';
import { useQuery, useQueryClient } from 'react-query';
import { useEffect } from 'react';
import api from '../api';

const Home = () => {

    const queryClient = useQueryClient()

    const { data: products, error, isLoading } = useQuery('products', api.getProducts, {
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

    const generateNewProduct = async () => {
        try {
            await api.generateNewProduct()
            queryClient.invalidateQueries('products')
        } catch (err) {}
    }

    return <div className={styles.container}>
        <p className={styles.title}>PRODUKTY</p>
        <button className={styles['debug-btn']} onClick={generateNewProduct}>***DEBUG*** generate new product</button>
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
                        <div className={styles.grid}>
                            {products!.map(product=><ProductCard key={`product-${product.id}`} product={product}/>)}
                        </div>
                    }
                </>
        }
    </div>
}

export default Home