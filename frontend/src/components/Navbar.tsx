import styles from './Navbar.module.scss'
import { Link, useNavigate } from "react-router-dom"
import shopLogo from '../shoplogo.gif'

const Navbar = () => {

    const navigate = useNavigate()

    return <nav className={styles.container}>
        <img className={styles['main-logo']} src={shopLogo} alt='Shop logo' onClick={()=>navigate('/')} />
        <Link className={styles.cart} to='/cart'>🛒</Link>
    </nav>
}

export default Navbar