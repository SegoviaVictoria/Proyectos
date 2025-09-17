const loginForm = document.getElementById('login-form');
const registerForm = document.getElementById('register-form');
const toggleForm = document.getElementById('toggle-form');
const formTitle = document.getElementById('form-title');

toggleForm.addEventListener('click', () => {
    const isLoginVisible = !loginForm.classList.contains('hidden');
    loginForm.classList.toggle('hidden');
    registerForm.classList.toggle('hidden');
    formTitle.textContent = isLoginVisible ? 'Crear Cuenta' : 'Iniciar Sesión';
    toggleForm.textContent = isLoginVisible
        ? '¿Ya tienes cuenta? Inicia sesión'
        : '¿No tienes cuenta? Regístrate';
});

loginForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch('http://localhost:8080/api/usuarios/login', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        const data = await response.json(); // Leer el cuerpo de la respuesta una sola vez
        console.log('Respuesta del backend:', data);

        if (response.ok) {
            alert('Login exitoso');
            localStorage.setItem('usuarioId', data.id); // Guardar el ID del usuario en localStorage
            localStorage.setItem('rol', data.rol); // Guardar el rol del usuario en localStorage
            return redirectToRolePage(data.rol); // Redirigir según el rol
        } else {
            alert(data.message || 'Error en el login');
        }
    } catch (error) {
        console.error('Error de red:', error);
        alert('Hubo un error al intentar iniciar sesión. Intenta nuevamente.');
    }
});

function redirectToRolePage(rol) {
    switch (rol) {
        case 'CLIENTE':
            window.location.href = 'cliente.html';
            break;
        case 'EMPRESA':
            window.location.href = 'empresa.html';
            break;
        case 'ADMIN':
            window.location.href = 'admin.html';
            break;
        default:
            alert('Rol no reconocido');
    }
}

registerForm.addEventListener('submit', async (e) => {
    e.preventDefault(); // Evitar el envío predeterminado del formulario

    const nombre = document.getElementById('register-nombre').value;
    const email = document.getElementById('register-email').value;
    const password = document.getElementById('register-password').value;
    const rol = document.getElementById('register-rol').value;

    try {
        const response = await fetch('http://localhost:8080/api/usuarios', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nombre, email, password, rol })
        });

        const data = await response.json();
        if (response.ok) {
            alert('Cuenta creada. Ahora inicia sesión.');
            toggleForm.click(); // Cambiar al formulario de login
        } else {
            alert(data.message || 'Error al crear cuenta');
        }
    } catch (error) {
        console.error('Error al registrar usuario:', error);
        alert('Hubo un problema al intentar crear la cuenta. Intenta nuevamente.');
    }
});
