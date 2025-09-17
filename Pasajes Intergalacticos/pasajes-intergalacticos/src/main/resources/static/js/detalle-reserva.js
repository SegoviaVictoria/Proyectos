
document.addEventListener('DOMContentLoaded', async () => {
    
    // Obtener el ID de la reserva desde la URL
    const params = new URLSearchParams(window.location.search);
    const reservaId = params.get('reservaId');
    const usuarioId = localStorage.getItem('usuarioId');

    console.log('ID de la reserva:', reservaId); // Para depuración
    console.log('ID del usuario:', usuarioId); // Para depuración

    if (!reservaId) {
        alert('No se ha encontrado una reserva válida.');
        window.location.href = 'cliente.html?usuarioId=${usuarioId}'; // Redirigir a la página principal si no hay reservaId
        return;
    }

    try {
        // Realizar la solicitud al backend para obtener los detalles de la reserva
        const response = await fetch(`http://localhost:8080/api/reservas/${reservaId}`);
        if (!response.ok) {
            throw new Error('Error al obtener los detalles de la reserva');
        }

        const reserva = await response.json();
        const detalleReserva = document.getElementById('detalle-reserva');
        
        console.log('Detalles de la reserva:', reserva); // Para depuración

        // Renderizar los detalles de la reserva
        detalleReserva.innerHTML = `
            <h2>Reserva ID: ${reserva.id}</h2>
            <p><strong>Origen:</strong> ${reserva.viaje.origen || 'Origen desconocido'}</p>
            <p><strong>Destino:</strong> ${reserva.viaje.destino || 'Destino desconocido'}</p>
            <p><strong>Fecha de reserva:</strong> ${new Date(reserva.fechaReserva).toLocaleString()}</p>
            <p><strong>Asiento:</strong> Fila ${reserva.fila}, Columna ${reserva.columna}</p>
            <p><strong>Estado:</strong> ${reserva.estado}</p>
            <p><strong>Duración del viaje:</strong> ${Math.floor(reserva.viaje.duracionMinutos / 60)} horas y ${reserva.viaje.duracionMinutos % 60} minutos</p>
            <p><strong>Nave:</strong> ${reserva.viaje.nave.nombre} (${reserva.viaje.nave.modelo})</p>
            <p><strong>Precio:</strong> $${reserva.viaje.precio}</p>
        `;
    } catch (error) {
        console.error('Error al cargar los detalles de la reserva:', error);
        alert('No se pudieron cargar los detalles de la reserva. Intenta nuevamente más tarde.');
    }

    // Manejar el botón "Volver"
    const volverButton = document.getElementById('volver');
    volverButton.addEventListener('click', () => {
        window.location.href = 'cliente.html?usuarioId=${usuarioId}'; // Redirigir a la página principal
    });
});