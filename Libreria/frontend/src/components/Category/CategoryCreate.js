import React from 'react';
import { useNavigate } from 'react-router-dom';
import CategoryForm from './CategoryForm';
import { createCategory } from '../../services/categoryService';

const CategoryCreate = ({ showNotification }) => {
  const navigate = useNavigate();

  const handleSave = async (categoryData) => {
    try {
      await createCategory(categoryData);
      showNotification('Categoría creada con éxito', 'success');
      navigate('/categories');
    } catch (error) {
      showNotification('Error al crear la categoría', 'error');
    }
  };

  const handleCancel = () => {
    navigate('/categories');
  };

  return <CategoryForm onSave={handleSave} onCancel={handleCancel} />;
};

export default CategoryCreate;
