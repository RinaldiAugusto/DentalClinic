package com.augusto.__ClinicaOdontologicaSpringJPA.configuration;

import com.augusto.__ClinicaOdontologicaSpringJPA._2_service.INotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NotificationDataLoader implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NotificationDataLoader.class);

    @Autowired
    private INotificationService notificationService;

    @Override
    public void run(String... args) throws Exception {
        // ✅ COMENTADO - No ejecutar notificaciones automáticas al iniciar
        // logger.info("🚀 INICIANDO SISTEMA DE NOTIFICACIONES - Cargando recordatorios automáticos...");
        // Thread.sleep(3000);
        // notificationService.simulateScheduledReminders();
        // logger.info("✅ SISTEMA DE NOTIFICACIONES INICIALIZADO CORRECTAMENTE - Recordatorios cargados");

        logger.info("🔕 SISTEMAS LISTOS");
    }
}