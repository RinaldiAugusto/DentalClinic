package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.StatsDTOs.StatsResponseDTO;
import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.IStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stats")
public class StatsController {

    @Autowired
    private IStatsService statsService;

    @GetMapping("/patients-monthly")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatsResponseDTO> getPatientsMonthly() {
        StatsResponseDTO stats = statsService.getPatientsMonthlyStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/appointments-today")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<StatsResponseDTO> getAppointmentsToday() {
        StatsResponseDTO stats = statsService.getAppointmentsTodayStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/top-dentists")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<StatsResponseDTO> getTopDentists() {
        StatsResponseDTO stats = statsService.getTopDentistsStats();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/overview")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<StatsResponseDTO> getOverview() {
        StatsResponseDTO stats = statsService.getOverviewStats();
        return ResponseEntity.ok(stats);
    }
}