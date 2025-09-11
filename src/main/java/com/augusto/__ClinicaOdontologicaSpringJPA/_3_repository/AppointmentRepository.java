package com.augusto.__ClinicaOdontologicaSpringJPA._3_repository;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
}
