document.addEventListener('DOMContentLoaded', () => {

    const viajeInfo = document.getElementById('viaje-info');
    const reservasList = document.getElementById('reservas-list');
    const backBtn = document.getElementById('back-btn');

    // Manejar el clic en el botón "Volver"
    backBtn.addEventListener('click', () => {
        window.location.href = 'empresa.html?usuarioId=${usuarioId}'; // Volver a la página anterior
    });

    // Obtener el ID del viaje desde la URL
    const urlParams = new URLSearchParams(window.location.search);
    const viajeId = urlParams.get('viajeId');

    console.log('ID del viaje:', viajeId);

    // Función para obtener el número de reservas
    const obtenerNumeroDeReservas = async () => {
        let numeroDeReservas = 0;

        try {
            const reservasResponse = await fetch(`http://localhost:8080/api/reservas/viajes/${viajeId}`);
            if (reservasResponse.ok) {
                const reservas = await reservasResponse.json(); // Suponiendo que el endpoint devuelve un array
                numeroDeReservas = reservas.length; // Contar el número de reservas
            } else {
                console.error(`Error al cargar las reservas para el viaje ${viajeId}`);
            }
        } catch (error) {
            console.error('Error al obtener el número de reservas:', error);
        }

        return numeroDeReservas;
    };

    // Función para cargar la información del viaje
    const cargarInfoViaje = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/viajes/${viajeId}`);
            if (!response.ok) {
                throw new Error('Error al cargar la información del viaje');
            }

            // Obtener la información del viaje
            const viaje = await response.json();
            console.log('Información del viaje:', viaje); // Para depuración

            // Obtener el número de reservas
            const numeroDeReservas = await obtenerNumeroDeReservas();

            // Renderizar la información del viaje
            viajeInfo.innerHTML = `
                <h3>${viaje.origen} -> ${viaje.destino}</h3>
                <p><strong>Fecha de salida:</strong> ${new Date(viaje.fechaSalida).toLocaleString()}</p>
                <p><strong>Duración:</strong> ${Math.floor(viaje.duracionMinutos / 60)} horas y ${viaje.duracionMinutos % 60} minutos</p>
                <p><strong>Precio:</strong> $${viaje.precio.toFixed(2)}</p>
                <p><strong>Nave:</strong> ${viaje.nave.nombre} (${viaje.nave.modelo})</p>
                <p><strong>Capacidad:</strong> ${viaje.nave.capacidad} pasajeros</p>
                <p><strong>Número de reservas:</strong> ${numeroDeReservas}</p>
            `;
        } catch (error) {
            console.error('Error al cargar la información del viaje:', error);
        }
    };

    // Función para cargar las reservas del viaje
    const cargarReservas = async () => {
        try {
            const response = await fetch(`http://localhost:8080/api/reservas/viajes/${viajeId}`);
            if (!response.ok) {
                throw new Error('Error al cargar las reservas');
            }

            const reservas = await response.json();
            console.log('Reservas:', reservas); // Para depuración

            // Dividir reservas en confirmadas y pendientes
            const reservasPendientes = reservas.filter(reserva => reserva.estado === 'PENDIENTE');
            const reservasConfirmadas = reservas.filter(reserva => reserva.estado === 'CONFIRMADA');

            // Limpiar las secciones de reservas
            reservasList.innerHTML = '';
            const pendientesSection = document.createElement('div');
            const confirmadasSection = document.createElement('div');

            // Renderizar las reservas pendientes
            pendientesSection.innerHTML = `<h3></h3>`;
            for (const reserva of reservasPendientes) {
                // Obtener información del cliente para cada reserva
                const clienteResponse = await fetch(`http://localhost:8080/api/reservas/${reserva.id}/usuario`);
                let cliente = { nombre: 'Desconocido', email: 'No disponible' }; // Valores predeterminados

                if (clienteResponse.ok) {
                    cliente = await clienteResponse.json();
                } else {
                    console.error(`Error al cargar el cliente para la reserva ${reserva.id}`);
                }

                // Renderizar la reserva con la información del cliente
                const card = document.createElement('div');
                card.classList.add('card');
                card.innerHTML = `
                <h3>Reserva ID: ${reserva.id}</h3>
                <p><strong>Cliente:</strong> ${cliente.nombre}</p>
                <p><strong>Email:</strong> ${cliente.email}</p>
                <p><strong>Asiento:</strong> Fila ${reserva.fila}, Columna ${reserva.columna}</p>
                <p><strong>Estado:</strong> ${reserva.estado}</p>
                <button class="confirmar-btn" data-reserva-id="${reserva.id}">Confirmar Reserva</button>
            `;
                reservasList.appendChild(card);
            }

            // Renderizar las reservas confirmadas
            confirmadasSection.innerHTML = `<h2>Reservas Confirmadas</h2>`;
            for (const reserva of reservasConfirmadas) {
                const clienteResponse = await fetch(`http://localhost:8080/api/reservas/${reserva.id}/usuario`);
                let cliente = { nombre: 'Desconocido', email: 'No disponible' };

                if (clienteResponse.ok) {
                    cliente = await clienteResponse.json();
                } else {
                    console.error(`Error al cargar el cliente para la reserva ${reserva.id}`);
                }

                const card = document.createElement('div');
                card.classList.add('card');
                card.innerHTML = `
                <h3>Reserva ID: ${reserva.id}</h3>
                <p><strong>Cliente:</strong> ${cliente.nombre}</p>
                <p><strong>Email:</strong> ${cliente.email}</p>
                <p><strong>Asiento:</strong> Fila ${reserva.fila}, Columna ${reserva.columna}</p>
                <p><strong>Estado:</strong> ${reserva.estado}</p>
            `;
                confirmadasSection.appendChild(card);
            }

            // Añadir ambas secciones al contenedor principal
            reservasList.appendChild(pendientesSection);
            reservasList.appendChild(confirmadasSection);

            // Añadir eventos a los botones "Confirmar Reserva"
            const confirmarButtons = document.querySelectorAll('.confirmar-btn');
            confirmarButtons.forEach(button => {
                button.addEventListener('click', async (event) => {
                    const reservaId = button.getAttribute('data-reserva-id');
                    const reserva = reservas.find(r => r.id == reservaId);
                    await confirmarReserva(reserva);
                });
            });

        } catch (error) {
            console.error('Error al cargar las reservas:', error);
        }
    };

    const confirmarReserva = async (reserva) => {

        console.log('Reserva a confirmar:', reserva);

        try {
            const response = await fetch(`http://localhost:8080/api/reservas/${reserva.id}`, {
                method: 'PATCH',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ estado: 'CONFIRMADA' })
            });
            if (!response.ok) {
                throw new Error('Error al confirmar la reserva');
            }

            // Actualizar la lista de reservas después de confirmar
            alert('Reserva confirmada con éxito');
            cargarReservas(); // Recargar las reservas
        } catch (error) {
            console.error('Error al confirmar la reserva:', error);
            alert('No se pudo confirmar la reserva');
        }
    };

    // Cargar las reservas al cargar la página
    cargarReservas();
    cargarInfoViaje();
});