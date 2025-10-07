package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.NotificationRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Notification;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService implements INotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    @Autowired
    private NotificationRepository notificationRepository;

    @Override
    public NotificationResponseDTO sendAppointmentReminder(NotificationRequestDTO request) {
        logger.info("📧 ENVIANDO RECORDATORIO - Destinatario: {}, Tipo: APPOINTMENT_REMINDER",
                request.getPatientEmail());

        try {
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

            if (sentSuccessfully) {
                logger.info("✅ RECORDATORIO ENVIADO EXITOSAMENTE - ID: {}, Paciente: {}",
                        savedNotification.getId(), request.getPatientEmail());
            } else {
                logger.warn("⚠️ RECORDATORIO FALLÓ - ID: {}, Paciente: {}",
                        savedNotification.getId(), request.getPatientEmail());
            }

            return convertToDTO(savedNotification);

        } catch (Exception e) {
            logger.error("❌ ERROR ENVIANDO RECORDATORIO - Paciente: {}, Error: {}",
                    request.getPatientEmail(), e.getMessage(), e);
            throw new RuntimeException("Error enviando notificación: " + e.getMessage());
        }
    }

    @Override
    public NotificationResponseDTO sendAppointmentConfirmation(NotificationRequestDTO request) {
        logger.info("✅ ENVIANDO CONFIRMACIÓN - Destinatario: {}, Tipo: APPOINTMENT_CONFIRMATION",
                request.getPatientEmail());

        try {
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

            if (sentSuccessfully) {
                logger.info("✅ CONFIRMACIÓN ENVIADA EXITOSAMENTE - ID: {}, Paciente: {}",
                        savedNotification.getId(), request.getPatientEmail());
            } else {
                logger.warn("⚠️ CONFIRMACIÓN FALLÓ - ID: {}, Paciente: {}",
                        savedNotification.getId(), request.getPatientEmail());
            }

            return convertToDTO(savedNotification);

        } catch (Exception e) {
            logger.error("❌ ERROR ENVIANDO CONFIRMACIÓN - Paciente: {}, Error: {}",
                    request.getPatientEmail(), e.getMessage(), e);
            throw new RuntimeException("Error enviando notificación: " + e.getMessage());
        }
    }

    @Override
    public List<NotificationResponseDTO> getNotificationsByPatient(String patientEmail) {
        logger.info("📋 CONSULTANDO NOTIFICACIONES - Paciente: {}", patientEmail);

        try {
            List<Notification> notifications = notificationRepository.findByPatientEmailOrderBySentAtDesc(patientEmail);

            logger.info("✅ CONSULTA DE NOTIFICACIONES COMPLETADA - Paciente: {}, Total: {}",
                    patientEmail, notifications.size());

            return notifications.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("❌ ERROR CONSULTANDO NOTIFICACIONES - Paciente: {}, Error: {}",
                    patientEmail, e.getMessage(), e);
            throw new RuntimeException("Error obteniendo notificaciones: " + e.getMessage());
        }
    }

    @Override
    public List<NotificationResponseDTO> getAllNotifications() {
        logger.info("📊 CONSULTANDO TODAS LAS NOTIFICACIONES - Iniciando consulta global");

        try {
            List<Notification> notifications = notificationRepository.findAll();

            logger.info("✅ CONSULTA GLOBAL COMPLETADA - Total de notificaciones: {}", notifications.size());

            return notifications.stream()
                    .map(this::convertToDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("❌ ERROR CONSULTANDO NOTIFICACIONES GLOBALES - Error: {}", e.getMessage(), e);
            throw new RuntimeException("Error obteniendo todas las notificaciones: " + e.getMessage());
        }
    }

    @Override
    public void simulateScheduledReminders() {
        logger.info("⏰ EJECUTANDO RECORDATORIOS PROGRAMADOS - Iniciando proceso automático...");

        try {
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

            logger.info("🎯 RECORDATORIOS PROGRAMADOS COMPLETADOS - Procesados 2 notificaciones automáticas");

        } catch (Exception e) {
            logger.error("❌ ERROR EN RECORDATORIOS PROGRAMADOS - Detalle: {}", e.getMessage(), e);
        }
    }

    // Método auxiliar para simular envío (90% éxito)
    private boolean simulateNotificationSend() {
        boolean success = Math.random() > 0.1; // 90% de éxito
        logger.debug("🤖 SIMULACIÓN DE ENVÍO - Resultado: {}", success ? "ÉXITO" : "FALLO");
        return success;
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