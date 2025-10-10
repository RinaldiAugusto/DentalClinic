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
        console.log('🔧 createDentist llamado con:', dentistData);

        const token = AuthService.getToken();
        console.log('🔧 Token disponible:', !!token);

        const url = `${CONFIG.API_BASE_URL}${CONFIG.ENDPOINTS.DENTISTS}`;
        console.log('🔧 URL:', url);

        // Usar fetch directo para debugging
        const response = await fetch(url, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(dentistData)
        });

        console.log('🔧 Response status:', response.status);
        console.log('🔧 Response ok:', response.ok);
        console.log('🔧 Response type:', response.type);

        if (!response.ok) {
            const errorText = await response.text();
            console.log('🔧 Error response:', errorText);
            throw new Error(`HTTP ${response.status}: ${errorText}`);
        }

        const result = await response.json();
        console.log('🔧 Success result:', result);
        return result;

    } catch (error) {
        console.error('🔧 Error en createDentist:', error);
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

    // Agregá estas funciones a la clase DentistUI en dentists.js
    static async showEditForm(dentistId) {
        try {
            const dentist = await DentistService.getDentistById(dentistId);

            // Llenar el formulario modal
            document.getElementById('editDentistId').value = dentist.id;
            document.getElementById('editRegistration').value = dentist.registration;
            document.getElementById('editName').value = dentist.name;
            document.getElementById('editLastName').value = dentist.lastName;

            // Mostrar el modal
            $('#editDentistModal').modal('show');

        } catch (error) {
            this.showAlert('Error al cargar odontólogo: ' + error.message, 'danger');
        }
    }

    static async saveDentistEdit() {
        const dentistId = document.getElementById('editDentistId').value;
        const dentistData = {
            registration: document.getElementById('editRegistration').value,
            name: document.getElementById('editName').value,
            lastName: document.getElementById('editLastName').value
        };

        try {
            await DentistService.updateDentist(dentistId, dentistData);
            this.showAlert('✅ Odontólogo actualizado exitosamente', 'success');
            $('#editDentistModal').modal('hide');
            loadDentists(); // Recargar la lista
        } catch (error) {
            this.showAlert('❌ Error al actualizar odontólogo: ' + error.message, 'danger');
        }
    }
}