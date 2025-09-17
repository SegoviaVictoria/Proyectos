document.addEventListener('DOMContentLoaded', () => {

    // Botones de navegación
    const viewViajesButton = document.getElementById('view-viajes');
    const viewReservasButton = document.getElementById('view-reservas');
    const viewFavoritosButton = document.getElementById('view-favoritos');

    // Secciones
    const viajesSection = document.getElementById('viajes-section');
    const reservasSection = document.getElementById('reservas-section');
    const favoritosSection = document.getElementById('favoritos-section');

    // Botones de busqueda
    const buscarBtn = document.getElementById('buscar-btn');
    const origenInput = document.getElementById('origen');
    const destinoInput = document.getElementById('destino');
    const fechaSalidaInput = document.getElementById('fecha-salida');
    const ordenarSelect = document.getElementById('ordenar');
    const viajesList = document.getElementById('viajes-list');

    // Llave de acceso a Unsplash
    const UNSPLASH_ACCESS_KEY = 'or7C_kF8WdRjesLT3ufW5E_8q2D5MDzaMq0mDsl-wmw';

    buscarBtn.addEventListener('click', () => {
        const filtros = {
            origen: origenInput.value.trim(),
            destino: destinoInput.value.trim(),
            fechaSalida: fechaSalidaInput.value,
            ordenar: ordenarSelect.value
        };
        // Llamar a la función con los filtros
        cargarViajesConImagenes(filtros);
    });

    /// Obtener múltiples imágenes de la NASA (variadas)
    const obtenerImagenesDeUnsplash = async () => {
        const imagenes = [];
        const query = 'galaxy';
        const perPage = 15;

        try {
            const response = await fetch(`https://api.unsplash.com/search/photos?query=${query}&per_page=${perPage}&client_id=${UNSPLASH_ACCESS_KEY}`);
            const data = await response.json();

            if (Array.isArray(data.results)) {
                data.results.forEach(img => {
                    imagenes.push(img.urls.regular); // Puedes usar thumb, small, regular, full, raw
                });
            }
        } catch (error) {
            console.error('Error al obtener imágenes de Unsplash:', error);
        }
        return imagenes;
    };

    // Llamar a esta función y asignar las imágenes a los viajes
    const cargarViajesConImagenes = async (filtros = {}) => {
        let url = 'http://localhost:8080/api/viajes/buscar';
        const params = new URLSearchParams();

        if (filtros.origen) params.append('origen', filtros.origen);
        if (filtros.destino) params.append('destino', filtros.destino);
        if (filtros.fechaSalida) params.append('fechaSalida', filtros.fechaSalida);
        if (filtros.ordenar) params.append('ordenar', filtros.ordenar);

        if ([...params].length > 0) {
            url += '?' + params.toString();
        }

        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error('Error al cargar los viajes');
            }

            const viajes = await response.json();
            const imagenes = await obtenerImagenesDeUnsplash(); // Obtener imágenes de la NASA

            const viajesList = document.getElementById('viajes-list');
            viajesList.innerHTML = ''; // Limpiar la lista de viajes

            // Renderizar los viajes con imágenes
            viajes.forEach((viaje, index) => {
                const imagenUrl = imagenes[index % imagenes.length]; // Usar imágenes cíclicamente si hay menos imágenes que viajes

                const card = document.createElement('div');
                card.classList.add('card');
                card.innerHTML = `
                <div class="card-content">
                    <div class="trayecto">
                        <span class="origen">${viaje.origen}</span>
                        <i class="fa-solid fa-arrow-right trayecto-arrow"></i>
                        <span class="destino">${viaje.destino}</span>
                    </div>
                    <div class="datos-viaje">
                        <div class="dato-viaje">
                            <span class="dato-label">Fecha de salida</span>
                            <span class="dato-valor">${new Date(viaje.fechaSalida).toLocaleString()}</span>
                        </div>
                        <div class="dato-viaje">
                            <span class="dato-label">Duración</span>
                            <span class="dato-valor">${Math.floor(viaje.duracionMinutos / 60)}h ${viaje.duracionMinutos % 60}m</span>
                        </div>
                        <div class="dato-viaje">
                            <span class="dato-label">Precio</span>
                            <span class="dato-valor">$${viaje.precio.toFixed(2)}</span>
                        </div>
                    </div>
                    <div class="card-buttons">
                        <button class="reservar-btn" data-viaje-id="${viaje.id}">Reservar</button>
                        <button class="favorito-btn" data-viaje-id="${viaje.id}" aria-label="Agregar a favoritos"><i class="fa-regular fa-heart"></i></button>
                    </div>
                </div>
                <img src="${imagenUrl}" alt="Imagen del espacio">
                `;
                viajesList.appendChild(card);
            });

            // Añadir eventos a los botones
            const reservarButtons = document.querySelectorAll('.reservar-btn');
            reservarButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const viajeId = button.getAttribute('data-viaje-id');
                    window.location.href = `reserva.html?viajeId=${viajeId}`;
                });
            });

            const favoritoButtons = document.querySelectorAll('.favorito-btn');
            favoritoButtons.forEach(button => {
                button.addEventListener('click', async () => {
                    const viajeId = button.getAttribute('data-viaje-id');
                    const usuarioId = localStorage.getItem('usuarioId');

                    try {
                        const response = await fetch(`http://localhost:8080/api/usuarios/${usuarioId}/favoritos/${viajeId}`, {
                            method: 'POST'
                        });
                        if (response.ok) {
                            alert('Viaje agregado a favoritos');
                            cargarFavoritos(); // Recargar la lista de favoritos
                        } else {
                            alert('Error al agregar el viaje a favoritos');
                        }
                    } catch (error) {
                        console.error('Error al agregar el viaje a favoritos:', error);
                    }
                });
            });
        } catch (error) {
            console.error('Error al cargar los viajes:', error);
        }
    };

    // Cargar las reservas del cliente
    const cargarReservas = async () => {

        const usuarioId = parseInt(localStorage.getItem('usuarioId'));

        if (isNaN(usuarioId)) {
            alert('No se ha encontrado un usuario autenticado. Por favor, inicia sesión.');
            window.location.href = 'login.html'; // Redirigir al login si no hay usuario autenticado
            return;
        }

        try {
            // Realizar la solicitud al endpoint del backend
            const response = await fetch(`http://localhost:8080/api/usuarios/${usuarioId}/reservas`);
            if (!response.ok) {
                throw new Error('Error al obtener las reservas del cliente');
            }

            const reservas = await response.json();

            const reservasList = document.getElementById('reservas-list');
            reservasList.innerHTML = ''; // Limpiar la lista de reservas
            console.log('Reservas recibidas:', reservas); // Para depuración

            // Renderizar las reservas en la página
            reservas.forEach(reserva => {
                const origen = reserva.viaje?.origen || 'Origen desconocido';
                const destino = reserva.viaje?.destino || 'Destino desconocido';
                const fechaSalida = reserva.viaje?.fechaSalida
                    ? new Date(reserva.viaje.fechaSalida).toLocaleString()
                    : 'Fecha desconocida';


                const card = document.createElement('div');
                card.classList.add('card');
                card.innerHTML = `
        <div class="card-content">
        <div class="trayecto">
            <span class="origen">${origen}</span>
            <i class="fa-solid fa-arrow-right trayecto-arrow"></i>
            <span class="destino">${destino}</span>
        </div>
        <div class="datos-viaje">
            <div class="dato-viaje">
                <span class="dato-label">Fecha de salida</span>
                <span class="dato-valor">${fechaSalida}</span>
            </div>
            <div class="dato-viaje">
                <span class="dato-label">Estado</span>
                <span class="dato-valor">${reserva.estado}</span>
            </div>
        </div>
        <div class="card-buttons">
            <button class="detalle-btn" data-reserva-id="${reserva.id}">Ver detalles</button>
        </div>
    </div>
    `;
                reservasList.appendChild(card);
            });

            // Añadir el event listener para el botón "Ver detalles"
            const detalleButtons = document.querySelectorAll('.detalle-btn');
            detalleButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const reservaId = button.getAttribute('data-reserva-id');
                    window.location.href = `detalle-reserva.html?reservaId=${reservaId}`; // Redirigir a la página de detalles
                });
            });
        } catch (error) {
            console.error('Error al cargar las reservas:', error);
            alert('No se pudieron cargar las reservas. Intenta nuevamente más tarde.');
        }
    };

    const cargarFavoritos = async () => {
        const usuarioId = localStorage.getItem('usuarioId'); // Asegúrate de que el usuario esté autenticado

        try {
            const response = await fetch(`http://localhost:8080/api/usuarios/${usuarioId}/favoritos`);
            const favoritos = await response.json();

            console.log('Favoritos recibidos:', favoritos); // Para depuración

            const favoritosList = document.getElementById('favoritos-list');
            favoritosList.innerHTML = ''; // Limpiar la lista de favoritos

            favoritos.forEach(viaje => {
                const card = document.createElement('div');
                card.classList.add('card');
                card.innerHTML = `
                    <h3>${viaje.origen} -> ${viaje.destino}</h3>
                    <p>Fecha de salida: ${new Date(viaje.fechaSalida).toLocaleString()}</p>
                    <p>Duración: ${viaje.duracionMinutos} minutos</p>
                    <p>Precio: $${viaje.precio.toFixed(2)}</p>
                    <button id="favorito-btn" class="eliminar-favorito-btn" data-viaje-id="${viaje.id}" aria-label="Eliminar de favoritos">
                        <span class="equis-favorito">&times;</span></button>
                `;
                favoritosList.appendChild(card);
            });

            // Añadir el event listener para eliminar favoritos
            const favoritoButtons = document.querySelectorAll('.eliminar-favorito-btn');
            favoritoButtons.forEach(button => {
                button.addEventListener('click', async () => {
                    // ...tu lógica de favorito...
                    const icon = button.querySelector('i');
                    icon.classList.toggle('fa-regular');
                    icon.classList.toggle('fa-solid');
                    // Puedes también cambiar el color si quieres
                });
            });
            favoritoButtons.forEach(button => {
                button.addEventListener('click', async () => {
                    const viajeId = button.getAttribute('data-viaje-id');
                    try {
                        const response = await fetch(`http://localhost:8080/api/usuarios/${usuarioId}/favoritos/${viajeId}`, {
                            method: 'DELETE'
                        });
                        if (response.ok) {
                            alert('Viaje eliminado de favoritos');
                            cargarFavoritos(); // Recargar la lista de favoritos
                        } else {
                            alert('Error al eliminar el viaje de favoritos');
                        }
                    } catch (error) {
                        console.error('Error al eliminar el viaje de favoritos:', error);
                    }
                });
            });
        } catch (error) {
            console.error('Error al cargar los favoritos:', error);
        }
    };

    cargarViajesConImagenes(); // Cargar los viajes disponibles con imágenes de la NASA
    cargarReservas(); // Cargar las reservas del cliente
    cargarFavoritos(); // Cargar los viajes favoritos del cliente

    const usuarioId = parseInt(localStorage.getItem('usuarioId'));
    console.log('ID del usuario autenticado:', usuarioId); // Para depuración

});
