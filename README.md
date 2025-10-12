# 🦷 Dental Clinic Management System - Backend

## 📋 Descripción del Proyecto
Sistema completo de gestión para clínica odontológica desarrollado con **Java Spring Boot**, que proporciona una API RESTful para administrar pacientes, odontólogos, turnos y notificaciones.

---

## 🚀 Características Técnicas

### Backend
- **Framework**: Spring Boot 3.x
- **Java**: 17+
- **Base de Datos**: H2 (desarrollo) / PostgreSQL (producción)
- **Autenticación**: JWT (JSON Web Tokens)
- **Documentación**: OpenAPI 3.0 (Swagger)
- **Seguridad**: Spring Security
- **Persistencia**: Spring Data JPA
- **Validación**: Bean Validation
- **Testing**: JUnit 5, Mockito

### Frontend
- **Tecnologías**: HTML5, CSS3, JavaScript (ES6+)
- **UI Framework**: Bootstrap 4.6
- **Iconos**: Font Awesome 6.0
- **HTTP Client**: Fetch API

---

## 🏗️ Arquitectura del Proyecto
```
src/main/java/com/augusto/__ClinicaOdontologicaSpringJPA/
├── _1_controller/ # Controladores REST
├── _2_service/ # Lógica de negocio
│ ├── impl/ # Implementaciones de servicios
│ └── interfaces/ # Contratos de servicios
├── _3_repository/ # Acceso a datos (Spring Data JPA)
├── _4_entity/ # Entidades JPA
├── dto/ # Objetos de Transferencia de Datos
├── mapper/ # Mapeadores (MapStruct)
├── configuration/ # Configuraciones de Spring
└── exception/ # Manejo de excepciones
```

---

## 📚 Módulos Principales

### 1. 🔐 Autenticación y Autorización
- Registro y login de usuarios
- Autorización basada en roles (ADMIN, USER)
- Tokens JWT para autenticación stateless
- Configuración de seguridad con Spring Security

### 2. 👥 Gestión de Pacientes
- CRUD completo de pacientes
- Validación de datos con Bean Validation
- Búsqueda por nombre, apellido y DNI
- Gestión de direcciones embebidas

### 3. 🦷 Gestión de Odontólogos
- CRUD completo de odontólogos
- Validación de matrícula profesional
- Búsqueda por nombre, apellido y matrícula

### 4. 📅 Gestión de Turnos
- Sistema de reserva de turnos
- Validación de disponibilidad
- Relaciones Many-to-One con pacientes y odontólogos
- DTOs para diferentes casos de uso

### 5. 📊 Sistema de Reportes
- Generación de PDFs (reportes de pacientes, comprobantes)
- Certificados médicos personalizados
- Integración con librerías de generación de documentos

### 6. 🔔 Sistema de Notificaciones
- Recordatorios de turnos
- Confirmaciones automáticas
- Historial de notificaciones enviadas

### 7. 📈 Dashboard y Estadísticas
- Métricas en tiempo real
- Estadísticas de pacientes mensuales
- Top odontólogos más solicitados
- Resumen general de la clínica

---

## 🛠️ Tecnologías y Dependencias

### Backend Dependencies
```
<!-- Spring Boot Starters -->
spring-boot-starter-web
spring-boot-starter-data-jpa
spring-boot-starter-security
spring-boot-starter-validation

<!-- Database -->
h2 (runtime)
postgresql (runtime)

<!-- JWT -->
io.jsonwebtoken:jjwt-api
io.jsonwebtoken:jjwt-impl
io.jsonwebtoken:jjwt-jackson

<!-- Documentation -->
springdoc-openapi-starter-webmvc-ui

<!-- PDF Generation -->
com.itextpdf:itext7-core

<!-- Testing -->
spring-boot-starter-test
```

### Frontend Dependencies
```
<!-- UI Framework -->
Bootstrap 4.6.1
Font Awesome 6.0.0

<!-- JavaScript -->
jQuery 3.4.1
Popper.js 1.16.0
```

## 🔧 Configuración y Despliegue
Requisitos Previos

Java 17 o superior

Maven 3.6+

Base de datos H2 (desarrollo) o PostgreSQL (producción)

### Variables de Entorno
```
# Database
spring.datasource.url=jdbc:postgresql://localhost:5432/dental_clinic
spring.datasource.username=your_username
spring.datasource.password=your_password

# JWT
jwt.secret=your-jwt-secret-key
jwt.expiration=86400000

# Server
server.port=8080
```

### Ejecución Local
```
# Clonar el repositorio
git clone https://github.com/RinaldiAugusto/DentalClinic.git

# Navegar al directorio
cd DentalClinic

# Compilar y ejecutar
mvn spring-boot:run
```

## 📡 API Endpoints

### Autenticación
```
POST /api/auth/register       - Registro de usuarios
POST /api/auth/login          - Login de usuarios
```

### Pacientes
```
GET /patients                 - Listar todos los pacientes
GET /patients/{id}            - Obtener paciente por ID
POST /patients                - Crear nuevo paciente
PUT /patients/{id}            - Actualizar paciente
DELETE /patients/delete/{id}  - Eliminar paciente

```

### Odontólogos
```
GET /dentists                 - Listar todos los odontólogos
GET /dentists/{id}            - Obtener odontólogo por ID
POST /dentists                - Crear nuevo odontólogo
PUT /dentists/{id}            - Actualizar odontólogo
DELETE /dentists/delete/{id}  - Eliminar odontólogo

```

### Turnos
```
GET /appointments/v2          - Listar todos los turnos (v2)
GET /appointments/v2/{id}     - Obtener turno por ID (v2)
POST /appointments/v2         - Crear nuevo turno (v2)
PUT /appointments/v2/{id}     - Actualizar turno (v2)
DELETE /appointments/v2/{id}  - Eliminar turno (v2)

```

### Reportes PDF
```
GET /pdf/patient-report/{patientId}         - Reporte de paciente
GET /pdf/appointment-receipt/{appointmentId} - Comprobante de turno
POST /pdf/medical-certificate/{patientId}   - Certificado médico

```

### Notificaciones
```
GET /notifications/all                     - Historial de notificaciones
POST /notifications/appointment-reminder    - Recordatorio de turno
POST /notifications/appointment-confirmation - Confirmación de turno

```

### Estadísticas
```
GET /stats/patients-monthly  - Estadísticas mensuales de pacientes
GET /stats/appointments-today - Turnos del día
GET /stats/top-dentists      - Top odontólogos
GET /stats/overview          - Resumen general

```

## 🧪 Testing
El proyecto incluye tests unitarios y de integración:
```
# Ejecutar todos los tests
mvn test

# Ejecutar tests con cobertura
mvn jacoco:report

```

## 📊 Métricas y Monitoreo
```
Jacoco: Cobertura de código

Spring Boot Actuator: Health checks y métricas

Logging: Configuración con Logback
```

## 🚀 Despliegue en Producción
```
Plataforma: Render.com

URL: https://dental-clinic-backend-53ys.onrender.com

Base de Datos: PostgreSQL

CI/CD: Integración con GitHub
```

## 👨‍💻 Habilidades Demostradas
```
Spring Framework

Spring Boot Auto-configuration

Spring Data JPA y Repositories

Spring Security y JWT

Spring MVC y REST controllers

Spring Validation

Patrones de Diseño

MVC (Model-View-Controller)

DTO (Data Transfer Object)

Repository Pattern

Service Layer Pattern

Dependency Injection

Base de Datos

Mapeo ORM con JPA/Hibernate

Relaciones @OneToMany, @ManyToOne

Consultas con Spring Data JPA

Transacciones y manejo de excepciones

Seguridad

Autenticación JWT stateless

Autorización basada en roles

Configuración de CORS

Protección contra CSRF

Calidad de Código

Principios SOLID

Clean Architecture

Manejo de excepciones

Validación de datos

Logging estructurado
```

## 👤 Autor
```
Augusto Rinaldi - Desarrollador Backend Java

GitHub: @RinaldiAugusto

LinkedIn: Augusto Rinaldi

gmail: augusto.rinaldi.75@gmail.com
```