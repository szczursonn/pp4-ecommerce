import React, { useState } from "react"
import { useQuery, useQueryClient } from "react-query"
import { useParams } from "react-router-dom"
import { ProductData, validateProduct } from "../types/ProductData"

export const ProductPage = () => {

    const [quantity, setQuantity] = useState(1)
    const { id } = useParams()
    const queryClient = useQueryClient()
    const [success, setSuccess] = useState(false)

    const { data: product, error, isLoading } = useQuery(`product-${id}`, async () => {
            const res = await fetch(`http://localhost:8080/api/products/${id}`)
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (!validateProduct(data)) throw new Error('Response validation failed')
            return data as ProductData
        }, {
            staleTime: Infinity
        }
    )

    const addToCart = () => {
        fetch(`http://localhost:8080/api/sales/offer/${id}?quantity=${quantity}`, {
            method: 'POST'
        })
        .then(()=>{
            setSuccess(true)
            queryClient.invalidateQueries('offer')
        })
        .catch(()=>{})
    }

    const updateQuantity = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.currentTarget.valueAsNumber
        if (value < 1 || value > 99) return
        setQuantity(value)
    }

    return <div>
        <p>Placeholder, id: {id}</p>
        {
            isLoading ? 
                <p>loading...</p>
            :
                <>
                    {
                    error ?
                        <p>Error loading product</p>
                    :
                        <div>
                            <img src={product!.imageUrl!} alt={product!.name}/>
                            <p>{product!.name}</p>
                            <p>{product!.price?.toFixed(2)} zł</p>
                            <input type='number' min={1} max={99} value={quantity} autoComplete='off' onChange={updateQuantity}></input>
                            <button onClick={addToCart}>add to cart</button>
                            {success && <p>✔️</p>}
                        </div>
                    }
                </>
        }
    </div>
}