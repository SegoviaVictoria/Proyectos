import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getCategoryById, updateCategory } from '../../services/categoryService';

const CategoryEdit = ({ showNotification }) => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [name, setName] = useState('');
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchCategory = async () => {
      try {
        const response = await getCategoryById(id);
        setName(response.data.name);
      } catch (error) {
        showNotification('Error al cargar la categoría', 'error');
      } finally {
        setLoading(false);
      }
    };
    fetchCategory();
  }, [id, showNotification]);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      await updateCategory(id, { name });
      showNotification('Categoría actualizada correctamente');
      navigate('/categories');
    } catch (error) {
      showNotification('Error al actualizar la categoría', 'error');
    }
  };

  if (loading) return <div>Cargando...</div>;

  return (
    <div>
      <h2>Editar Categoría</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label>Nombre</label>
          <input
            type="text"
            className="form-control"
            value={name}
            onChange={e => setName(e.target.value)}
            required
          />
        </div>
        <button type="submit" className="btn btn-primary">Guardar</button>
      </form>
    </div>
  );
};

export default CategoryEdit;