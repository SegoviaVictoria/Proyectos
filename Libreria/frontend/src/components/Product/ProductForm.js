import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { getProductById, createProduct, updateProduct } from '../../services/productService';
import { getCategories } from '../../services/categoryService';

const ProductForm = ({ showNotification }) => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [categories, setCategories] = useState([]);
  const [product, setProduct] = useState({
    name: '',
    description: '',
    price: 0,
    stock: 0,
    category: { id: '' },
    updateDate: new Date().toISOString()
  });
  const [loading, setLoading] = useState(!!id);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [categoriesResponse, productResponse] = await Promise.all([
          getCategories(),
          id ? getProductById(id) : Promise.resolve(null)
        ]);

        setCategories(categoriesResponse.data);

        if (productResponse) {
          const category = categoriesResponse.data.find(
            cat => cat.name === productResponse.data.categoryName
          );

          setProduct({
            ...productResponse.data,
            category: category ? { id: category.id } : { id: '' }
          });
        }
      } catch (error) {
        showNotification('Error al cargar los datos', 'error');
      } finally {
        setLoading(false);
      }
    };

    fetchData();
  }, [id, showNotification]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setProduct(prev => ({
      ...prev,
      [name]: name === 'category' ? { id: value } :
        (name === 'price' || name === 'stock') ? Number(value) : value
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!product.category.id) {
      showNotification('Debe seleccionar una categoría', 'error');
      return;
    }

    const selectedCategory = categories.find(cat => cat.id === Number(product.category.id));
    const productData = {
      ...product,
      categoryName: selectedCategory ? selectedCategory.name : ''
    };

    try {
      if (id) {
        await updateProduct(id, productData);
        showNotification('Producto actualizado con éxito');
      } else {
        await createProduct(productData);
        showNotification('Producto creado con éxito');
      }
      navigate('/products');
    } catch (error) {
      showNotification('Error al guardar el producto', 'error');
    }
  };

  if (loading) return <div>Cargando...</div>;

  return (
    <div>
      <h2>{id ? 'Editar Producto' : 'Nuevo Producto'}</h2>
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label className="form-label">Nombre</label>
          <input
            type="text"
            className="form-control"
            name="name"
            value={product.name}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Descripción</label>
          <textarea
            className="form-control"
            name="description"
            value={product.description}
            onChange={handleChange}
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Precio</label>
          <input
            type="number"
            className="form-control"
            name="price"
            value={product.price}
            onChange={handleChange}
            step="0.01"
            min="0"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Stock</label>
          <input
            type="number"
            className="form-control"
            name="stock"
            value={product.stock}
            onChange={handleChange}
            min="0"
            required
          />
        </div>

        <div className="mb-3">
          <label className="form-label">Categoría</label>
          <select
            className="form-select"
            name="category"
            value={product.category.id || ''}
            onChange={handleChange}
            required
          >
            <option value="">Seleccione una categoría</option>
            {categories.map(cat => (
              <option key={cat.id} value={cat.id}>{cat.name}</option>
            ))}
          </select>
        </div>

        <button type="submit" className="btn btn-primary">
          {id ? 'Actualizar' : 'Crear'}
        </button>
      </form>
    </div>
  );
};

export default ProductForm;
