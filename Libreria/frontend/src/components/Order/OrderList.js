import React, { useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getOrders, deleteOrder } from '../../services/orderService'; // asegúrate de tener ambos métodos

const OrderList = ({ showNotification }) => {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  /* ─────────────────────────────  Cargar pedidos  ───────────────────────────── */
  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const { data } = await getOrders();      // ← el backend ya devuelve orderDetails
        setOrders(data);
      } catch (err) {
        console.error(err);
        showNotification('Error al cargar los pedidos', 'error');
      } finally {
        setLoading(false);
      }
    };
    fetchOrders();
  }, [showNotification]);

  /* ─────────────────────────────  Eliminar pedido  ──────────────────────────── */
  const handleDelete = async (id) => {
    const confirmado = window.confirm(
      '¿Seguro que quieres eliminar este pedido? Esta acción no se puede deshacer.'
    );
    if (!confirmado) return;

    try {
      await deleteOrder(id);
      setOrders(prev => prev.filter(o => o.id !== id));
      showNotification('Pedido eliminado correctamente', 'success');
    } catch (err) {
      console.error(err);
      showNotification('Error al eliminar el pedido', 'error');
    }
  };

  if (loading) return <div>Cargando pedidos…</div>;

  /* ───────────────────────────────  Render  ─────────────────────────────────── */
  return (
    <div>
      <h2>Pedidos</h2>

      <Link to="/orders/new" className="btn btn-primary mb-3">
        Nuevo pedido
      </Link>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Fecha</th>
            <th>Cliente</th>
            <th>Total</th>
            <th>Enviado</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {orders.map(order => (
            <tr key={order.id}>
              <td>{order.id}</td>
              <td>{new Date(order.orderDate).toLocaleDateString()}</td>
              <td>{order.customerName}</td>
              <td>${order.total.toFixed(2)}</td>

              <td>
                <span
                  style={{
                    display: 'inline-block',
                    width: '12px',
                    height: '12px',
                    borderRadius: '50%',
                    backgroundColor: order.shipped ? '#4caf50' : '#f44336',
                    boxShadow: '0 0 5px rgba(0,0,0,0.1)',
                    verticalAlign: 'middle',
                  }}
                  title={order.shipped ? 'Enviado' : 'No enviado'}
                />
              </td>

              {/* Botones de acción */}
              <td>
                <button
                  className="btn btn-sm btn-info me-2"
                  onClick={() => navigate(`/orders/${order.id}`)}
                >
                  Ver
                </button>

                <button
                  className="btn btn-sm btn-warning me-2"
                  onClick={() => navigate(`/orders/edit/${order.id}`)}
                >
                  Editar
                </button>

                {Array.isArray(order.orderDetails) && order.orderDetails.length === 0 && (
                  <button
                    className="btn btn-sm btn-danger"
                    onClick={() => handleDelete(order.id)}
                  >
                    Eliminar
                  </button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default OrderList;
