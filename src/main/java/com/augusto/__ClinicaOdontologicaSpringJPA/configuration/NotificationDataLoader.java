package com.augusto.__ClinicaOdontologicaSpringJPA.configuration;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.INotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotificationDataLoader implements CommandLineRunner {

    @Autowired
    private INotificationService notificationService;

    @Override
    public void run(String... args) throws Exception {
        // Ejecutar recordatorios programados al iniciar la aplicación
        System.out.println("🚀 Iniciando sistema de notificaciones...");
        notificationService.simulateScheduledReminders();
    }
}