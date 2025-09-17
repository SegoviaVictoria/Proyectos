import React, { useEffect, useState } from 'react';
import { Link, useParams } from 'react-router-dom';
import { getOrderById, deleteOrderDetail } from '../../services/orderService';

const OrderDetailList = () => {
    const { orderId } = useParams();
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    const handleDelete = async (detailId) => {
        if (!window.confirm('¿Seguro que deseas eliminar este detalle?')) return;
        try {
            await deleteOrderDetail(Number(detailId));
            const response = await getOrderById(Number(orderId));
            const orderData = Array.isArray(response.data) ? response.data[0] : response.data;
            setOrder(orderData);
        } catch (e) {
            setError('Error al eliminar el detalle');
            console.error(e);
        }
    };

    useEffect(() => {
        const fetchOrder = async () => {
            try {
                const response = await getOrderById(orderId);
                const orderData = Array.isArray(response.data) ? response.data[0] : response.data;
                setOrder(orderData);
            } catch (err) {
                setError('Error al cargar el pedido');
            } finally {
                setLoading(false);
            }
        };
        fetchOrder();
    }, [orderId]);

    if (loading) return <p>Cargando pedido...</p>;
    if (error) return <p>{error}</p>;
    if (!order) return <p>No se encontró el pedido</p>;

    return (
        <div>
            <h2>Detalles del Pedido #{order.id}</h2>
            <p><strong>Cliente:</strong> {order.customerName}</p>
            <p><strong>Fecha:</strong> {new Date(order.orderDate).toLocaleString()}</p>

            <table className="table">
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Cantidad</th>
                        <th>Precio Unitario</th>
                        <th>Subtotal</th>
                        <th>Acciones</th>
                    </tr>
                </thead>
                <tbody>
                    {(order.details || []).map((detail) => (
                        <tr key={detail.id}>
                            <td>{detail.productName}</td>
                            <td>{detail.quantity}</td>
                            <td>{detail.price.toFixed(2)} €</td>
                            <td>{(detail.quantity * detail.price).toFixed(2)} €</td>
                            <td>
                                <Link
                                    to={`/orders/${order.id}/details/${detail.id}/edit`}
                                    className="btn btn-sm btn-warning me-2"
                                >
                                    Editar
                                </Link>
                                <button
                                    className="btn btn-sm btn-danger"
                                    onClick={() => handleDelete(detail.id)}
                                >
                                    Eliminar
                                </button>
                            </td>
                        </tr>
                    ))}
                </tbody>
                <tfoot>
                    <tr>
                        <th colSpan="3" style={{ textAlign: 'right' }}>Total:</th>
                        <th>
                            {(order.details || [])
                                .reduce((total, detail) => total + detail.quantity * detail.price, 0)
                                .toFixed(2)} €
                        </th>
                    </tr>
                </tfoot>
            </table>
        </div>
    );
};

export default OrderDetailList;
