# 🦷 Dental Clinic - API REST

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.1-brightgreen)
![Docker](https://img.shields.io/badge/Docker-Ready-blue)
![GitHub Actions](https://github.com/RinaldiAugusto/DentalClinic/actions/workflows/ci.yml/badge.svg)

Una API REST profesional para gestión de clínicas dentales desarrollada con Spring Boot y arquitectura en capas.

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías-utilizadas)
- [Estructura](#estructura-del-proyecto)
- [Instalación](#instalación-y-ejecución)
- [API](#documentación-de-la-api)
- [Endpoints](#endpoints-principales)
- [Docker](#docker)
- [Diagramas](#diagramas)
- [Contribución](#contribución)
- [Estado](#estado-del-proyecto)

## ⚙️ Características

- ✅ **Arquitectura en capas** (Controller-Service-Repository)
- ✅ **DTOs y Mappers** para separación de concerns
- ✅ **Validaciones automáticas** con Bean Validation
- ✅ **Manejo profesional de errores** global
- ✅ **Documentación automática** con Swagger/OpenAPI
- ✅ **Containerización** con Docker
- ✅ **Base de datos H2** para desarrollo
- ✅ **Migración gradual** de endpoints

## 🛠️ Tecnologías Utilizadas

| Tecnología | Versión | Propósito |
|------------|---------|-----------|
| Spring Boot | 3.2.1 | Framework principal |
| Java | 17 | Lenguaje de programación |
| Spring Data JPA | 3.2.1 | Persistencia de datos |
| H2 Database | 2.2.224 | Base de datos en memoria |
| Docker | Latest | Containerización |
| SpringDoc OpenAPI | 2.0.3 | Documentación API |


## 🚀 Instalación y Ejecución

### Prerrequisitos
- Java 17
- Maven 3.8+
- Docker (opcional)

### Ejecución Local
```bash
# Clonar el repositorio
git clone https://github.com/RinaldiAugusto/DentalClinic.git
cd DentalClinic

# Compilar el proyecto
mvn clean package

# Ejecutar la aplicación
java -jar target/DentalClinicMVC-0.0.1-SNAPSHOT.jar

# Construir la imagen
docker build -t dental-clinic-app .
```

## 📚 Documentación de la API

Una vez ejecutada la aplicación, accede a la documentación interactiva:

- **Swagger UI**: http://localhost:8080/swagger-ui/index.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## 🌐 Endpoints Principales

### Pacientes (`/patients`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/patients` | Obtener todos los pacientes |
| GET | `/patients/{id}` | Obtener paciente por ID |
| POST | `/patients` | Crear nuevo paciente |
| PUT | `/patients/{id}` | Actualizar paciente |
| DELETE | `/patients/{id}` | Eliminar paciente |

### Dentistas (`/dentists`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/dentists` | Obtener todos los dentistas |
| GET | `/dentists/{id}` | Obtener dentista por ID |
| POST | `/dentists` | Crear nuevo dentista |
| PUT | `/dentists/{id}` | Actualizar dentista |
| DELETE | `/dentists/{id}` | Eliminar dentista |

### Turnos (`/appointments`)

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/appointments` | Obtener turnos (v1) |
| GET | `/appointments/v2` | Obtener turnos con DTOs (v2) |
| POST | `/appointments/v2` | Crear turno con DTOs |

## 🐳 Docker

El proyecto incluye un **Dockerfile de dos etapas**:

```dockerfile
# Primera etapa: construcción
FROM maven:3.8.5-openjdk-17 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Segunda etapa: ejecución
FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
```


## 👥 Contribución

¡Contribuciones son bienvenidas! 

1. **Fork** el proyecto
2. **Crea una rama**: `git checkout -b feature/nueva-feature`
3. **Commit**: `git commit -m 'feat: nueva feature'`
4. **Push**: `git push origin feature/nueva-feature`
5. **Abre un Pull Request**

## 📈 Estado del Proyecto

### ✅ Completado

- [x] API REST completa
- [x] Arquitectura con DTOs
- [x] Documentación Swagger
- [x] Docker containerización
- [x] GitHub Actions CI/CD

## 👤 Autor

**Augusto Rinaldi** - [GitHub](https://github.com/RinaldiAugusto)

---

<div align="center">

### ⭐ Si te gusta este proyecto, ¡dale una estrella!

</div>
