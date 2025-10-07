package com.augusto.__ClinicaOdontologicaSpringJPA._1_controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/public")
public class PublicController {

    @GetMapping("/health")
    public String healthCheck() {
        return "✅ Dental Clinic Backend is running! 🦷 - " + LocalDateTime.now();
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        info.put("name", "Dental Clinic Backend");
        info.put("version", "1.0.0");
        info.put("status", "running");
        info.put("timestamp", LocalDateTime.now().toString());
        info.put("database", "PostgreSQL");
        info.put("framework", "Spring Boot 3");
        return info;
    }

    @GetMapping("/test")
    public String test() {
        return """
               <html>
                 <body style="font-family: Arial, sans-serif; text-align: center; padding: 50px;">
                   <h1>🦷 Dental Clinic Backend</h1>
                   <p>✅ <strong>Status:</strong> Running</p>
                   <p>🚀 <strong>Deployed:</strong> Render.com</p>
                   <p>🗄️ <strong>Database:</strong> PostgreSQL</p>
                   <p>🔐 <strong>Security:</strong> JWT Authentication</p>
                   <p>📚 <strong>API Docs:</strong> <a href="/swagger-ui.html">Swagger</a></p>
                 </body>
               </html>
               """;
    }
}