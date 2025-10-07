package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl.PdfService;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Dentist;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Address;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PdfServiceTest {

    @InjectMocks
    private PdfService pdfService;

    private Patient patient;
    private Appointment appointment;

    @BeforeEach
    void setUp() {
        Address address = new Address();
        address.setStreet("Calle Falsa");
        address.setLocation("Springfield");
        address.setProvince("Buenos Aires");

        patient = new Patient();
        patient.setId(1L);
        patient.setName("Juan");
        patient.setLastName("Perez");
        patient.setCardIdentity("12345678");
        patient.setAdmissionOfDate(LocalDate.of(2024, 1, 15));
        patient.setAddress(address);

        Dentist dentist = new Dentist();
        dentist.setName("Dr. García");
        dentist.setLastName("Lopez");
        dentist.setRegistration(12345);

        appointment = new Appointment();
        appointment.setId(1L);
        appointment.setPatient(patient);
        appointment.setDentist(dentist);
        appointment.setDate(LocalDate.of(2024, 1, 20));
    }

    @Test
    void testGeneratePatientReport_Success() {
        // When
        byte[] pdfBytes = pdfService.generatePatientReport(patient);

        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }

    @Test
    void testGenerateAppointmentReceipt_Success() {
        // When
        byte[] pdfBytes = pdfService.generateAppointmentReceipt(appointment);

        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }

    @Test
    void testGenerateMedicalCertificate_Success() {
        // When
        byte[] pdfBytes = pdfService.generateMedicalCertificate(
                patient,
                "Caries dental",
                "Limpieza profesional"
        );

        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
    }

    @Test
    void testGeneratePatientReport_WithNullFields() {
        // Given
        Patient patientWithNulls = new Patient();
        patientWithNulls.setName("Maria");
        patientWithNulls.setLastName("Lopez");
        // admissionOfDate is null
        // address is null

        // When
        byte[] pdfBytes = pdfService.generatePatientReport(patientWithNulls);

        // Then
        assertNotNull(pdfBytes);
        assertTrue(pdfBytes.length > 0);
        // Should not throw exception
    }
}