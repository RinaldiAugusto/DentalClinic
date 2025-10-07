package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IPdfService;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Patient;
import com.augusto.__ClinicaOdontologicaSpringJPA._4_entity.Appointment;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService implements IPdfService {

    private static final Font TITLE_FONT = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
    private static final Font SUBTITLE_FONT = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);
    private static final Font NORMAL_FONT = new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);

    @Override
    public byte[] generatePatientReport(Patient patient) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Título
            addTitle(document, "REPORTE DE PACIENTE");

            // Información del paciente
            addSubtitle(document, "Información Personal");
            addKeyValue(document, "Nombre:", patient.getName() + " " + patient.getLastName());
            addKeyValue(document, "DNI:", patient.getCardIdentity() != null ? patient.getCardIdentity() : "No especificado");

            // ✅ CORREGIDO: Manejar fecha NULL
            String admissionDate = (patient.getAdmissionOfDate() != null)
                    ? patient.getAdmissionOfDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "No especificada";
            addKeyValue(document, "Fecha de Ingreso:", admissionDate);

            // Espacio
            document.add(Chunk.NEWLINE);

            // Información de contacto
            addSubtitle(document, "Información de Contacto");
            if (patient.getAddress() != null) {
                String street = patient.getAddress().getStreet() != null ? patient.getAddress().getStreet() : "";
                String location = patient.getAddress().getLocation() != null ? patient.getAddress().getLocation() : "";
                String province = patient.getAddress().getProvince() != null ? patient.getAddress().getProvince() : "";

                addKeyValue(document, "Dirección:", street + ", " + location);
                addKeyValue(document, "Provincia:", province);
            } else {
                addKeyValue(document, "Dirección:", "No especificada");
            }

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando PDF de paciente: " + e.getMessage());
        }
    }

    @Override
    public byte[] generateAppointmentReceipt(Appointment appointment) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Título
            addTitle(document, "COMPROBANTE DE TURNO");

            // Información del turno
            addKeyValue(document, "Paciente:",
                    appointment.getPatient().getName() + " " + appointment.getPatient().getLastName());
            addKeyValue(document, "Odontólogo:",
                    appointment.getDentist().getName() + " " + appointment.getDentist().getLastName());

            // ✅ CORREGIDO: Manejar fecha NULL
            String appointmentDate = (appointment.getDate() != null)
                    ? appointment.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
                    : "No especificada";
            addKeyValue(document, "Fecha:", appointmentDate);

            addKeyValue(document, "Matrícula:",
                    String.valueOf(appointment.getDentist().getRegistration()));

            // Espacio
            document.add(Chunk.NEWLINE);

            // Footer
            Paragraph footer = new Paragraph("Clínica Odontológica - Este comprobante es válido como constancia de turno.",
                    new Font(Font.FontFamily.HELVETICA, 10, Font.ITALIC));
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando comprobante de turno: " + e.getMessage());
        }
    }

    @Override
    public byte[] generateMedicalCertificate(Patient patient, String diagnosis, String treatment) {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);

            document.open();

            // Título
            addTitle(document, "CERTIFICADO MÉDICO");

            // Información del paciente
            addSubtitle(document, "Datos del Paciente");
            addKeyValue(document, "Nombre:", patient.getName() + " " + patient.getLastName());
            addKeyValue(document, "DNI:", patient.getCardIdentity() != null ? patient.getCardIdentity() : "No especificado");

            // Espacio
            document.add(Chunk.NEWLINE);

            // Diagnóstico y tratamiento
            addSubtitle(document, "Evaluación Médica");
            addParagraph(document, "Diagnóstico: " + (diagnosis != null ? diagnosis : "No especificado"));
            addParagraph(document, "Tratamiento Indicado: " + (treatment != null ? treatment : "No especificado"));

            // Espacio
            document.add(Chunk.NEWLINE);

            // Firma
            Paragraph signature = new Paragraph("_________________________\nDr. Responsable\nMatrícula: 12345",
                    new Font(Font.FontFamily.HELVETICA, 10, Font.NORMAL));
            signature.setAlignment(Element.ALIGN_RIGHT);
            document.add(signature);

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error generando certificado médico: " + e.getMessage());
        }
    }

    // Métodos auxiliares para formatear el PDF (sin cambios)
    private void addTitle(Document document, String title) throws DocumentException {
        Paragraph titleParagraph = new Paragraph(title, TITLE_FONT);
        titleParagraph.setAlignment(Element.ALIGN_CENTER);
        titleParagraph.setSpacingAfter(20);
        document.add(titleParagraph);
    }

    private void addSubtitle(Document document, String subtitle) throws DocumentException {
        Paragraph subtitleParagraph = new Paragraph(subtitle, SUBTITLE_FONT);
        subtitleParagraph.setSpacingBefore(10);
        subtitleParagraph.setSpacingAfter(10);
        document.add(subtitleParagraph);
    }

    private void addKeyValue(Document document, String key, String value) throws DocumentException {
        Paragraph paragraph = new Paragraph();
        paragraph.setFont(NORMAL_FONT);
        paragraph.add(new Chunk(key, new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
        paragraph.add(new Chunk(" " + value));
        paragraph.setSpacingAfter(5);
        document.add(paragraph);
    }

    private void addParagraph(Document document, String text) throws DocumentException {
        Paragraph paragraph = new Paragraph(text, NORMAL_FONT);
        paragraph.setSpacingAfter(5);
        document.add(paragraph);
    }
}