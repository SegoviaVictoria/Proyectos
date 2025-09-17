import React from 'react';
import { Link } from 'react-router-dom';

const Navbar = () => (
  <nav className="navbar navbar-expand-lg navbar-light bg-light">
    <div className="container-fluid">
      <Link className="navbar-brand" to="/">Libreria Online</Link>
      <div className="navbar-nav">
        <Link className="nav-link" to="/categories">Categorias</Link>
        <Link className="nav-link" to="/products">Productos</Link>
        <Link className="nav-link" to="/customers">Clientes</Link>
        <Link className="nav-link" to="/orders">Pedidos</Link>
      </div>
    </div>
  </nav>
);

export default Navbar;