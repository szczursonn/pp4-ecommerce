import './Navbar.scss'
import { Link, useNavigate } from "react-router-dom"
import shopLogo from '../shoplogo.gif'

const Navbar = () => {

    const navigate = useNavigate()

    return <div className='Navbar'>
        <nav className='row'>
            <img className='shoplogo' src={shopLogo} alt='Shop logo' onClick={()=>navigate('/')} />
            <input type='text'></input>
            <Link to='/cart'>Cart</Link>
        </nav>
        <div className='divider' />
    </div>
}

export default Navbar