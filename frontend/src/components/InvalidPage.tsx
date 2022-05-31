import { Link } from 'react-router-dom'
import './InvalidPage.scss'

const InvalidPage = () => {
    return <div className="InvalidPage">
        <h2 className="giganumber">404</h2>
        <p className='explanation'>There is no page on <code>{window.location.pathname}</code></p>
        <Link className='goback' to={'/'}>Go back home</Link>
    </div>
}

export default InvalidPage