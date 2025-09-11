package com.augusto.__ClinicaOdontologicaSpringJPA._3_repository;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DentistRepository extends JpaRepository<Dentist, Long> {

}
