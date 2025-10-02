package com.augusto.__ClinicaOdontologicaSpringJPA._2_service;

import com.augusto.__ClinicaOdontologicaSpringJPA.dto.StatsDTOs.StatsResponseDTO;

public interface IStatsService {
    StatsResponseDTO getPatientsMonthlyStats();
    StatsResponseDTO getAppointmentsTodayStats();
    StatsResponseDTO getTopDentistsStats();
    StatsResponseDTO getOverviewStats();
}