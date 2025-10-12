# 🦷 Dental Clinic Management System

A complete full-stack web application for managing dental clinics, built with Spring Boot and modern frontend technologies.

![Java](https://img.shields.io/badge/Java-17-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0-green)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue)
![Bootstrap](https://img.shields.io/badge/Bootstrap-5-purple)

## 🚀 Live Demo

- **Frontend**: [View Live Demo](https://rinaldiaugusto.github.io/DentalClinic)
- **Backend API**: `https://dental-clinic-backend-53ys.onrender.com`
- **Test Credentials**:
  - Email: `admin@clinica.com`
  - Password: `admin123`

⚠️ **Note**: The frontend demo requires the backend to be running. Use the test credentials above.

## 📋 Features

### 🔐 Authentication & Security
- JWT-based authentication
- Role-based access control (ADMIN/USER)
- Secure password encryption
- Session management

### 👥 User Management
- User registration and login
- Role-based dashboard access
- Profile management

### 🦷 Dentists Management
- Complete CRUD operations
- License number validation
- Search and filter functionality

### 👨‍⚕️ Patients Management
- Patient registration with full details
- Address management
- Medical history tracking
- Advanced search capabilities

### 📅 Appointments System
- Schedule and manage appointments
- Real-time availability checking
- Appointment status tracking (Pending/Confirmed/Completed/Cancelled)
- Calendar integration

### 📊 Reporting & Analytics
- Patient medical reports (PDF)
- Appointment receipts
- Medical certificates
- Clinic statistics dashboard

### 🔔 Notifications
- Appointment reminders
- Confirmation notifications
- Notification history
- Email integration ready

## 🛠️ Technology Stack

### Backend
- **Java 17** - Core programming language
- **Spring Boot 3.0** - Application framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Production database
- **JWT** - Token-based authentication
- **Maven** - Dependency management

### Frontend
- **HTML5** - Markup language
- **CSS3** - Styling with custom terracota palette
- **JavaScript (ES6+)** - Client-side functionality
- **Bootstrap 5** - Responsive design framework
- **Font Awesome** - Icons
- **Chart.js** - Statistics and charts

### DevOps & Tools
- **Render** - Cloud deployment platform
- **Git** - Version control
- **Postman** - API testing
- **GitHub Pages** - Frontend hosting

## 🗄️ Database Schema

![Database Schema](docs/database/schema.png)

Key Entities:
- **Users** - System users and authentication
- **Dentists** - Dental professionals
- **Patients** - Clinic patients with medical history
- **Appointments** - Scheduling and visit tracking
- **Addresses** - Patient location information

## 🚀 Installation & Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- PostgreSQL 12+
- Node.js (for frontend development)

### Backend Setup
```bash
# Clone the repository
git clone https://github.com/RinaldiAugusto/DentalClinic.git
cd DentalClinic/backend

# Configure database in application.properties
spring.datasource.url=jdbc:postgresql://localhost:5432/dental_clinic
spring.datasource.username=your_username
spring.datasource.password=your_password

# Run the application
mvn spring-boot:run