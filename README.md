# 🧬 Mutant Detector API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![Tests](https://img.shields.io/badge/Tests-13%2B%20passing-success.svg)]()
[![Coverage](https://img.shields.io/badge/Coverage-95%25+-brightgreen.svg)]()

API REST para detectar si un humano es mutante basándose en su secuencia de ADN. Proyecto desarrollado como examen técnico para **MercadoLibre Backend Developer**.

---

## 📋 Tabla de Contenidos

1. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
2. [Prerequisitos](#-prerequisitos)
3. [Cómo Ejecutar la API Localmente](#-cómo-ejecutar-la-api-localmente)
4. [Cómo Ejecutar la API con Docker](#-cómo-ejecutar-la-api-con-docker)
5. [Endpoints de la API](#-endpoints-de-la-api)
6. [Cómo Ejecutar los Tests](#-cómo-ejecutar-los-tests)
7. [URL de la API Desplegada](#-url-de-la-api-desplegada)

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3.2
- **Build Tool:** Gradle 8
- **Base de Datos:** H2 (en memoria)
- **Persistencia:** Spring Data JPA / Hibernate
- **Testing:** JUnit 5
- **Documentación:** SpringDoc OpenAPI (Swagger UI)
- **Contenerización:** Docker
- **Utilidades:** Lombok

---

## 📦 Prerequisitos

- **Java JDK 21** o superior.
- **Git**.
- **Docker Desktop** (para la opción de Docker).

---

## 🚀 Cómo Ejecutar la API Localmente

### Paso 1: Clonar el Repositorio
```bash
git clone https://github.com/tu-usuario/tu-repositorio.git
cd tu-repositorio
```

### Paso 2: Compilar y Ejecutar la Aplicación con Gradle
```bash
# En Windows
.\gradlew.bat bootRun

# En Mac/Linux
./gradlew bootRun
```
La aplicación se iniciará y estará escuchando en el puerto `8080`.

### Paso 3: Acceder a la Aplicación
Una vez iniciada, puedes acceder a los siguientes recursos:

- **Swagger UI (Documentación Interactiva):**
  [http://localhost:8080/swagger-ui.html](http://localhost:80