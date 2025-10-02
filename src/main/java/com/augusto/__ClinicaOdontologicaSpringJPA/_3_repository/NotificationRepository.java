package com.augusto.__ClinicaOdontologicaSpringJPA._3_repository;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByPatientEmailOrderBySentAtDesc(String patientEmail);
    List<Notification> findByStatus(String status);
    List<Notification> findByTypeOrderBySentAtDesc(String type);
}