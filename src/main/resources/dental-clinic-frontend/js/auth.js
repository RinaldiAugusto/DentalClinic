// js/auth.js - CON MOCK SUPPORT
class AuthService {
    static isAuthenticated() {
        const token = localStorage.getItem('token');
        return token !== null && token !== 'undefined' && token !== '';
    }

    static getToken() {
        return localStorage.getItem('token');
    }

    static login(token) {
        localStorage.setItem('token', token);
    }

    static logout() {
        localStorage.removeItem('token');
        window.location.href = 'login.html';
    }

    static async makeAuthenticatedRequest(url, options = {}) {
        if (!this.isAuthenticated()) {
            this.redirectToLogin();
            throw new Error('No autenticado');
        }

        const defaultOptions = {
            headers: {
                ...getAuthHeaders(),
                ...options.headers
            }
        };

        try {
            const result = await makeRequest(url, { ...defaultOptions, ...options });
            return result;
        } catch (error) {
            if (error.message.includes('401') || error.message.includes('403')) {
                this.logout();
            }
            throw error;
        }
    }

    static redirectToLogin() {
        if (!window.location.href.includes('login.html')) {
            window.location.href = 'login.html';
        }
    }
}

// Función para mostrar alerts
function showAlert(message, type = 'info') {
    // Remover alerts existentes
    const existingAlerts = document.querySelectorAll('.alert');
    existingAlerts.forEach(alert => alert.remove());

    const alertDiv = document.createElement('div');
    alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
    alertDiv.innerHTML = `
        ${message}
        <button type="button" class="close" data-dismiss="alert">
            <span>&times;</span>
        </button>
    `;

    const container = document.querySelector('.container') || document.body;
    container.insertBefore(alertDiv, container.firstChild);

    // Auto-remove after 5 seconds
    setTimeout(() => {
        if (alertDiv.parentElement) {
            alertDiv.remove();
        }
    }, 5000);
}

// Verificar autenticación en páginas protegidas
function checkAuthentication() {
    if (!AuthService.isAuthenticated()) {
        AuthService.redirectToLogin();
    }
}

// Verificar si ya está autenticado (para login page)
function redirectIfAuthenticated() {
    if (AuthService.isAuthenticated()) {
        window.location.href = 'dashboard.html';
    }
}