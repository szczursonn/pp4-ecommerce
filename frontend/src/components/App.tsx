import './App.scss';
import { Route, Routes } from 'react-router-dom';
import Home from './Home';
import Navbar from './Navbar';
import Placeholder from './Placeholder';

const App = () => {
  return (
    <div className="App">
      <Navbar />
      <main className='content-container'>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/cart' element={<Placeholder />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
