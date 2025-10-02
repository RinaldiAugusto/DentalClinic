package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationRequestDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs.NotificationResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private INotificationService notificationService;

    @PostMapping("/appointment-reminder")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<NotificationResponseDTO> sendAppointmentReminder(@RequestBody NotificationRequestDTO request) {
        NotificationResponseDTO response = notificationService.sendAppointmentReminder(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/appointment-confirmation")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<NotificationResponseDTO> sendAppointmentConfirmation(@RequestBody NotificationRequestDTO request) {
        NotificationResponseDTO response = notificationService.sendAppointmentConfirmation(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/patient/{patientEmail}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<List<NotificationResponseDTO>> getNotificationsByPatient(@PathVariable String patientEmail) {
        List<NotificationResponseDTO> notifications = notificationService.getNotificationsByPatient(patientEmail);
        return ResponseEntity.ok(notifications);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<NotificationResponseDTO>> getAllNotifications() {
        List<NotificationResponseDTO> notifications = notificationService.getAllNotifications();
        return ResponseEntity.ok(notifications);
    }

    @PostMapping("/simulate-scheduled")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> simulateScheduledReminders() {
        notificationService.simulateScheduledReminders();
        return ResponseEntity.ok("Recordatorios programados ejecutados correctamente");
    }
}