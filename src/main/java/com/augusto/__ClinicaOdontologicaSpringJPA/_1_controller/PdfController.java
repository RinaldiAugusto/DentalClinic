package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPdfService;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.AppointmentRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.PatientRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/pdf")
public class PdfController {

    private static final Logger logger = LoggerFactory.getLogger(PdfController.class);

    @Autowired
    private IPdfService pdfService;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @GetMapping("/patient-report/{patientId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<byte[]> generatePatientReport(@PathVariable Long patientId) {
        try {
            logger.info("📄 SOLICITANDO REPORTE DE PACIENTE - ID: {}", patientId);

            Optional<Patient> patientOpt = patientRepository.findById(patientId);
            if (patientOpt.isEmpty()) {
                logger.warn("⚠️ PACIENTE NO ENCONTRADO - ID: {}", patientId);
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = pdfService.generatePatientReport(patientOpt.get());

            logger.info("✅ REPORTE DE PACIENTE GENERADO - ID: {}, Tamaño: {} bytes", patientId, pdfBytes.length);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=reporte-paciente-" + patientId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            logger.error("❌ ERROR GENERANDO REPORTE DE PACIENTE - ID: {}, Error: {}", patientId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/appointment-receipt/{appointmentId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<byte[]> generateAppointmentReceipt(@PathVariable Long appointmentId) {
        try {
            logger.info("🎫 SOLICITANDO COMPROBANTE DE TURNO - ID: {}", appointmentId);

            Optional<Appointment> appointmentOpt = appointmentRepository.findById(appointmentId);
            if (appointmentOpt.isEmpty()) {
                logger.warn("⚠️ TURNO NO ENCONTRADO - ID: {}", appointmentId);
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = pdfService.generateAppointmentReceipt(appointmentOpt.get());

            logger.info("✅ COMPROBANTE DE TURNO GENERADO - ID: {}, Tamaño: {} bytes", appointmentId, pdfBytes.length);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comprobante-turno-" + appointmentId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            logger.error("❌ ERROR GENERANDO COMPROBANTE DE TURNO - ID: {}, Error: {}", appointmentId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/medical-certificate/{patientId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<byte[]> generateMedicalCertificate(
            @PathVariable Long patientId,
            @RequestBody MedicalCertificateRequest request) {

        try {
            logger.info("🏥 SOLICITANDO CERTIFICADO MÉDICO - Paciente ID: {}", patientId);

            Optional<Patient> patientOpt = patientRepository.findById(patientId);
            if (patientOpt.isEmpty()) {
                logger.warn("⚠️ PACIENTE NO ENCONTRADO - ID: {}", patientId);
                return ResponseEntity.notFound().build();
            }

            byte[] pdfBytes = pdfService.generateMedicalCertificate(
                    patientOpt.get(),
                    request.getDiagnosis(),
                    request.getTreatment()
            );

            logger.info("✅ CERTIFICADO MÉDICO GENERADO - Paciente ID: {}, Tamaño: {} bytes", patientId, pdfBytes.length);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=certificado-medico-" + patientId + ".pdf")
                    .contentType(MediaType.APPLICATION_PDF)
                    .body(pdfBytes);

        } catch (Exception e) {
            logger.error("❌ ERROR GENERANDO CERTIFICADO MÉDICO - Paciente ID: {}, Error: {}", patientId, e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // DTO interno para el request del certificado médico
    public static class MedicalCertificateRequest {
        private String diagnosis;
        private String treatment;

        // Getters y Setters
        public String getDiagnosis() { return diagnosis; }
        public void setDiagnosis(String diagnosis) { this.diagnosis = diagnosis; }

        public String getTreatment() { return treatment; }
        public void setTreatment(String treatment) { this.treatment = treatment; }
    }
}