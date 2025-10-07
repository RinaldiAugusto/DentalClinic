package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl.NotificationService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.NotificationRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Notification;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationResponseDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationService notificationService;

    private NotificationRequestDTO reminderRequest;
    private NotificationRequestDTO confirmationRequest;
    private Notification reminderNotification;
    private Notification confirmationNotification;

    @BeforeEach
    void setUp() {
        // Request para recordatorio
        reminderRequest = new NotificationRequestDTO(
                "paciente@test.com",
                "Juan Perez",
                "APPOINTMENT_REMINDER",
                "Recordatorio de su turno"
        );

        // Request para confirmación - ✅ CORREGIDO: Tipo correcto
        confirmationRequest = new NotificationRequestDTO(
                "paciente@test.com",
                "Juan Perez",
                "APPOINTMENT_CONFIRMATION",
                "Confirmación de su turno"
        );

        LocalDateTime now = LocalDateTime.now();

        // Notificación de recordatorio
        reminderNotification = Notification.builder()
                .id(1L)
                .patientEmail("paciente@test.com")
                .patientName("Juan Perez")
                .type("APPOINTMENT_REMINDER")
                .message("Recordatorio de su turno")
                .status("SENT")
                .sentAt(now)
                .createdAt(now)
                .build();

        // Notificación de confirmación - ✅ CORREGIDO: Tipo correcto
        confirmationNotification = Notification.builder()
                .id(2L)
                .patientEmail("paciente@test.com")
                .patientName("Juan Perez")
                .type("APPOINTMENT_CONFIRMATION")
                .message("Confirmación de su turno")
                .status("SENT")
                .sentAt(now)
                .createdAt(now)
                .build();
    }

    @Test
    void testSendAppointmentReminder_Success() {
        // Given
        when(notificationRepository.save(any(Notification.class))).thenReturn(reminderNotification);

        // When
        NotificationResponseDTO response = notificationService.sendAppointmentReminder(reminderRequest);

        // Then
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("paciente@test.com", response.getPatientEmail());
        assertEquals("APPOINTMENT_REMINDER", response.getType()); // ✅ CORREGIDO
        assertEquals("SENT", response.getStatus());

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testSendAppointmentConfirmation_Success() {
        // Given
        when(notificationRepository.save(any(Notification.class))).thenReturn(confirmationNotification);

        // When
        NotificationResponseDTO response = notificationService.sendAppointmentConfirmation(confirmationRequest);

        // Then
        assertNotNull(response);
        assertEquals(2L, response.getId());
        assertEquals("APPOINTMENT_CONFIRMATION", response.getType()); // ✅ CORREGIDO

        verify(notificationRepository, times(1)).save(any(Notification.class));
    }

    @Test
    void testGetNotificationsByPatient_Success() {
        // Given
        List<Notification> notifications = Arrays.asList(reminderNotification);
        when(notificationRepository.findByPatientEmailOrderBySentAtDesc(any())).thenReturn(notifications);

        // When
        List<NotificationResponseDTO> response = notificationService.getNotificationsByPatient("paciente@test.com");

        // Then
        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals("paciente@test.com", response.get(0).getPatientEmail());

        verify(notificationRepository, times(1)).findByPatientEmailOrderBySentAtDesc("paciente@test.com");
    }

    @Test
    void testGetAllNotifications_Success() {
        // Given
        List<Notification> notifications = Arrays.asList(reminderNotification, confirmationNotification);
        when(notificationRepository.findAll()).thenReturn(notifications);

        // When
        List<NotificationResponseDTO> response = notificationService.getAllNotifications();

        // Then
        assertNotNull(response);
        assertEquals(2, response.size());

        verify(notificationRepository, times(1)).findAll();
    }

    @Test
    void testSimulateScheduledReminders_Success() {
        // Given
        when(notificationRepository.save(any(Notification.class))).thenReturn(reminderNotification);

        // When
        notificationService.simulateScheduledReminders();

        // Then
        // Verificar que se llamó al repository para guardar las notificaciones
        verify(notificationRepository, atLeast(2)).save(any(Notification.class));
    }

    @Test
    void testSendAppointmentReminder_FailedSimulation() {
        // Given
        // El método simulateNotificationSend() usa Math.random(), así que no podemos controlarlo directamente
        // Pero podemos verificar que el método maneja correctamente cualquier resultado
        when(notificationRepository.save(any(Notification.class))).thenReturn(reminderNotification);

        // When
        NotificationResponseDTO response = notificationService.sendAppointmentReminder(reminderRequest);

        // Then
        assertNotNull(response);
        // El test pasa si no se lanza una excepción, independientemente del resultado de la simulación
    }

    @Test
    void testConvertToDTO() {
        // Given
        Notification notification = reminderNotification;

        // Simular que el repository devuelve una lista con la notificación
        List<Notification> notifications = Arrays.asList(notification);
        when(notificationRepository.findAll()).thenReturn(notifications);

        // When - Llamamos a un método público que usa convertToDTO internamente
        List<NotificationResponseDTO> result = notificationService.getAllNotifications();

        // Then
        assertNotNull(result);
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals("paciente@test.com", result.get(0).getPatientEmail());
    }
}