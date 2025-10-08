// MOCK BACKEND SERVICE
class MockBackend {
    static async request(endpoint, options = {}) {
        console.log('MOCK: Request a', endpoint);
        await new Promise(resolve => setTimeout(resolve, 800));

        if (endpoint === '/auth/register') {
            return { token: 'mock-token-' + Date.now(), user: { email: options.body?.email, role: 'ADMIN' } };
        }

        if (endpoint === '/auth/login') {
            if (options.body?.email && options.body?.password) {
                return { token: 'mock-token-' + Date.now(), user: { email: options.body.email, role: 'ADMIN' } };
            }
            throw new Error('Credenciales inválidas');
        }

        return { message: 'Mock response' };
    }
}

// FUNCIONES AUXILIARES
function showAlert(message, type = 'info') {
    const existingAlerts = document.querySelectorAll('.alert');
    existingAlerts.forEach(alert => alert.remove());

    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show mt-3`;
    alertDiv.innerHTML = `${message}<button type="button" class="close" data-dismiss="alert"><span>&times;</span></button>`;

    const responseDiv = document.getElementById('response');
    responseDiv.innerHTML = '';
    responseDiv.appendChild(alertDiv);

    setTimeout(() => { if (alertDiv.parentElement) alertDiv.remove(); }, 5000);
}

function toggleForms(showLogin) {
    const loginSection = document.getElementById('loginSection');
    const registerSection = document.getElementById('registerSection');
    loginSection.style.display = showLogin ? 'block' : 'none';
    registerSection.style.display = showLogin ? 'none' : 'block';
}

// INICIALIZACIÓN CUANDO EL DOCUMENTO ESTÁ LISTO
document.addEventListener('DOMContentLoaded', function() {
    console.log('Login page loaded - DOM ready');

    // Verificar autenticación
    if (AuthService.isAuthenticated()) {
        window.location.href = 'dashboard.html';
        return;
    }

    // ELEMENTOS DEL DOM
    const loginForm = document.getElementById('loginForm');
    const registerForm = document.getElementById('registerForm');
    const showRegisterLink = document.getElementById('showRegisterLink');
    const showLoginLink = document.getElementById('showLoginLink');

    // EVENTO: Mostrar registro
    showRegisterLink.addEventListener('click', function(e) {
        e.preventDefault();
        console.log('Click en Registrate aquí');
        toggleForms(false);
    });

    // EVENTO: Mostrar login
    showLoginLink.addEventListener('click', function(e) {
        e.preventDefault();
        console.log('Click en Inicia sesión');
        toggleForms(true);
    });

    // EVENTO: Submit login
    loginForm.addEventListener('submit', async function(event) {
        event.preventDefault();
        console.log('Submit login');

        const loginBtn = document.getElementById('loginBtn');
        const email = document.getElementById('email').value;
        const password = document.getElementById('password').value;

        if (!email || !password) {
            showAlert('Complete todos los campos', 'danger');
            return;
        }

        loginBtn.disabled = true;
        loginBtn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Iniciando...';

        try {
            let result;
            if (CONFIG.USE_MOCK) {
                result = await MockBackend.request('/auth/login', { body: { email, password } });
            } else {
                const response = await fetch(CONFIG.API_BASE_URL + CONFIG.ENDPOINTS.AUTH.LOGIN, {
                    method: 'POST', headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ email, password })
                });
                if (!response.ok) throw new Error('Error login');
                result = await response.json();
            }

            AuthService.login(result.token);
            showAlert('Login exitoso!', 'success');
            setTimeout(() => window.location.href = 'dashboard.html', 1000);
        } catch (error) {
            showAlert('Error: ' + error.message, 'danger');
        } finally {
            loginBtn.disabled = false;
            loginBtn.innerHTML = 'Iniciar Sesión';
        }
    });

    // EVENTO: Submit registro
    registerForm.addEventListener('submit', async function(event) {
        event.preventDefault();
        console.log('Submit registro');

        const registerBtn = document.getElementById('registerBtn');
        const email = document.getElementById('registerEmail').value;
        const password = document.getElementById('registerPassword').value;
        const confirmPassword = document.getElementById('registerConfirmPassword').value;

        if (!email || !password || !confirmPassword) {
            showAlert('Complete todos los campos', 'danger');
            return;
        }
        if (password !== confirmPassword) {
            showAlert('Contraseñas no coinciden', 'danger');
            return;
        }
        if (password.length < 6) {
            showAlert('Contraseña muy corta', 'danger');
            return;
        }

        registerBtn.disabled = true;
        registerBtn.innerHTML = '<span class="spinner-border spinner-border-sm"></span> Registrando...';

        try {
            if (CONFIG.USE_MOCK) {
                await MockBackend.request('/auth/register', { body: { email, password } });
                showAlert('Registro exitoso! (Mock)', 'success');
            } else {
                const response = await fetch(CONFIG.API_BASE_URL + CONFIG.ENDPOINTS.AUTH.REGISTER, {
                    method: 'POST', headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ email, password })
                });
                if (!response.ok) throw new Error('Error registro');
                showAlert('Registro exitoso!', 'success');
            }

            registerForm.reset();
            setTimeout(() => {
                toggleForms(true);
                document.getElementById('email').value = email;
                document.getElementById('password').value = password;
            }, 1000);
        } catch (error) {
            showAlert('Error: ' + error.message, 'danger');
        } finally {
            registerBtn.disabled = false;
            registerBtn.innerHTML = 'Registrarse';
        }
    });

    console.log('Todos los event listeners configurados');
});