import styles from './Home.module.scss'
import ProductCard from './ProductCard';
import { useQuery, useQueryClient } from 'react-query';
import { useEffect } from 'react';
import API from '../api';

const Home = () => {

    const queryClient = useQueryClient()

    const { data: products, error, isLoading } = useQuery('products', API.getProducts, {
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

    const generateXNewProducts = async (x: number) => {
        const promises: Promise<any>[] = []
        for (let i=0;i<x;i++) {
            promises.push(API.generateNewProduct())
        }

        await Promise.allSettled(promises)
        queryClient.invalidateQueries('products')
    }

    return <div className={styles.container}>
        <p className={styles.title}>PRODUKTY</p>
        <div className={styles['debug-btn-container']}>
            <button className={styles['debug-btn']} onClick={()=>generateXNewProducts(1)}>***DEBUG*** generate new product</button>
            <button className={styles['debug-btn']} onClick={()=>generateXNewProducts(5)}>X5</button>
            <button className={styles['debug-btn']} onClick={()=>generateXNewProducts(10)}>X10</button>
        </div>
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