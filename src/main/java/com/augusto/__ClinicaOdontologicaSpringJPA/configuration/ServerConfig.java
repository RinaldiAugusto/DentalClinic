package com.augusto.__ClinicaOdontologicaSpringJPA.configuration;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
public class ServerConfig implements WebServerFactoryCustomizer<ConfigurableWebServerFactory> {

    @Override
    public void customize(ConfigurableWebServerFactory factory) {
        // Render provee el puerto via variable de entorno PORT
        String port = System.getenv("PORT");
        if (port != null) {
            factory.setPort(Integer.parseInt(port));
        }
    }
}