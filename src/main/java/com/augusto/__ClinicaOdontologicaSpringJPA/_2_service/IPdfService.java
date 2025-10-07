package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;

public interface IPdfService {
    byte[] generatePatientReport(Patient patient);
    byte[] generateAppointmentReceipt(Appointment appointment);
    byte[] generateMedicalCertificate(Patient patient, String diagnosis, String treatment);
}