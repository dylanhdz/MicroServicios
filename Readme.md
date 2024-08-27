# Microservicios - Angular - Spring Boot - Docker
Sistema de matriculación de estudiantes en cursos, desarrollado con Angular, Spring Boot y Docker.
## Requisitos
- Docker
- Docker Compose
- Java 22
- Maven
- Node.js
- Angular CLI

# Modo de Instalación
## Local
## Clonar el Repositorio
```bash
git clone
```
## Iniciar los microservicios
```bash
cd msvc-usuarios
mvn spring-boot:run 
cd ..
cd msvc-cursos
mvn spring-boot:run
cd ..
cd front
ng serve
```

## Dockerizar
```bash
docker-compose up
```

# Sobre las Pruebas
## Resultados de las Pruebas Estáticas
Se realizaron pruebas estáticas con SonarQube, obteniendo los siguientes resultados:
- Bugs: 0
- Vulnerabilities: 0
- Code Smells: 0
Se realizó un análisis usando OWASP ZAP y se obtuvieron los siguientes resultados:
- Error: "The following resources are missing a Content-Security-Policy header"
- Solución: Se debe agregar la cabecera `Content-Security-Policy` en el archivo `index.html` de Angular.

## Resultados de las Pruebas Dinámicas
Se realizaron pruebas dinámicas con Karma y Jasmine, obteniendo los siguientes resultados:
- Test Suites: 3
- Test Cases: 10
- Assertions: 10