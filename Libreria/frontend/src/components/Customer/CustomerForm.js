import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getCustomerById, createCustomer, updateCustomer } from '../../services/customerService';

const CustomerForm = ({ showNotification }) => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [customer, setCustomer] = useState({ firstName: '', lastName: '', email: '', phone: '', active: true });
  const [loading, setLoading] = useState(!!id);

  useEffect(() => {
    const fetchCustomer = async () => {
      if (id) {
        try {
          const response = await getCustomerById(id);
          setCustomer({ ...response.data, active: response.data.active ?? true });
        } catch {
          showNotification('Error al cargar el cliente', 'error');
        } finally {
          setLoading(false);
        }
      }
    };
    fetchCustomer();
  }, [id, showNotification]);

  const handleChange = e => {
    const { name, value } = e.target;
    setCustomer(prev => ({
      ...prev,
      [name]: name === 'active' ? value === 'true' : value
    }));
  };

  const handleSubmit = async e => {
    e.preventDefault();
    try {
      if (id) {
        await updateCustomer(id, customer);
        showNotification('Cliente actualizado con éxito');
      } else {
        await createCustomer(customer);
        showNotification('Cliente creado con éxito');
      }
      navigate('/customers');
    } catch {
      showNotification('Error al guardar el cliente', 'error');
    }
  };

  if (loading) return <div>Cargando...</div>;

  return (
    <div>
      <h2>{id ? 'Editar Cliente' : 'Nuevo Cliente'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Nombre</label>
          <input
            type="text"
            className="form-control"
            name="firstName"
            value={customer.firstName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Apellidos</label>
          <input
            type="text"
            className="form-control"
            name="lastName"
            value={customer.lastName}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Email</label>
          <input
            type="email"
            className="form-control"
            name="email"
            value={customer.email}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Teléfono</label>
          <input
            type="tel"
            className="form-control"
            name="phone"
            value={customer.phone}
            onChange={handleChange}
            required
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Activo</label>
          <select
            className="form-control"
            name="active"
            value={customer.active ? 'true' : 'false'}
            onChange={e => setCustomer(prev => ({ ...prev, active: e.target.value === 'true' }))}
          >
            <option value="true">Sí</option>
            <option value="false">No</option>
          </select>
        </div>
        <button type="submit" className="btn btn-primary">
          {id ? 'Actualizar' : 'Crear'}
        </button>
      </form>
    </div>
  );
};

export default CustomerForm;
