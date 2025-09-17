import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getCustomers } from '../../services/customerService';

const CustomerList = () => {
  const [customers, setCustomers] = useState([]);

  useEffect(() => {
    const fetchCustomers = async () => {
      const response = await getCustomers();
      setCustomers(response.data);
    };
    fetchCustomers();
  }, []);

  return (
    <div>
      <h2>Clientes</h2>
      <Link to="/customers/new" className="btn btn-primary mb-3">Nuevo Cliente</Link>
      <table className="table">
        <thead>
          <tr>
            <th>Activo</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Teléfono</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {customers.map(customer => (
            <tr key={customer.id}>
              <td>
                <span
                  style={{
                    display: 'inline-block',
                    width: '12px',
                    height: '12px',
                    borderRadius: '50%',
                    backgroundColor: customer.active ? '#4caf50' : '#f44336',
                    boxShadow: '0 0 5px rgba(0,0,0,0.1)',
                    cursor: 'default'
                  }}
                  title={customer.active ? 'Activo' : 'Inactivo'}
                ></span>
              </td>
              <td>{customer.firstName + " " + customer.lastName}</td>
              <td>{customer.email}</td>
              <td>{customer.phone}</td>
              <td>
                <Link to={`/customers/edit/${customer.id}`} className="btn btn-sm btn-warning">
                  Editar
                </Link>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CustomerList;
