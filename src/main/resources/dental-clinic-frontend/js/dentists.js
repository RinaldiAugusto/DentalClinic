// js/dentists.js - SERVICIO PARA ODONTÓLOGOS
class DentistService {
    static async getAllDentists() {
        try {
            const response = await AuthService.makeAuthenticatedRequest(
                `${CONFIG.API_BASE_URL}${CONFIG.ENDPOINTS.DENTISTS}`
            );
            return await response.json();
        } catch (error) {
            console.error('Error fetching dentists:', error);
            throw error;
        }
    }

static async createDentist(dentistData) {
    try {
        const response = await AuthService.makeAuthenticatedRequest(
            `${CONFIG.API_BASE_URL}${CONFIG.ENDPOINTS.DENTISTS}`,
            {
                method: 'POST',
                body: JSON.stringify(dentistData)
            }
        );

        console.log('Response status:', response.status);
        console.log('Response headers:', response.headers);

        // Primero obtener el texto de la respuesta
        const responseText = await response.text();
        console.log('Raw response:', responseText);

        // Intentar parsear como JSON, si falla usar el texto
        try {
            return JSON.parse(responseText);
        } catch (e) {
            console.log('Response is not JSON, returning text:', responseText);
            return { message: responseText, status: response.status };
        }

    } catch (error) {
        console.error('Error creating dentist:', error);
        throw error;
    }
}

    static async updateDentist(id, dentistData) {
        try {
            const response = await AuthService.makeAuthenticatedRequest(
                `${CONFIG.API_BASE_URL}${CONFIG.ENDPOINTS.DENTISTS}/${id}`,
                {
                    method: 'PUT',
                    body: JSON.stringify(dentistData)
                }
            );
            return await response.json();
        } catch (error) {
            console.error('Error updating dentist:', error);
            throw error;
        }
    }

    static async deleteDentist(id) {
        try {
            const response = await AuthService.makeAuthenticatedRequest(
                `${CONFIG.API_BASE_URL}${CONFIG.ENDPOINTS.DENTISTS}/${id}`,
                {
                    method: 'DELETE'
                }
            );
            return await response.json();
        } catch (error) {
            console.error('Error deleting dentist:', error);
            throw error;
        }
    }

    static async getDentistById(id) {
        try {
            const response = await AuthService.makeAuthenticatedRequest(
                `${CONFIG.API_BASE_URL}${CONFIG.ENDPOINTS.DENTISTS}/${id}`
            );
            return await response.json();
        } catch (error) {
            console.error('Error fetching dentist:', error);
            throw error;
        }
    }
}

// FUNCIONES DE UI PARA ODONTÓLOGOS
class DentistUI {
    static showAlert(message, type = 'info') {
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

        setTimeout(() => {
            if (alertDiv.parentElement) alertDiv.remove();
        }, 5000);
    }

    static formatDentistTable(dentists) {
        if (!dentists || dentists.length === 0) {
            return `
                <tr>
                    <td colspan="5" class="text-center text-muted py-4">
                        <i class="fas fa-user-md fa-2x mb-2"></i><br>
                        No hay odontólogos registrados
                    </td>
                </tr>
            `;
        }

        return dentists.map(dentist => `
            <tr id="dentist-${dentist.id}">
                <td><strong>${dentist.id}</strong></td>
                <td><span class="badge badge-secondary">${dentist.registration}</span></td>
                <td>${dentist.name}</td>
                <td>${dentist.lastName}</td>
                <td>
                    <button class="btn btn-sm btn-outline-primary mr-1" onclick="DentistUI.showEditForm(${dentist.id})">
                        <i class="fas fa-edit"></i> Editar
                    </button>
                    <button class="btn btn-sm btn-outline-danger" onclick="DentistUI.confirmDelete(${dentist.id})">
                        <i class="fas fa-trash"></i> Eliminar
                    </button>
                </td>
            </tr>
        `).join('');
    }
}