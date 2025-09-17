import React from 'react';
import { Link } from 'react-router-dom';

const LandingPage = () => {
  return (
    <div
      className="landing-container"
      style={{
        height: '60vh',
        display: 'flex',
        flexDirection: 'column',
        justifyContent: 'center',
        alignItems: 'center',
        padding: '40px',
        textAlign: 'center',
        backgroundColor: '#f8f9fa'
      }}
    >
      <h1>Bienvenido a Librería Online</h1>
      <p>Tu tienda de libros favorita en línea.</p>
      <div style={{ marginTop: '20px' }}>
        <Link to="/products" className="btn btn-secondary">
          Ver Productos
        </Link>
      </div>
    </div>
  );
};

export default LandingPage;
