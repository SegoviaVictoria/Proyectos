import api from './api';

export const getOrders = () => api.get('/orders');
export const createOrder = (order) => api.post('/orders', order);
export const getOrderById = (id) => api.get(`/orders/${id}`);
export const updateOrder = (id, order) => api.put(`/orders/${id}`, order);
export const deleteOrderDetail = (detailId) => api.delete(`/orderDetails/${detailId}`);
export const deleteOrder = (id) => api.delete(`/orders/${id}`);