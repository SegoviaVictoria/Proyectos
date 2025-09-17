import React, { useState } from 'react';

const CategoryForm = ({ onSave, onCancel }) => {
    const [name, setName] = useState('');

    const handleSubmit = (e) => {
        e.preventDefault();
        if (!name.trim()) {
            alert('El nombre es obligatorio');
            return;
        }
        onSave({ name: name.trim() });
};

return (
    <div className="category-form">
        <h2>Nueva Categoría</h2>
        <form onSubmit={handleSubmit}>
            <div className="mb-3">
                <label htmlFor="categoryName" className="form-label">Nombre</label>
                <input
                    type="text"
                    id="categoryName"
                    className="form-control"
                    value={name}
                    onChange={(e) => setName(e.target.value)}
                    placeholder="Introduce el nombre de la categoría"
                    required
                />
            </div>

            <button type="submit" className="btn btn-primary me-2">Guardar</button>
            <button type="button" className="btn btn-secondary" onClick={onCancel}>Cancelar</button>
        </form>
    </div>
);
};

export default CategoryForm;
