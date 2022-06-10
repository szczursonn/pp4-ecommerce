import { Link } from 'react-router-dom'
import styles from './InvalidPage.module.scss'

const InvalidPage = () => {
    return <div>
        <h2 className={styles.giganumber}>404</h2>
        <p className={styles.explanation}>There is no page on <code>{window.location.pathname}</code></p>
        <Link className={styles.goback} to={'/'}>Go back home</Link>
    </div>
}

export default InvalidPage