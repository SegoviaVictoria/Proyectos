import React, { useCallback, useEffect, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { getProducts, deleteProduct } from '../../services/productService';

const ProductList = ({ showNotification }) => {
  const [products, setProducts] = useState([]);
  const [filteredProducts, setFilteredProducts] = useState([]);
  const [searchTerm, setSearchTerm] = useState('');
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  const fetchProducts = useCallback(async () => {
    setLoading(true);
    try {
      const { data } = await getProducts();
      setProducts(data);
      setFilteredProducts(data);
    } catch (error) {
      showNotification('Error al cargar los productos', 'error');
    } finally {
      setLoading(false);
    }
  }, [showNotification]);

  useEffect(() => {
    fetchProducts();
  }, [fetchProducts]);

  const handleDelete = async (id) => {
    if (!window.confirm('¿Estás seguro de que deseas eliminar este producto?')) return;

    try {
      await deleteProduct(id);
      showNotification('Producto eliminado exitosamente', 'success');
      setProducts((prev) => prev.filter((p) => p.id !== id));
      setFilteredProducts((prev) => prev.filter((p) => p.id !== id));
    } catch (error) {
      if (error.response?.status === 409) {
        showNotification('No se puede eliminar: el producto está asociado a una orden', 'warning');
      } else {
        showNotification('Error al eliminar el producto', 'error');
      }
    }
  };

  const handleSearch = (e) => {
    const value = e.target.value;
    setSearchTerm(value);

    if (!value.trim()) {
      setFilteredProducts(products);
      return;
    }

    const lowerCaseValue = value.toLowerCase();
    const isNumber = !isNaN(value);

    const filtered = products.filter((p) =>
      isNumber
        ? p.id === parseInt(value)
        : p.name.toLowerCase().includes(lowerCaseValue)
    );

    setFilteredProducts(filtered);
  };

  if (loading) return <div>Cargando productos...</div>;

  return (
    <div>
      <h2>Productos</h2>

      <div className="mb-3 d-flex align-items-center gap-2">
        <input
          type="text"
          className="form-control"
          placeholder="Buscar por ID o nombre..."
          value={searchTerm}
          onChange={handleSearch}
        />
        <button className="btn btn-secondary" onClick={() => {
          setSearchTerm('');
          setFilteredProducts(products);
        }}>
          Limpiar
        </button>
      </div>

      <Link to="/products/new" className="btn btn-primary mb-3">
        Nuevo Producto
      </Link>

      <table className="table">
        <thead>
          <tr>
            <th>ID</th>
            <th>Nombre</th>
            <th>Precio</th>
            <th>Categoría</th>
            <th>Acciones</th>
          </tr>
        </thead>
        <tbody>
          {filteredProducts.length === 0 ? (
            <tr>
              <td colSpan="5">No hay productos que coincidan con la búsqueda</td>
            </tr>
          ) : (
            filteredProducts.map((product) => (
              <tr key={product.id}>
                <td>{product.id}</td>
                <td>{product.name}</td>
                <td>{product.price.toFixed(2)} €</td>
                <td>{product.categoryName || 'Sin categoría'}</td>
                <td>
                  <button
                    className="btn btn-sm btn-warning me-2"
                    onClick={() => navigate(`/products/edit/${product.id}`)}
                  >
                    Editar
                  </button>
                  <button
                    className="btn btn-sm btn-danger"
                    onClick={() => handleDelete(product.id)}
                  >
                    Eliminar
                  </button>
                </td>
              </tr>
            ))
          )}
        </tbody>
      </table>
    </div>
  );
};

export default ProductList;
