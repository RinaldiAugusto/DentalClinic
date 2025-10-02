package com.augusto.__ClinicaOdontologicaSpringJPA._4_entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String patientEmail;
    private String patientName;
    private String type; // APPOINTMENT_REMINDER, APPOINTMENT_CONFIRMATION, etc.
    private String message;
    private String status; // SENT, FAILED, PENDING

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime sentAt;

    @Column(columnDefinition = "TIMESTAMP")
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}