document.addEventListener('DOMContentLoaded', () => {

    const viajesList = document.getElementById('viajes-list');
    const reservasList = document.getElementById('reservas-list');
    const crearViajeForm = document.getElementById('crear-viaje-form');
    const naveSelect = document.getElementById('nave');
    let usuario = null; // Variable para almacenar el usuario autenticado

    const empresaId = localStorage.getItem('usuarioId'); // Obtener el ID de la empresa autenticada

    // Función para cargar las naves disponibles
    const cargarNaves = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/naves`);
            if (!response.ok) {
                throw new Error('Error al cargar las naves');
            }

            const naves = await response.json();
            naveSelect.innerHTML = ''; // Limpiar el select de naves

            naves.forEach(nave => {
                const option = document.createElement('option');
                option.value = nave.id;
                option.textContent = `${nave.nombre} (${nave.modelo}) - Capacidad: ${nave.capacidad}`;
                naveSelect.appendChild(option);
            });
        } catch (error) {
            console.error('Error al cargar las naves:', error);
        }
    };

    console.log("Empresa/Usuario Id: " + empresaId); // Verificar el ID de la empresa en la consola

    // Función para cargar los viajes de la empresa
    const cargarViajes = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/usuarios/${empresaId}`);
            if (!response.ok) {
                throw new Error('Error al cargar los viajes');
            }

            // Obtener el usuario y su lista de viajes
            usuario = await response.json();
            const viajes = usuario.viajes; // Acceder al array de viajes dentro del usuario

            viajesList.innerHTML = ''; // Limpiar la lista de viajes

            for (const viaje of viajes) {
                // Obtener el número de reservas para este viaje
                const reservasResponse = await fetch(`http://localhost:8080/api/reservas/viajes/${viaje.id}`);
                let numeroDeReservas = 0;

                if (reservasResponse.ok) {
                    const reservas = await reservasResponse.json(); // Suponiendo que el endpoint devuelve un array
                    numeroDeReservas = reservas.length; // Suponiendo que el endpoint devuelve un número
                } else {
                    console.error(`Error al cargar las reservas para el viaje ${viaje.id}`);
                }

                // Crear la tarjeta del viaje
                const card = document.createElement('div');
                card.classList.add('card');
                card.innerHTML = `
                    <h3>${viaje.origen} -> ${viaje.destino}</h3>
                    <p><strong>Fecha de salida</strong>: ${new Date(viaje.fechaSalida).toLocaleString()}</p>
                    <p><strong>Duración</strong>: ${Math.floor(viaje.duracionMinutos / 60)} horas y ${viaje.duracionMinutos % 60} minutos</p>
                    <p><strong>Precio</strong>: $${viaje.precio.toFixed(2)}</p>
                    <p><strong>Nave</strong>: ${viaje.nave.nombre} (${viaje.nave.modelo})</p>
                    <p><strong>Capacidad</strong>: ${viaje.nave.capacidad} pasajeros</p>
                    <p><strong>Numero de reservas</strong>: ${numeroDeReservas}</p>
                    <button class="ver-reservas-btn" data-viaje-id="${viaje.id}">Ver Reservas</button>
                `;
                viajesList.appendChild(card);
            }

            // Añadir eventos a los botones "Ver Reservas"
            const verReservasButtons = document.querySelectorAll('.ver-reservas-btn');
            verReservasButtons.forEach(button => {
                button.addEventListener('click', () => {
                    const viajeId = button.getAttribute('data-viaje-id');
                    window.location.href = `reservas-empresa.html?viajeId=${viajeId}`;
                });
            });
        } catch (error) {
            console.error('Error al cargar los viajes:', error);
        }
    };

    // Función para cargar las reservas de un viaje
    const cargarReservas = async (viajeId) => {
        try {
            const response = await fetch(`http://localhost:8080/api/viajes/${viajeId}/reservas`);
            if (!response.ok) {
                throw new Error('Error al cargar las reservas');
            }

            const reservas = await response.json();
            reservasList.innerHTML = ''; // Limpiar la lista de reservas

            reservas.forEach(reserva => {
                const card = document.createElement('div');
                card.classList.add('card');
                card.innerHTML = `
                    <h3>Reserva ID: ${reserva.id}</h3>
                    <p>Cliente: ${reserva.cliente.nombre} (${reserva.cliente.email})</p>
                    <p>Asiento: Fila ${reserva.fila}, Columna ${reserva.columna}</p>
                    <p>Estado: ${reserva.estado}</p>
                `;
                reservasList.appendChild(card);
            });
        } catch (error) {
            console.error('Error al cargar las reservas:', error);
        }
    };

    // Función para crear un nuevo viaje
    crearViajeForm.addEventListener('submit', async (event) => {
        event.preventDefault();

        // Obtener el objeto nave basado en el select
        const naveSeleccionada = {
            id: parseInt(naveSelect.value), // Este es el id de la nave seleccionada
            nombre: naveSelect.options[naveSelect.selectedIndex].text.split('(')[0].trim(),
            modelo: naveSelect.options[naveSelect.selectedIndex].text.split('(')[1].split(')')[0].trim(),
            capacidad: parseInt(naveSelect.options[naveSelect.selectedIndex].text.split('Capacidad: ')[1].trim())
        };

        const nuevoViaje = {
            origen: document.getElementById('origen').value,
            destino: document.getElementById('destino').value,
            fechaSalida: document.getElementById('fecha-salida').value,
            duracionMinutos: parseInt(document.getElementById('duracion').value),
            precio: parseFloat(document.getElementById('precio').value),
            nave: naveSeleccionada,
            empresa: usuario
            }

        try {
            const response = await fetch('http://localhost:8080/api/viajes', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(nuevoViaje)
            });

            if (!response.ok) {
                throw new Error('Error al crear el viaje');
            }

            alert('Viaje creado exitosamente');
            crearViajeForm.reset(); // Limpiar el formulario
            cargarViajes(); // Recargar la lista de viajes
        } catch (error) {
            console.error('Error al crear el viaje:', error);
        }
    });

    // Cargar las naves y los viajes al cargar la página
    cargarNaves();
    cargarViajes();
});