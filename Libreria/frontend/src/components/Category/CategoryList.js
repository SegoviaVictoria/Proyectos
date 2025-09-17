import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { getCategories, deleteCategoryById } from '../../services/categoryService';

const CategoryList = ({ showNotification }) => {
  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCategories = async () => {
      try {
        const response = await getCategories();
        setCategories(response.data);
      } catch (error) {
        showNotification('Error al cargar las categorías', 'error');
      } finally {
        setLoading(false);
      }
    };

    fetchCategories();
  }, [showNotification]);

  const handleDelete = async (id) => {
    if (!window.confirm('¿Estás seguro de eliminar esta categoría?')) return;

    try {
      await deleteCategoryById(id);
      showNotification('Categoría eliminada correctamente', 'success');
      setCategories(categories.filter(category => category.id !== id));
    } catch (error) {
      if (
        error.response &&
        error.response.status === 400 
      ) {
        showNotification('No se puede eliminar la categoría porque tiene libros asociados', 'error');
      } else {
        showNotification('Error al eliminar la categoría', 'error');
      }
    }
  };

  if (loading) return <div>Cargando categorías...</div>;

  return (
    <div>
      <h2>Categorías</h2>
      <Link to="/categories/new" className="btn btn-primary mb-3">
        Nueva Categoría
      </Link>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {categories.map((category) => (
            <tr key={category.id}>
              <td>{category.id}</td>
              <td>{category.name}</td>
              <td>
                <Link
                  to={`/categories/edit/${category.id}`}
                  className="btn btn-warning btn-sm me-2"
                >
                  Editar
                </Link>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleDelete(category.id)}
                >
                  Eliminar
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default CategoryList;
