package com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs;

import java.time.LocalDateTime;

public class NotificationResponseDTO {
    private Long id;
    private String patientEmail;
    private String patientName;
    private String type;
    private String message;
    private String status;
    private LocalDateTime sentAt;
    private LocalDateTime createdAt;

    // Constructores
    public NotificationResponseDTO() {}

    public NotificationResponseDTO(Long id, String patientEmail, String patientName, String type,
                                   String message, String status, LocalDateTime sentAt, LocalDateTime createdAt) {
        this.id = id;
        this.patientEmail = patientEmail;
        this.patientName = patientName;
        this.type = type;
        this.message = message;
        this.status = status;
        this.sentAt = sentAt;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}