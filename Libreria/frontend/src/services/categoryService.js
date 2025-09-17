import api from './api';

export const getCategories = () => api.get('/categories');
export const createCategory = (category) => api.post('/categories', category);
export const updateCategory = (id, category) => api.put(`/categories/${id}`, category);
export const getCategoryById = (id) => api.get(`/categories/${id}`);
export const deleteCategoryById = (id) => api.delete(`/categories/${id}`);