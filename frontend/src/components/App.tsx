import './App.scss';
import { Route, Routes } from 'react-router-dom';
import Home from './Home';
import Navbar from './Navbar';
import { ProductPage } from './ProductPage';
import CartPage from './CartPage';

const App = () => {
  return (
    <div className="App">
      <Navbar />
      <main className='content-container'>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/cart' element={<CartPage />} />
          <Route path='/products/:id' element={<ProductPage />} />
        </Routes>
      </main>
    </div>
  );
}

export default App;
