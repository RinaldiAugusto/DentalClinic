package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.NotificationRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Notification;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public NotificationResponseDTO sendAppointmentReminder(NotificationRequestDTO request) {
        try {
            System.out.println("🔔 Enviando recordatorio a: " + request.getPatientEmail());

            // Simular envío de email/SMS
            boolean sentSuccessfully = simulateNotificationSend();

            Notification notification = Notification.builder()
                    .patientEmail(request.getPatientEmail())
                    .patientName(request.getPatientName())
                    .type("APPOINTMENT_REMINDER")
                    .message(request.getMessage())
                    .status(sentSuccessfully ? "SENT" : "FAILED")
                    .sentAt(LocalDateTime.now())
                    .build();

            Notification savedNotification = notificationRepository.save(notification);

            System.out.println("✅ Recordatorio " + (sentSuccessfully ? "enviado" : "falló") +
                    " para: " + request.getPatientEmail());

            return convertToDTO(savedNotification);

        } catch (Exception e) {
            System.out.println("❌ Error enviando recordatorio: " + e.getMessage());
            throw new RuntimeException("Error enviando notificación: " + e.getMessage());
        }
    }

    @Override
    public NotificationResponseDTO sendAppointmentConfirmation(NotificationRequestDTO request) {
        try {
            System.out.println("✅ Enviando confirmación a: " + request.getPatientEmail());

            // Simular envío de email/SMS
            boolean sentSuccessfully = simulateNotificationSend();

            Notification notification = Notification.builder()
                    .patientEmail(request.getPatientEmail())
                    .patientName(request.getPatientName())
                    .type("APPOINTMENT_CONFIRMATION")
                    .message(request.getMessage())
                    .status(sentSuccessfully ? "SENT" : "FAILED")
                    .sentAt(LocalDateTime.now())
                    .build();

            Notification savedNotification = notificationRepository.save(notification);

            System.out.println("✅ Confirmación " + (sentSuccessfully ? "enviada" : "falló") +
                    " para: " + request.getPatientEmail());

            return convertToDTO(savedNotification);

        } catch (Exception e) {
            System.out.println("❌ Error enviando confirmación: " + e.getMessage());
            throw new RuntimeException("Error enviando notificación: " + e.getMessage());
        }
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByPatient(String patientEmail) {
        try {
            List<Notification> notifications = notificationRepository.findByPatientEmailOrderBySentAtDesc(patientEmail);
            return notifications.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo notificaciones: " + e.getMessage());
        }
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {
        try {
            List<Notification> notifications = notificationRepository.findAll();
            return notifications.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo todas las notificaciones: " + e.getMessage());
        }
    }

    @Override
    public void simulateScheduledReminders() {
        try {
            System.out.println("⏰ Ejecutando recordatorios programados...");

            // Simular algunos recordatorios automáticos
            NotificationRequestDTO reminder1 = new NotificationRequestDTO(
                    "paciente1@email.com",
                    "Juan Pérez",
                    "APPOINTMENT_REMINDER",
                    "Recordatorio: Su turno es mañana a las 10:00 AM con el Dr. García"
            );
            sendAppointmentReminder(reminder1);

            NotificationRequestDTO reminder2 = new NotificationRequestDTO(
                    "paciente2@email.com",
                    "María López",
                    "APPOINTMENT_REMINDER",
                    "Recordatorio: Su turno es hoy a las 15:30 PM con la Dra. Martínez"
            );
            sendAppointmentReminder(reminder2);

            System.out.println("✅ Recordatorios programados completados");

        } catch (Exception e) {
            System.out.println("❌ Error en recordatorios programados: " + e.getMessage());
        }
    }

    // Método auxiliar para simular envío (90% éxito)
    private boolean simulateNotificationSend() {
        return Math.random() > 0.1; // 90% de éxito
    }

    // Convertir Entity a DTO
    private NotificationResponseDTO convertToDTO(Notification notification) {
        return new NotificationResponseDTO(
                notification.getId(),
                notification.getPatientEmail(),
                notification.getPatientName(),
                notification.getType(),
                notification.getMessage(),
                notification.getStatus(),
                notification.getSentAt(),
                notification.getCreatedAt()
        );
    }
}