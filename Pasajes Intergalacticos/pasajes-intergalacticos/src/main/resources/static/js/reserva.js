document.addEventListener('DOMContentLoaded', async () => {

  // Obtener el viajeId de la URL
  const params = new URLSearchParams(window.location.search);
  const viajeId = params.get('viajeId');

  if (!viajeId) {
    alert('No se ha seleccionado un viaje.');
    return;
  }

  console.log('ID del viaje:', viajeId); // Para depuración

  // Obtener la información del viaje desde el servidor
  const response = await fetch(`http://localhost:8080/api/viajes/${viajeId}`);
  const viaje = await response.json();

  console.log('Datos del viaje:', viaje); // Para depuración

  // Mostrar la información del viaje en la página
  const viajeInfo = document.getElementById('viaje-info');
  let naveImgUrl = '';

  // Petición para obtener una imagen de la nave
  try {
    const res = await fetch('https://images-api.nasa.gov/search?q=spacecraft&media_type=image');
    const data = await res.json();
    const items = data.collection.items;

    if (!items || items.length === 0) {
      document.getElementById('caption').textContent = "No se encontraron imágenes.";
      return;
    }

    const randomItem = items[Math.floor(Math.random() * items.length)];
    naveImgUrl = randomItem.links[0].href;

  } catch (error) {
    document.getElementById('caption').textContent = "Error al cargar imagen.";
    console.error("Error:", error);
  }
  const descripcion = `
    Emprenda un trayecto intergaláctico desde <strong>${viaje.origen}</strong> hasta <strong>${viaje.destino}</strong> a bordo de la nave <strong>${viaje.nave.nombre}</strong> (${viaje.nave.modelo}). 
    Disfrute de instalaciones de última generación y un servicio de excelencia durante los ${viaje.duracionMinutos} minutos de viaje. 
    Nuestra tripulación está comprometida con su seguridad y comodidad en todo momento. 
    <br><br>
    <em>Le recomendamos presentarse al embarque con al menos 30 minutos de antelación. Para su mayor confort, la nave dispone de servicios de catering, entretenimiento y asistencia personalizada.</em>
`;

  viajeInfo.innerHTML = `
  <div class="card">
    <div class="card-content">
      <div class="trayecto">
        <span class="origen">${viaje.origen}</span>
        <span class="trayecto-arrow">&rarr;</span>
        <span class="destino">${viaje.destino}</span>
      </div>
      <div class="datos-viaje">
        <div class="dato-viaje">
          <span class="dato-label">Fecha de salida</span>
          <span class="dato-valor">${new Date(viaje.fechaSalida).toLocaleString()}</span>
        </div>
        <div class="dato-viaje">
          <span class="dato-label">Duración estimada</span>
          <span class="dato-valor">${viaje.duracionMinutos} minutos</span>
        </div>
        <div class="dato-viaje">
          <span class="dato-label">Precio</span>
          <span class="dato-valor">$${viaje.precio.toFixed(2)}</span>
        </div>
        <div class="dato-viaje">
          <span class="dato-label">Capacidad de la nave</span>
          <span class="dato-valor">${viaje.nave.capacidad} pasajeros</span>
        </div>
      </div>
      <div class="servicios-viaje">
        <div class="servicio servicio-catering">Catering</div>
        <div class="servicio servicio-entretenimiento">Entretenimiento</div>
        <div class="servicio servicio-asistencia">Asistencia personalizada</div>
        <div class="servicio servicio-vistas">Vistas panorámicas</div>
      </div>
      <div class="card-img-container-horizontal">
        <img id="nave-img" src="${naveImgUrl}" alt="Imagen de la nave" />
        <div class="nombre-nave">${viaje.nave.nombre} (${viaje.nave.modelo})</div>
      </div>
    </div>
  </div>
`;

  // Obtener las reservas de la nave
  const reservasResponse = await fetch(`http://localhost:8080/api/reservas/nave/${viaje.nave.id}`);
  const reservas = await reservasResponse.json();

  console.log('Reservas de la nave:', reservas); // Para depuración

  // Crear la matriz de asientos
  const asientosContainer = document.getElementById('asientos-container');
  const filas = viaje.nave.filas; // Número de filas de la nave
  const columnas = viaje.nave.columnas; // Número de columnas de la nave

  console.log('Creando la cuadrícula de asientos...'); // Para depuración
  console.log('Reservas existentes:', reservas); // Para depuración

  // Crear la cuadrícula de asientos
  for (let fila = 1; fila <= filas; fila++) {
    const filaDiv = document.createElement('div');
    filaDiv.classList.add('fila');
    for (let columna = 1; columna <= columnas; columna++) {
      const asiento = document.createElement('div');
      asiento.classList.add('asiento');
      asiento.dataset.fila = fila;
      asiento.dataset.columna = columna;

      // Verificar si el asiento está reservado
      const reservado = reservas.some(reserva => reserva.fila === fila && reserva.columna === columna);
      if (reservado) {
        asiento.classList.add('ocupado'); // Marcar como ocupado
        asiento.textContent = 'X';
      } else {
        asiento.classList.add('disponible'); // Marcar como disponible
        asiento.textContent = `${fila}-${columna}`;
        asiento.addEventListener('click', () => {
          // Seleccionar el asiento
          document.querySelectorAll('.asiento.seleccionado').forEach(a => a.classList.remove('seleccionado'));
          asiento.classList.add('seleccionado');
        });
      }
      filaDiv.appendChild(asiento);
    }
    asientosContainer.appendChild(filaDiv);
  }

  // Manejar la confirmación de la reserva
  const confirmarReservaButton = document.getElementById('confirmar-reserva');
  confirmarReservaButton.addEventListener('click', async () => {
    try {
      // Obtener datos completos del usuario
      const usuario = await fetch(`http://localhost:8080/api/usuarios/${usuarioId}`).then(res => res.json());

      // Obtener datos completos del viaje
      const viaje = await fetch(`http://localhost:8080/api/viajes/${viajeId}`).then(res => res.json());

      // Obtener el asiento seleccionado
      const asientoSeleccionado = document.querySelector('.asiento.seleccionado');
      if (!asientoSeleccionado) {
        alert('Por favor, selecciona un asiento.');
        return;
      }

      const fila = parseInt(asientoSeleccionado.dataset.fila);
      const columna = parseInt(asientoSeleccionado.dataset.columna);

      // Crear el objeto de reserva completo
      const reserva = {
        usuario: usuario,
        viaje: viaje,
        nave: viaje.nave,
        fechaReserva: new Date().toISOString(),
        estado: "PENDIENTE",
        fila: fila, // Reemplaza con la fila seleccionada
        columna: columna // Reemplaza con la columna seleccionada
      };

      // Enviar la reserva al backend
      const response = await fetch('http://localhost:8080/api/reservas', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(reserva)
      });

      if (response.ok) {
        alert('Reserva creada exitosamente');
        window.location.href = `cliente.html?usuarioId=${usuarioId}`; // Redirigir a la página cliente
      } else {
        const errorData = await response.json();
        alert(`Error al crear la reserva: ${errorData.message}`);
      }
    } catch (error) {
      console.error('Error al crear la reserva:', error);
      alert('Hubo un problema al intentar crear la reserva. Intenta nuevamente.');
    }
  });

  // Manejar el evento del botón "Volver"
  const volverButton = document.getElementById('volver');
  volverButton.addEventListener('click', () => {
    window.location.href = 'cliente.html?usuarioId=${usuarioId}'; // Redirigir a la página cliente
  });

  const usuarioId = parseInt(localStorage.getItem('usuarioId'));
  console.log('ID del usuario autenticado:', usuarioId); // Para depuración

});