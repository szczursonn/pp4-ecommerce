import './Price.scss'

const Price = ({price}: {price: number|null}) => {

    return <div className="Price">
        {
            price === null ?
                <span className='unavailable'>Price Unavailable</span>
            :
                <>
                    <span className='major'>{price.toFixed(2).split('.')[0]+','}</span>
                    <span className='minor'>{price.toFixed(2).split('.')[1]} zł</span>
                </>
        }
        
    </div>
}

export default Price