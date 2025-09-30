package com.augusto.__ClinicaOdontologicaSpringJPA.mapper;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Address;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import org.springframework.stereotype.Component;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.AddressDTOs.AddressDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientCreateDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.PatientDTOs.PatientResponseDTO;


@Component
public class PatientMapper {

    // Convierte Patient Entity → PatientResponseDTO
    public PatientResponseDTO toResponseDTO(Patient patient) {
        if (patient == null) {
            return null;
        }

        // Convertir Address a AddressDTO
        AddressDTO addressDTO = null;
        if (patient.getAddress() != null) {
            addressDTO = new AddressDTO(
                    patient.getAddress().getId(),
                    patient.getAddress().getStreet(),
                    patient.getAddress().getLocation(),
                    patient.getAddress().getProvince()
            );
        }

        return new PatientResponseDTO(
                patient.getId(),
                patient.getName(),
                patient.getLastName(),
                patient.getCardIdentity(),
                patient.getAdmissionOfDate(),
                addressDTO
        );
    }

    // Convierte PatientCreateDTO → Patient Entity
    public Patient toEntity(PatientCreateDTO patientCreateDTO) {
        if (patientCreateDTO == null) {
            return null;
        }

        Patient patient = new Patient();
        patient.setName(patientCreateDTO.getName());
        patient.setLastName(patientCreateDTO.getLastName());
        patient.setCardIdentity(patientCreateDTO.getCardIdentity());
        patient.setAdmissionOfDate(patientCreateDTO.getAdmissionOfDate());

        // Convertir AddressCreateDTO → Address Entity
        if (patientCreateDTO.getAddress() != null) {
            Address address = new Address();
            address.setStreet(patientCreateDTO.getAddress().getStreet());
            address.setLocation(patientCreateDTO.getAddress().getLocation());
            address.setProvince(patientCreateDTO.getAddress().getProvince());
            patient.setAddress(address);
        }

        return patient;
    }

    // Actualiza un Patient existente con datos de DTO
    public void updateEntityFromDTO(PatientCreateDTO patientCreateDTO, Patient patient) {
        if (patientCreateDTO == null || patient == null) {
            return;
        }

        if (patientCreateDTO.getName() != null) {
            patient.setName(patientCreateDTO.getName());
        }
        if (patientCreateDTO.getLastName() != null) {
            patient.setLastName(patientCreateDTO.getLastName());
        }
        if (patientCreateDTO.getCardIdentity() != null) {
            patient.setCardIdentity(patientCreateDTO.getCardIdentity());
        }
        if (patientCreateDTO.getAdmissionOfDate() != null) {
            patient.setAdmissionOfDate(patientCreateDTO.getAdmissionOfDate());
        }

        // Actualizar address
        if (patientCreateDTO.getAddress() != null) {
            if (patient.getAddress() == null) {
                patient.setAddress(new Address());
            }
            patient.getAddress().setStreet(patientCreateDTO.getAddress().getStreet());
            patient.getAddress().setLocation(patientCreateDTO.getAddress().getLocation());
            patient.getAddress().setProvince(patientCreateDTO.getAddress().getProvince());
        }
    }
}