import { useQuery, useQueryClient } from "react-query"
import { Offer, validateOffer } from "../types/Offer"

const CartPage = () => {

    const queryClient = useQueryClient()

    const { data: offer, error, isLoading, isFetching } = useQuery('offer', async () => {
            const res = await fetch('http://localhost:8080/api/sales/offer')
            if (!res.ok) throw new TypeError(res.statusText)
            const data = await res.json()
            if (!validateOffer(data)) throw new Error('Response validation failed')
            return data as Offer
        }, {
            staleTime: Infinity
        }
    )

    return <div className="CartPage">
    <button onClick={()=>queryClient.invalidateQueries('offer')}>reload offer</button>
    {
        isLoading || isFetching ?
            <p>
                loading...
            </p>
        :
            <>
                {error ?
                    <p>
                        'Failed to load products'
                    </p>
                :
                    <div className='products-container'>
                        {offer!.items.map(item=><p key={item.product.id}>{item.product.name} {item.quantity}x{item.product.price?.toFixed(2)}zł</p>)}
                        <span>total: {offer!.total.toFixed(2)}zł</span>
                    </div>
                }
            </>
    }
</div>
}

export default CartPage