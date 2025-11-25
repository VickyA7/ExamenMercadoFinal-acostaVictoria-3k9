# 🧬 Mutant Detector API

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.x-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Gradle](https://img.shields.io/badge/Gradle-8.x-blue.svg)](https://gradle.org/)
[![Docker](https://img.shields.io/badge/Docker-Ready-blue.svg)](https://www.docker.com/)
[![Tests](https://img.shields.io/badge/Tests-Passing-success.svg)]()
[![Coverage](https://img.shields.io/badge/Coverage-95%25+-brightgreen.svg)]()

API REST para detectar si un humano es mutante basándose en su secuencia de ADN. Proyecto desarrollado como examen técnico para **MercadoLibre Backend Developer**.

---

## 📄 Información Académica

- **Alumna:** Victoria Rocio Acosta Yancarelli
- **Materia:** Desarrollo de Software
- **Curso:** 3k9
- **Año:** 2025
- **Universidad:** Universidad Tecnológica Nacional - Regional Mendoza

---

## 📋 Tabla de Contenidos

1. [URL de la API Desplegada](#-url-de-la-api-desplegada)
2. [Tecnologías Utilizadas](#-tecnologías-utilizadas)
3. [Lógica y Rendimiento](#-lógica-y-rendimiento)
4. [Arquitectura del Proyecto](#-arquitectura-del-proyecto)
5. [Prerequisitos](#-prerequisitos)
6. [Instrucciones de Ejecución Local](#-instrucciones-de-ejecución-local)
7. [Instrucciones de Ejecución con Docker](#-instrucciones-de-ejecución-con-docker)
8. [Uso de la API](#-uso-de-la-api)
9. [Testing](#-testing)

---

## 🌐 URL de la API Desplegada

La API está desplegada en Render y es accesible públicamente. La mejor forma de interactuar con ella es a través de su documentación Swagger UI:

**[https://examenmercadofinal-acostavictoria-3k9.onrender.com/swagger-ui.html](https://examenmercadofinal-acostavictoria-3k9.onrender.com/swagger-ui.html)**

---

## 🛠️ Tecnologías Utilizadas

- **Lenguaje:** Java 21
- **Framework:** Spring Boot 3.2
- **Build Tool:** Gradle 8
- **Base de Datos:** H2 (en memoria para desarrollo local)
- **Persistencia:** Spring Data JPA / Hibernate
- **Testing:** JUnit 5
- **Documentación:** SpringDoc OpenAPI (Swagger UI)
- **Contenerización:** Docker
- **Utilidades:** Lombok

---

## ⚡ Lógica y Rendimiento

El núcleo del proyecto es un algoritmo eficiente para la detección de mutantes:

- **Regla de Negocio:** Se busca más de una secuencia de 4 letras iguales en dirección horizontal, vertical o diagonal.
- **Validación:** Se asegura que la matriz sea NxN y contenga solo caracteres válidos (A, T, C, G).
- **Optimización:** Implementa **Early Termination**. El algoritmo se detiene inmediatamente al encontrar la segunda secuencia, evitando recorrer toda la matriz innecesariamente.
- **Complejidad:** $O(N^2)$ en el peor caso, pero tiende a $O(1)$ en casos positivos.

---

## 🏗️ Arquitectura del Proyecto

El proyecto sigue una arquitectura en capas limpia y desacoplada para promover la mantenibilidad y escalabilidad:

- **Controller:** Expone los endpoints REST y maneja el tráfico HTTP.
- **DTO (Data Transfer Object):** Define los "contratos" de la API para las peticiones y respuestas.
- **Service:** Orquesta la lógica de negocio principal, conectando el detector y el repositorio.
- **Repository:** Proporciona una abstracción para el acceso a datos usando Spring Data JPA.
- **Entity:** Define el modelo de datos que se mapea a la base de datos.
- **Validation:** Contiene la lógica para validaciones de entrada personalizadas.
- **Exception Handling:** Centraliza el manejo de errores para respuestas consistentes.
- **Config:** Gestiona la configuración de beans, como Swagger.

### Estructura de Directorios
```
src/main/java/org/example/
├── controller/ # MutantController
├── dto/ # DnaRequest, StatsResponse
├── service/ # MutantDetector (Algoritmo), Services
├── repository/ # DnaRecordRepository
├── entity/ # DnaRecord
├── validation/ # Validadores custom
├── exception/ # GlobalExceptionHandler
└── config/ # SwaggerConfig
```

---

## 📦 Prerequisitos

- **Java JDK 21** o superior.
- **Git**.
- **Docker Desktop** (para la opción de Docker).

---

## 🚀 Instrucciones de Ejecución Local

### Paso 1: Clonar el Repositorio
```bash
git clone https://github.com/acostaVictoria/ExamenMercado-acostaVictoria-3k9.git
cd ExamenMercado-acostaVictoria-3k9
```

### Paso 2: Ejecutar la Aplicación con Gradle
```bash
# En Windows
.\gradlew.bat bootRun

# En Mac/Linux
./gradlew bootRun
```
La aplicación se iniciará y estará escuchando en el puerto `8080`.

### Paso 3: Acceder a la Aplicación Local
- **Swagger UI (Documentación Interactiva):**
  [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)
- **Consola H2 Database:**
  [http://localhost:8080/h2-console](http://localhost:8080/h2-console) (JDBC URL: `jdbc:h2:mem:testdb`, User: `sa`, sin contraseña)

---

## 🐳 Instrucciones de Ejecución con Docker

### Paso 1: Construir la Imagen Docker
Asegúrate de que Docker Desktop esté corriendo y ejecuta en la raíz del proyecto:
```bash
docker build -t mutant-api .
```

### Paso 2: Ejecutar el Contenedor
```bash
docker run -p 8080:8080 --name mutant-app -d mutant-api
```

### Paso 3: Acceder a la Aplicación en Docker
La aplicación estará disponible en las mismas URLs locales que en el paso anterior, principalmente:
- **Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 📡 Uso de la API

La forma más sencilla de probar los endpoints es a través de la **interfaz de Swagger UI**. 

### POST /mutant
Verifica si una secuencia de ADN es mutante.

**Request de un Mutante:**
```bash
curl -X POST "http://localhost:8080/mutant" \
-H "Content-Type: application/json" \
-d '{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATGT",
    "AGAAGG",
    "CCCCTA",
    "TCACTG"
  ]
}'
```
**Respuesta:** `HTTP 200 OK`

**Request de un Humano:**
```bash
curl -X POST "http://localhost:8080/mutant" \
-H "Content-Type: application/json" \
-d '{
  "dna": [
    "ATGCGA",
    "CAGTGC",
    "TTATTT",
    "AGACGG",
    "GCGTCA",
    "TCACTG"
  ]
}'
```
**Respuesta:** `HTTP 403 Forbidden`

### GET /stats
Obtiene las estadísticas de los ADN verificados.

**Request:**
```bash
curl -X GET "http://localhost:8080/stats"
```
**Respuesta:** `HTTP 200 OK`
```json
{
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
```

---

## 🧪 Testing
El proyecto cuenta con una suite de 36 tests automatizados que garantizan la calidad y correctitud del código.

###Resumen de Tests
- MutantDetectorTest (17 tests): Valida el algoritmo core (casos mutantes, humanos, bordes y excepciones).
- MutantServiceTest (5 tests): Valida la lógica de negocio usando Mocks.
- StatsServiceTest (6 tests): Valida el cálculo de estadísticas.
- MutantControllerTest (8 tests): Tests de integración para los endpoints REST.


### Ejecutar todos los Tests
```bash
# En Windows
.\gradlew.bat test

# En Mac/Linux
./gradlew test
```

### Generar Reporte de Cobertura de Código
```bash
# En Windows
.\gradlew.bat jacocoTestReport

# En Mac/Linux
./gradlew jacocoTestReport
```
El reporte HTML se encontrará en `build/reports/jacoco/test/html/index.html`.