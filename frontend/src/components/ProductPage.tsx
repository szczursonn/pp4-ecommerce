import { useState } from "react"
import { useParams } from "react-router-dom"

export const ProductPage = () => {

    const [quantity, setQuantity] = useState(1)
    const { id } = useParams()

    return <div>
        Placeholder, id: {id}
    </div>
}