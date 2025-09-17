import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getOrderById, updateOrder } from '../../services/orderService';
import { getCustomers } from '../../services/customerService';

const OrderEdit = ({ showNotification }) => {
    const { orderId } = useParams();
    const navigate = useNavigate();
    const [order, setOrder] = useState(null);
    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [orderRes, customersRes] = await Promise.all([
                    getOrderById(orderId),
                    getCustomers()
                ]);
                setOrder(orderRes.data);
                setCustomers(customersRes.data);
            } catch {
                showNotification('Error al cargar datos', 'error');
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, [orderId, showNotification]);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setOrder(prev => ({
            ...prev,
            [name]: type === 'checkbox' ? checked : value
        }));
    };

    const handleCustomerChange = (e) => {
        setOrder(prev => ({
            ...prev,
            customerId: Number(e.target.value)
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await updateOrder(order.id, {
                ...order,
                customerId: order.customerId || (order.customer && order.customer.id),
                shipped: order.shipped
            });
            showNotification('Pedido actualizado con éxito');
            navigate('/orders');
        } catch {
            showNotification('Error al actualizar el pedido', 'error');
        }
    };

    if (loading || !order) return <div>Cargando...</div>;

    return (
        <div>
            <h2>Editar Pedido #{order.id}</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">Cliente</label>
                    <select
                        className="form-control"
                        value={order.customerId || (order.customer && order.customer.id) || ''}
                        onChange={handleCustomerChange}
                        required
                    >
                        <option value="">Selecciona un cliente</option>
                        {customers.map(c => (
                            <option key={c.id} value={c.id}>
                                {c.firstName} {c.lastName}
                            </option>
                        ))}
                    </select>
                </div>
                <div className="form-check mb-3">
                    <input
                        type="checkbox"
                        className="form-check-input"
                        name="shipped"
                        checked={order.shipped}
                        onChange={handleChange}
                    />
                    <label className="form-check-label">Enviado</label>
                </div>
                <button type="submit" className="btn btn-primary">Guardar Cambios</button>
            </form>
        </div>
    );
};

export default OrderEdit;