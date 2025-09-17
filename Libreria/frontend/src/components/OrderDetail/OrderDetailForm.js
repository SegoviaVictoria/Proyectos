import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getOrderById, updateOrder } from '../../services/orderService';

const OrderDetailForm = ({ showNotification }) => {
  const { orderId, detailId } = useParams();
  const navigate = useNavigate();

  const [order, setOrder] = useState(null);
  const [editingDetail, setEditingDetail] = useState({
    productName: '',
    quantity: 0,
    price: 0,
  });
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchOrder = async () => {
      try {
        const response = await getOrderById(orderId);
        const orderData = Array.isArray(response.data) ? response.data[0] : response.data;
        setOrder(orderData);

        const detailToEdit = orderData.details.find(d => String(d.id) === detailId);
        if (detailToEdit) {
          setEditingDetail({
            productName: detailToEdit.productName,
            quantity: detailToEdit.quantity,
            price: detailToEdit.price,
          });
        } else {
          showNotification('Detalle no encontrado', 'error');
          navigate(`/orders/${orderId}`);
        }
      } catch (error) {
        showNotification('Error al cargar el pedido', 'error');
        navigate('/orders');
      } finally {
        setLoading(false);
      }
    };
    fetchOrder();
  }, [orderId, detailId, navigate, showNotification]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setEditingDetail(prev => ({
      ...prev,
      [name]: name === 'quantity' || name === 'price' ? parseFloat(value) : value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const updatedDetails = order.details.map(d =>
        d.id === Number(detailId)
          ? { ...d, ...editingDetail }
          : d
      );

      console.log(editingDetail);
      const updatedOrder = { ...order, details: updatedDetails };
      await updateOrder(orderId, updatedOrder);

      showNotification('Detalle actualizado con éxito');
      navigate(`/orders/${orderId}`);
    } catch (error) {
      showNotification('Error al guardar el detalle', 'error');
    }
  };

  if (loading) return <div>Cargando...</div>;

  return (
    <div>
      <h2>Editar detalle del pedido #{orderId}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Producto</label>
          <input
            type="text"
            className="form-control"
            name="productName"
            value={editingDetail.productName}
            onChange={handleChange}
            required
            style={{ textAlign: 'center' }}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Cantidad</label>
          <input
            type="number"
            className="form-control"
            name="quantity"
            value={editingDetail.quantity}
            onChange={handleChange}
            min="0"
            required
            style={{ textAlign: 'center' }}
          />
        </div>
        <div className="mb-3">
          <label className="form-label">Precio</label>
          <input
            type="number"
            className="form-control"
            step="0.01"
            name="price"
            value={editingDetail.price}
            onChange={handleChange}
            min="0"
            required
            style={{ textAlign: 'center' }}
          />
        </div>
        <button type="submit" className="btn btn-primary">
          Actualizar
        </button>
      </form>
    </div>
  );
};

export default OrderDetailForm;
