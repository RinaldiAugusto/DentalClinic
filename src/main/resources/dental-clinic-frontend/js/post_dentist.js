// js/dentists.js - Versión adaptada
window.addEventListener('load', function () {
    if (!AuthService.isAuthenticated()) {
        window.location.href = 'login.html';
        return;
    }

    const formulario = document.querySelector('#add_new_dentist');
    formulario.addEventListener('submit', async function (event) {
        event.preventDefault();

        const formData = {
            registration: document.querySelector('#registration').value,
            name: document.querySelector('#name').value,
            lastName: document.querySelector('#lastName').value,
        };

        try {
            const response = await AuthService.makeAuthenticatedRequest(
                `${CONFIG.API_BASE_URL}${CONFIG.ENDPOINTS.DENTISTS}`,
                {
                    method: 'POST',
                    body: JSON.stringify(formData)
                }
            );

            if (response.ok) {
                showAlert('Odontólogo agregado exitosamente', 'success');
                resetUploadForm();
            } else {
                throw new Error('Error al agregar odontólogo');
            }
        } catch (error) {
            showAlert('Error: ' + error.message, 'danger');
        }
    });
});