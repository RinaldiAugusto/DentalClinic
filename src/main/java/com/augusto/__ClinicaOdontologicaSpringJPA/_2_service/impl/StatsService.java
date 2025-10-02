package com.augusto.__ClinicaOdontologicaSpringJPA._2_service.impl;

import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.AppointmentRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.DentistRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA._3_repository.PatientRepository;
import com.augusto.__ClinicaOdontologicaSpringJPA.dto.StatsDTOs.StatsResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StatsService implements IStatsService {

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DentistRepository dentistRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    public StatsResponseDTO getPatientsMonthlyStats() {
        try {
            // Simulación - en un proyecto real usarías consultas JPA con dates
            Map<String, Integer> monthlyData = new HashMap<>();
            monthlyData.put("Enero", 15);
            monthlyData.put("Febrero", 22);
            monthlyData.put("Marzo", 18);
            monthlyData.put("Abril", 25);
            monthlyData.put("Mayo", 30);
            monthlyData.put("Junio", 28);

            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("totalYear", 138);
            additionalInfo.put("averageMonthly", 23);
            additionalInfo.put("growth", "+12%");

            return new StatsResponseDTO(
                    "Estadísticas mensuales de pacientes registrados",
                    monthlyData,
                    additionalInfo
            );

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo estadísticas mensuales: " + e.getMessage());
        }
    }

    @Override
    public StatsResponseDTO getAppointmentsTodayStats() {
        try {
            // Simulación - en un proyecto real buscarías en la base de datos
            LocalDate today = LocalDate.now();

            Map<String, Object> todayStats = new HashMap<>();
            todayStats.put("date", today.toString());
            todayStats.put("totalAppointments", 8);
            todayStats.put("completed", 5);
            todayStats.put("pending", 3);
            todayStats.put("cancelled", 0);

            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("nextAppointment", "14:30 - Dr. García");
            additionalInfo.put("busiestHour", "10:00-11:00");

            return new StatsResponseDTO(
                    "Estadísticas de turnos para hoy",
                    todayStats,
                    additionalInfo
            );

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo estadísticas de turnos: " + e.getMessage());
        }
    }

    @Override
    public StatsResponseDTO getTopDentistsStats() {
        try {
            // Simulación - en un proyecto real usarías consultas agrupadas
            List<Map<String, Object>> topDentists = List.of(
                    Map.of("dentistName", "Dr. García", "appointments", 45, "specialty", "Ortodoncia"),
                    Map.of("dentistName", "Dra. Martínez", "appointments", 38, "specialty", "Endodoncia"),
                    Map.of("dentistName", "Dr. López", "appointments", 32, "specialty", "Periodoncia"),
                    Map.of("dentistName", "Dra. Rodríguez", "appointments", 28, "specialty", "Estética")
            );

            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("totalDentists", 12);
            additionalInfo.put("averageAppointments", 28.5);

            return new StatsResponseDTO(
                    "Dentistas más solicitados",
                    topDentists,
                    additionalInfo
            );

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo top dentistas: " + e.getMessage());
        }
    }

    @Override
    public StatsResponseDTO getOverviewStats() {
        try {
            // Estadísticas generales del sistema
            Map<String, Object> overview = new HashMap<>();
            overview.put("totalPatients", patientRepository.count());
            overview.put("totalDentists", dentistRepository.count());
            overview.put("totalAppointments", appointmentRepository.count());
            overview.put("activeToday", 8);
            overview.put("monthlyRevenue", 125000.00);

            Map<String, Object> additionalInfo = new HashMap<>();
            additionalInfo.put("systemUptime", "99.8%");
            additionalInfo.put("lastUpdate", LocalDate.now().toString());

            return new StatsResponseDTO(
                    "Resumen general del sistema",
                    overview,
                    additionalInfo
            );

        } catch (Exception e) {
            throw new RuntimeException("Error obteniendo resumen general: " + e.getMessage());
        }
    }
}