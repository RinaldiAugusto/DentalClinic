package com.augusto.__ClinicaOdontologicaSpringJPA.dto.StatsDTOs;

import java.util.Map;

public class StatsResponseDTO {
    private String message;
    private Object data;
    private Map<String, Object> additionalInfo;

    // Constructores
    public StatsResponseDTO() {}

    public StatsResponseDTO(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public StatsResponseDTO(String message, Object data, Map<String, Object> additionalInfo) {
        this.message = message;
        this.data = data;
        this.additionalInfo = additionalInfo;
    }

    // Getters y Setters
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public Object getData() { return data; }
    public void setData(Object data) { this.data = data; }

    public Map<String, Object> getAdditionalInfo() { return additionalInfo; }
    public void setAdditionalInfo(Map<String, Object> additionalInfo) { this.additionalInfo = additionalInfo; }
}