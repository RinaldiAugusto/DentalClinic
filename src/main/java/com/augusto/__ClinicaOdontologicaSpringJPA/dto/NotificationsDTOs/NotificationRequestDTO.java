package com.augusto.__ClinicaOdontologicaSpringJPA.dto.NotificationsDTOs;

public class NotificationRequestDTO {
    private String patientEmail;
    private String patientName;
    private String type;
    private String message;

    // Constructores
    public NotificationRequestDTO() {}

    public NotificationRequestDTO(String patientEmail, String patientName, String type, String message) {
        this.patientEmail = patientEmail;
        this.patientName = patientName;
        this.type = type;
        this.message = message;
    }

    // Getters y Setters
    public String getPatientEmail() { return patientEmail; }
    public void setPatientEmail(String patientEmail) { this.patientEmail = patientEmail; }

    public String getPatientName() { return patientName; }
    public void setPatientName(String patientName) { this.patientName = patientName; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}