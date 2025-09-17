import React from 'react';
import { Link } from 'react-router-dom';

const Footer = () => (
    <footer className="bg-light text-dark mt-5 pt-4 pb-4">
        <div className="container">
            <div className="row">
                
                <div className="col-md-4">
                    <h5>Librería Online</h5>
                    <p>Tu tienda local de confianza para libros y más.</p>
                </div>

                <div className="col-md-4">
                    <h5>Contacto</h5>
                    <ul className="list-unstyled">
                        <li><strong>Dirección:</strong> Calle Falsa 123, Ciudad, País</li>
                        <li><strong>Teléfono:</strong> +34 123 456 789</li>
                        <li><strong>Email:</strong> contacto@libreriaonline.com</li>
                    </ul>
                </div>

                <div className="col-md-4">
                    <h5>Enlaces rápidos</h5>
                    <ul className="list-unstyled">
                        <li><Link to="/products" className="text-dark">Productos</Link></li>
                        <li><Link to="/orders" className="text-dark">Pedidos</Link></li>
                    </ul>
                </div>
            </div>
            <div className="text-center mt-3">
                <small>© {new Date().getFullYear()} Librería Online. Todos los derechos reservados.</small>
            </div>
        </div>
    </footer>
);

export default Footer;