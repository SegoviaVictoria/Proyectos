import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { createOrder } from '../../services/orderService';
import { getCustomers } from '../../services/customerService';
import { getProducts } from '../../services/productService';

const OrderForm = ({ showNotification }) => {
    const navigate = useNavigate();

    const [unitPrice, setUnitPrice] = useState(0);
    const [customers, setCustomers] = useState([]);
    const [products, setProducts] = useState([]);
    const [order, setOrder] = useState({
        orderDate: new Date().toISOString().split('T')[0],
        shipped: false,
        customer: { id: '' },
        orderDetail: []
    });

    const [detail, setDetail] = useState({
        productId: '',
        quantity: 1,
        price: 0
    });

    useEffect(() => {
        const fetchData = async () => {
            try {
                const customerRes = await getCustomers();
                setCustomers(customerRes.data);

                const productRes = await getProducts();
                setProducts(productRes.data);
            } catch {
                showNotification('Error al cargar clientes o productos', 'error');
            }
        };
        fetchData();
    }, [showNotification]);

    const handleOrderChange = (e) => {
        const { name, value } = e.target;
        setOrder(prev => ({
            ...prev,
            [name]: name === 'shipped' ? e.target.checked : value
        }));
    };

    const handleCustomerChange = (e) => {
        setOrder(prev => ({
            ...prev,
            customer: { id: parseInt(e.target.value) }
        }));
    };

    const handleDetailChange = (e) => {
        const { name, value } = e.target;

        if (name === 'productId') {
            const selectedProduct = products.find(p => p.id === parseInt(value));
            const priceUnit = selectedProduct ? selectedProduct.price : 0;

            setUnitPrice(priceUnit);

            setDetail(prev => ({
                ...prev,
                productId: value,
                price: priceUnit * prev.quantity
            }));
        } else if (name === 'quantity') {
            const quantity = parseInt(value) || 0;
            setDetail(prev => ({
                ...prev,
                quantity: quantity,
                price: unitPrice * quantity
            }));
        }
    };

    const addDetail = () => {
        if (!detail.productId || detail.quantity <= 0 || detail.price <= 0) {
            showNotification('Completa los campos del producto correctamente', 'error');
            return;
        }

        const selectedProduct = products.find(p => p.id === parseInt(detail.productId));
        const newDetail = {
            product: { id: parseInt(detail.productId) },
            quantity: detail.quantity,
            price: detail.price,
            productName: selectedProduct?.name
        };

        setOrder(prev => ({
            ...prev,
            orderDetail: [...prev.orderDetail, newDetail]
        }));

        setDetail({ productId: '', quantity: 1, price: 0 });
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const now = new Date();
        const timeString = now.toTimeString().split(' ')[0]; 
        const fullDateTime = `${order.orderDate}T${timeString}`;

        const orderToSend = {
            orderDate: fullDateTime,
            isShipped: order.shipped, 
            customerId: order.customer.id,
            details: order.orderDetail.map(d => ({
                productId: d.product.id,
                quantity: d.quantity,
                price: d.price
            }))
        };

        try {
            await createOrder(orderToSend);
            showNotification('Pedido creado con éxito');
            navigate('/orders');
        } catch (error) {
            showNotification('Error al crear el pedido', 'error');
        }
    };

    return (
        <div>
            <h2>Nuevo Pedido</h2>
            <form onSubmit={handleSubmit}>
                <div className="mb-3">
                    <label className="form-label">Cliente</label>
                    <select
                        className="form-control"
                        value={order.customer.id}
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

                <div className="mb-3">
                    <label className="form-label">Fecha</label>
                    <input
                        type="date"
                        className="form-control"
                        name="orderDate"
                        value={order.orderDate}
                        onChange={handleOrderChange}
                        required
                    />
                </div>

                <div className="form-check mb-3">
                    <input
                        type="checkbox"
                        className="form-check-input"
                        name="shipped"
                        checked={order.shipped}
                        onChange={handleOrderChange}
                    />
                    <label className="form-check-label">Enviado</label>
                </div>

                <hr />

                <h5>Añadir productos</h5>
                <div className="row mb-3">
                    <div className="col">
                        <select
                            className="form-control"
                            name="productId"
                            value={detail.productId}
                            onChange={handleDetailChange}
                        >
                            <option value="">Selecciona un producto</option>
                            {products.map(p => (
                                <option key={p.id} value={p.id}>{p.name}</option>
                            ))}
                        </select>
                    </div>
                    <div className="col">
                        <input
                            type="number"
                            className="form-control"
                            name="quantity"
                            value={detail.quantity}
                            onChange={handleDetailChange}
                            min="1"
                            placeholder="Cantidad"
                            style={{ textAlign: 'center' }}
                        />
                    </div>
                    <div className="col">
                        <input
                            type="number"
                            className="form-control"
                            name="price"
                            value={detail.price.toFixed(2)}
                            readOnly
                            min="0"
                            step="0.01"
                            placeholder="Precio"
                        />
                    </div>
                    <div className="col-auto">
                        <button type="button" className="btn btn-secondary" onClick={addDetail}>
                            Añadir
                        </button>
                    </div>
                </div>

                {order.orderDetail.length > 0 && (
                    <ul className="list-group mb-3">
                        {order.orderDetail.map((d, idx) => (
                            <li key={idx} className="list-group-item">
                                {d.productName} - Cantidad: {d.quantity} - Precio: {d.price} €
                            </li>
                        ))}
                    </ul>
                )}

                <button type="submit" className="btn btn-primary">Crear Pedido</button>
            </form>
        </div>
    );
};

export default OrderForm;
