import './App.scss';
import { Route, Routes } from 'react-router-dom';
import Home from './Home';
import Navbar from './Navbar';
import ProductPage from './ProductPage';
import CartPage from './CartPage';
import InvalidPage from './InvalidPage';

const App = () => {
  
  return (
    <div>
      <Navbar />
      <main>
        <Routes>
          <Route path='/' element={<Home />} />
          <Route path='/cart' element={<CartPage />} />
          <Route path='/products/:id' element={<ProductPage />} />
          <Route path='*' element={<InvalidPage />}/>
        </Routes>
      </main>
    </div>
  );
}

export default App;
