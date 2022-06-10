import styles from './QuantitySelector.module.scss'

const QuantitySelector = ({quantity, disabled=false, onChange}: {quantity: number, disabled?: boolean, onChange: (quantity: number)=>void}) => {

    const updateQuantity = (e: React.ChangeEvent<HTMLInputElement>) => {
        const value = e.currentTarget.valueAsNumber
        if (isNaN(value) || value < 1 || value > 99) return
        onChange(value)
    }

    return <div>
        <button className={styles.btn} onClick={()=>onChange(quantity-1)} disabled={quantity <= 1}>-</button>
        <input className={styles.textfield} type='number' min={1} max={99} value={quantity} autoComplete='off' disabled={disabled} onChange={updateQuantity}></input>
        <button className={styles.btn} onClick={()=>onChange(quantity+1)} disabled={quantity >= 99}>+</button>
    </div>
}

export default QuantitySelector