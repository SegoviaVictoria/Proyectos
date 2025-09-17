import React, { useState } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Navbar from './components/Shared/Navbar';
import LandingPage from './components/Landing/LandingPage';
import Notification from './components/Shared/Notification';
import CategoryList from './components/Category/CategoryList';
import CategoryCreate from './components/Category/CategoryCreate';
import CategoryEdit from './components/Category/CategoryEdit';
import ProductList from './components/Product/ProductList';
import ProductForm from './components/Product/ProductForm';
import CustomerList from './components/Customer/CustomerList';
import CustomerForm from './components/Customer/CustomerForm';
import OrderList from './components/Order/OrderList';
import OrderForm from './components/Order/OrderForm';
import OrderDetailList from './components/OrderDetail/OrderDetailList';
import OrderDetailForm from './components/OrderDetail/OrderDetailForm';
import OrderEdit from './components/Order/OrderEdit';
import './themes/minty/bootstrap.min.css';

import './App.css';
import Footer from './components/Shared/Footer';

function App() {
  const [notification, setNotification] = useState({ show: false, message: '', type: '' });

  const showNotification = (message, type = 'success') => {
    setNotification({ show: true, message, type });
    setTimeout(() => setNotification({ show: false, message: '', type: '' }), 3000);
  };

  return (
    <Router>
      <div className="App">
        <Navbar />
        {notification.show && <Notification message={notification.message} type={notification.type} />}

        <div className="container mt-4">
          <Routes>
            <Route path="/" element={<LandingPage />} />
            <Route path="/categories" element={<CategoryList showNotification={showNotification} />} />
            <Route path="/categories/new" element={<CategoryCreate showNotification={showNotification} />} />
            <Route path="/categories/edit/:id" element={<CategoryEdit showNotification={showNotification} />} />

            <Route path="/products" element={<ProductList showNotification={showNotification} />} />
            <Route path="/products/new" element={<ProductForm showNotification={showNotification} />} />
            <Route path="/products/edit/:id" element={<ProductForm showNotification={showNotification} />} />

            <Route path="/customers" element={<CustomerList showNotification={showNotification} />} />
            <Route path="/customers/new" element={<CustomerForm showNotification={showNotification} />} />
            <Route path="/customers/edit/:id" element={<CustomerForm showNotification={showNotification} />} />

            <Route path="/orders" element={<OrderList showNotification={showNotification} />} />
            <Route path="/orders/:orderId" element={<OrderDetailList showNotification={showNotification} />} />
            <Route path="/orders/:orderId/details/:detailId/edit" element={<OrderDetailForm showNotification={showNotification} />} />
            <Route path="/orders/new" element={<OrderForm showNotification={showNotification} />} />
            <Route path="/orders/edit/:orderId" element={<OrderEdit showNotification={showNotification} />} />
          </Routes>
        </div>
        <Footer />
      </div>
    </Router>
  );
}

export default App;
