import { Link, useSearchParams } from "react-router-dom"
import styles from './PaymentCallbackPage.module.scss'

const PaymentCallbackPage = () => {

    const [searchParams, setSearchParams] = useSearchParams()

    const success = searchParams.get('error') === null

    return <div className={styles['page-container']}>
        {
            success ?
                <h2>nice</h2>
            :
                <h2>not nice :(</h2>
        }
        <Link to={'/'}>Home</Link>
    </div>
}

export default PaymentCallbackPage