document.addEventListener('DOMContentLoaded', () => {
    // Elementos de búsqueda
    const buscarUsuarios = document.getElementById('buscarUsuarios');
    const buscarEmpresas = document.getElementById('buscarEmpresas');
    const buscarNaves = document.getElementById('buscarNaves');

    let usuariosGlobal = [];
    let empresasGlobal = [];

    // Función para cargar usuarios
    const cargarUsuarios = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/usuarios');
            const usuarios = await response.json();
            const usuariosFiltrados = usuarios.filter(usuario => usuario.rol === 'CLIENTE');
            usuariosGlobal = usuariosFiltrados; // <-- Añade esto
            mostrarUsuarios(usuariosFiltrados);
        } catch (error) {
            console.error('Error al cargar usuarios:', error);
        }
    };

    // Función para cargar empresas
    const cargarEmpresas = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/usuarios');
            const empresas = await response.json();
            const empresasFiltradas = empresas.filter(empresa => empresa.rol === 'EMPRESA');
            empresasGlobal = empresasFiltradas; // <-- Añade esto
            mostrarEmpresas(empresasFiltradas);
        } catch (error) {
            console.error('Error al cargar empresas:', error);
        }
    };

    let viajesGlobal = [];
    // Función para cargar viajes
    const cargarViajes = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/viajes');
            const viajes = await response.json();
            viajesGlobal = viajes;
        } catch (error) {
            console.error('Error al cargar viajes:', error);
        }
    };

    // Mostrar usuarios como cards
    const mostrarUsuarios = (usuarios) => {
        const usuariosList = document.getElementById('usuarios-list');
        usuariosList.innerHTML = ''; // Limpiar la lista

        usuarios.forEach(usuario => {
            const card = document.createElement('div');
            card.classList.add('card');
            card.innerHTML = `
                <h3>${usuario.nombre}</h3>
                <p>Email: ${usuario.email}</p>
                <p>Rol: ${usuario.rol}</p>
                <button class="eliminar-usuario" data-id="${usuario.id}">Eliminar</button>
            `;
            usuariosList.appendChild(card);
        });

        // Añadir eventos a los botones de eliminar
        document.querySelectorAll('.eliminar-usuario').forEach(button => {
            button.addEventListener('click', async (event) => {
                const usuarioId = event.target.getAttribute('data-id');
                await eliminarUsuario(usuarioId);
            });
        });
    };

    // Mostrar empresas como cards
    const mostrarEmpresas = (empresas) => {
        const empresasList = document.getElementById('empresas-list');
        empresasList.innerHTML = '';

        empresas.forEach(empresa => {
            // Usar empresa.viajes directamente
            const viajesEmpresa = empresa.viajes || [];

            const card = document.createElement('div');
            card.classList.add('card');
            card.innerHTML = `
            <h3>${empresa.nombre}</h3>
            <p>Email: ${empresa.email}</p>
            <p>Rol: ${empresa.rol}</p>
            <div class="viajes-empresa">
                <h4>Viajes asociados:</h4>
                <ul>
                    ${viajesEmpresa.map(viaje => `
                        <li>
                            ${viaje.origen} → ${viaje.destino} (${viaje.fechaSalida})
                        </li>
                    `).join('')}
                </ul>
            </div>
        `;
            empresasList.appendChild(card);
            // ...añade los listeners como ya tienes...
        });
    };

    let navesGlobal = []; // Guarda todas las naves para filtrar

    // Mostrar naves como cards
    const mostrarNaves = (naves) => {
        const navesList = document.getElementById('naves-list');
        navesList.innerHTML = ''; // Limpiar la lista

        naves.forEach(nave => {
            const card = document.createElement('div');
            card.classList.add('card');
            card.innerHTML = `
                <h3>${nave.nombre}</h3>
                <p>Modelo: ${nave.modelo}</p>
                <p>Capacidad: ${nave.capacidad}</p>
            `;
            navesList.appendChild(card);
        });
    };

    // Función para cargar naves
    const cargarNaves = async () => {
        try {
            const response = await fetch('http://localhost:8080/api/naves');
            const naves = await response.json();
            navesGlobal = naves;
            mostrarNaves(naves);
        } catch (error) {
            console.error('Error al cargar naves:', error);
        }
    };

    // Filtro en tiempo real
    buscarNaves.addEventListener('input', (e) => {
        const texto = e.target.value.toLowerCase();
        const filtradas = navesGlobal.filter(nave =>
            nave.nombre.toLowerCase().includes(texto) ||
            nave.modelo.toLowerCase().includes(texto)
        );
        mostrarNaves(filtradas);
    });

    // Eliminar usuario
    const eliminarUsuario = async (usuarioId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/usuarios/${usuarioId}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                }
            });

            if (response.ok) {
                alert('Usuario eliminado correctamente');
                cargarUsuarios(); // Recargar la lista de usuarios
            } else {
                alert('Error al eliminar el usuario');
            }
        } catch (error) {
            console.error('Error al eliminar usuario:', error);
        }
    };

    // Filtros de búsqueda
    buscarUsuarios.addEventListener('input', (e) => {
        const texto = e.target.value.toLowerCase();
        const filtrados = usuariosGlobal.filter(usuario =>
            usuario.nombre.toLowerCase().includes(texto) ||
            usuario.email.toLowerCase().includes(texto)
        );
        mostrarUsuarios(filtrados);
    });

    buscarEmpresas.addEventListener('input', (e) => {
        const texto = e.target.value.toLowerCase();
        const filtradas = empresasGlobal.filter(empresa =>
            empresa.nombre.toLowerCase().includes(texto) ||
            empresa.email.toLowerCase().includes(texto)
        );
        mostrarEmpresas(filtradas);
    });

    // Cargar todos los datos al iniciar
    cargarUsuarios();
    cargarViajes().then(() => {
        cargarEmpresas();
    });
    cargarNaves();

    // Cerrar sesión
    const cerrarSesionBtn = document.getElementById('cerrarSesion');
    cerrarSesionBtn.addEventListener('click', () => {
        window.location.href = 'login.html';
    });
});