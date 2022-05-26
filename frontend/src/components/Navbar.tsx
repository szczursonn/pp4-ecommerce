import './Navbar.scss'
import { Link, useNavigate } from "react-router-dom"
import shopLogo from '../shoplogo.gif'

const Navbar = () => {

    const navigate = useNavigate()

    return <nav className='Navbar'>
        <img className='shoplogo' src={shopLogo} alt='Shop logo' onClick={()=>navigate('/')} />
        <Link className='cart-link' to='/cart'>🛒</Link>
    </nav>
}

export default Navbar