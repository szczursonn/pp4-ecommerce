import styles from './Price.module.scss'

const Price = ({price}: {price: number}) => {

    return <div className={styles.container}>
                <span className={styles.major}>{price.toFixed(2).split('.')[0]+','}</span>
                <span className={styles.minor}>{price.toFixed(2).split('.')[1]} zł</span>
    </div>
}

export default Price