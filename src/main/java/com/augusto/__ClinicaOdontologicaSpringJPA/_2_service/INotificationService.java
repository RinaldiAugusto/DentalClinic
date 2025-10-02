package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationResponseDTO;

import java.util.List;

public interface INotificationService {
    NotificationResponseDTO sendAppointmentReminder(NotificationRequestDTO request);
    NotificationResponseDTO sendAppointmentConfirmation(NotificationRequestDTO request);
    List<NotificationResponseDTO> getNotificationsByPatient(String patientEmail);
    List<NotificationResponseDTO> getAllNotifications();
    void simulateScheduledReminders();
}