package com.augusto.__ClinicaOdontologicaSpringJPA._3_repository;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

}
