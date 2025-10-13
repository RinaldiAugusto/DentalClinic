// config.js - CON MOCK TEMPORAL
const CONFIG = {
    API_BASE_URL: 'https://dental-clinic-backend-53ys.onrender.com',
    USE_MOCK: true, // CAMBIAR A FALSE CUANDO EL BACKEND FUNCIONE
    ENDPOINTS: {
        AUTH: {
            LOGIN: '/auth/login',
            REGISTER: '/auth/register'
        },
        DENTISTS: '/dentists',
        PATIENTS: '/patients',
        APPOINTMENTS: '/appointments'
    }
};

// Headers para autenticación
function getAuthHeaders() {
    const token = localStorage.getItem('token');
    return {
        'Content-Type': 'application/json',
        'Authorization': token ? `Bearer ${token}` : ''
    };
}

// Mock Service para desarrollo
class MockBackend {
    static async request(endpoint, options = {}) {
        console.log('🔧 MOCK: Simulando request a', endpoint, options);

        // Simular delay de red
        await new Promise(resolve => setTimeout(resolve, 800));

        // Simular errores aleatorios (10% de probabilidad)
        if (Math.random() < 0.1 && !endpoint.includes('/auth')) {
            throw new Error('Error simulado del servidor');
        }

        switch (endpoint) {
            case '/auth/register':
                return {
                    token: 'mock-jwt-token-' + Date.now(),
                    user: { email: options.body?.email, role: 'ADMIN' }
                };

            case '/auth/login':
                if (options.body?.email && options.body?.password) {
                    return {
                        token: 'mock-jwt-token-' + Date.now(),
                        user: { email: options.body.email, role: 'ADMIN' }
                    };
                }
                throw new Error('Credenciales inválidas');

            case '/dentists':
                if (options.method === 'GET') {
                    return [
                        { id: 1, registration: "12345", name: "Carlos", lastName: "López" },
                        { id: 2, registration: "67890", name: "Ana", lastName: "García" },
                        { id: 3, registration: "54321", name: "Miguel", lastName: "Rodríguez" }
                    ];
                }
                if (options.method === 'POST') {
                    return { id: Date.now(), ...options.body, success: true };
                }
                if (options.method === 'PUT') {
                    return { ...options.body, success: true };
                }
                if (options.method === 'DELETE') {
                    return { success: true, message: 'Odontólogo eliminado' };
                }
                return { success: true };

            case '/patients':
                if (options.method === 'GET') {
                    return [
                        { id: 1, dni: "12345678", name: "María", lastName: "González", address: "Calle 123", dischargeDate: "2024-01-15" },
                        { id: 2, dni: "87654321", name: "Juan", lastName: "Pérez", address: "Av. Principal 456", dischargeDate: "2024-01-10" }
                    ];
                }
                if (options.method === 'POST') {
                    return { id: Date.now(), ...options.body, success: true };
                }
                return { success: true };

            case '/appointments':
                if (options.method === 'GET') {
                    return [
                        {
                            id: 1,
                            dateTime: "2024-01-20T10:00:00",
                            dentist: { id: 1, name: "Carlos", lastName: "López" },
                            patient: { id: 1, name: "María", lastName: "González" }
                        }
                    ];
                }
                if (options.method === 'POST') {
                    return { id: Date.now(), ...options.body, success: true };
                }
                return { success: true };

            default:
                return { message: 'Mock response for ' + endpoint };
        }
    }
}

// Función para hacer requests (automáticamente usa mock o real)
async function makeRequest(url, options = {}) {
    const endpoint = url.replace(CONFIG.API_BASE_URL, '');

    if (CONFIG.USE_MOCK) {
        return await MockBackend.request(endpoint, options);
    } else {
        const response = await fetch(url, options);
        if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);
        return await response.json();
    }
}