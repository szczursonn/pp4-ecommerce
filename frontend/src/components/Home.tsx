import './Home.scss'
import logo from '../logo.svg';

const Home = () => {
    return <div className="Home">
        <img src={logo} className="logo" alt="logo" />
        <img src={logo} className="logo" alt="logo" />
        <img src={logo} className="logo" alt="logo" />
        <p>
          Edit <code>src/App.tsx</code> and save to reload.
        </p>
        <a
          className="link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </div>
}

export default Home