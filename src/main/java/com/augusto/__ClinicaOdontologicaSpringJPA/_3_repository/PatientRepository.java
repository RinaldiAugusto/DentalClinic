package com.augusto.__ClinicaOdontologicaSpringJPA._3_repository;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {

    @Query("SELECT p FROM Patient p WHERE p.lastName=?1")
    Optional<Patient> findByLastName(String lastName);

    Optional<Patient> findByName (String name);
}
