# GestionInventario | Java 21 + Spring Boot 4.2 + PostgreSQL

API REST de inventario de productos, lista para IntelliJ IDEA y para los cuatro escenarios solicitados por la práctica.

## Incluye

- Java 21; Gradle 9.2.1; Spring Boot 4.2.0; Spring Data JPA, Validation, PostgreSQL.
- CRUD `GET`, `POST`, `PUT`, `DELETE` en `/api/productos`.
- Swagger UI en `/swagger-ui.html` (raíz `/` redirige a Swagger).
- `Dockerfile` multi-etapa (`eclipse-temurin:21-jdk` → `eclipse-temurin:21-jre`).
- Archivos de ayuda para Amazon Elastic Beanstalk, Windows + Nginx, Ubuntu + Nginx/systemd.
- Pruebas unitarias de validación sin dependencia de una base de datos activa.

## 1. Abrir y compilar

1. Descomprime `GestionInventario_Proyecto_Java21.zip` en el Escritorio u otra carpeta.
2. En IntelliJ IDEA: **File > Open > carpeta GestionInventario**. Confía en el proyecto; espera la sincronización Gradle.
3. Configura **Project SDK / Gradle JVM = Java 21**.
4. En la terminal PowerShell del proyecto:

   ```powershell
   $env:JAVA_HOME = "C:\Users\alfredo\.jdks\ms-21.0.12.1"
   $env:Path = "$env:JAVA_HOME\bin;$env:Path"
   .\gradlew.bat clean bootJar
   ```

   **Nota:** el archivo `gradlew.bat` incluido es un lanzador autónomo que descarga Gradle 9.2.1 la primera vez; no es el wrapper JAR oficial. Requiere Internet y Java 21. Para crear un wrapper oficial posteriormente, ejecuta `gradle wrapper --gradle-version 9.2.1` con Gradle instalado.

## 2. Credenciales Neon: solo en variables de entorno

En **Neon > GestionInventario > Connect**, selecciona *Java* y desactiva el pooling. Copia el host, la BD y el usuario. En IntelliJ, entra en **Run > Edit Configurations > Spring Boot (clase `practica.gestioninventario.GestionInventarioApplication`) > Modify options > Environment variables**.

| Variable | Valor de ejemplo |
|---|---|
| `DB_URL` | `jdbc:postgresql://HOST_REAL_NEON/neondb?sslmode=require` |
| `DB_USER` | `neondb_owner` |
| `DB_PASS` | contraseña actual de Neon (NO la subas) |
| `PORT` | `8080` |

La contraseña que apareció anteriormente en una captura debe renovarse en Neon antes de usarla. No la escribas en `application.properties` ni la publiques.

**Si la clase principal no aparece**: abre el archivo `src/main/java/practica/gestioninventario/GestionInventarioApplication.java` y pulsa el triángulo verde junto a `main`. IntelliJ genera la configuración; luego agrega las variables.

## 3. Pruebas locales

Inicia la clase principal; debería indicar `Started GestionInventarioApplication` y el puerto 8080.

- http://localhost:8080/swagger-ui.html
- http://localhost:8080/api/productos
- http://localhost:8080/health

Ejemplo POST (Swagger, botón `Try it out`):

```json
{
  "nombre": "Teclado mecanico",
  "descripcion": "USB",
  "precio": 35.50,
  "stock": 12,
  "categoria": "Accesorios"
}
```

Comprueba GET, POST, PUT y DELETE con un ID real. Los registros quedan en PostgreSQL (tabla `productos`, creada por JPA `ddl-auto=update` para esta práctica).

## 4. Destinos de despliegue

**Render:** conectar repo de GitHub como **Web Service / Docker**, agregar `DB_URL`, `DB_USER`, `DB_PASS`, `PORT=8080` y abrir `/swagger-ui.html` en la URL pública. Render construye usando el Dockerfile incluido.

**AWS Elastic Beanstalk:** lanzar **Corretto 21 on Amazon Linux 2023**, crear una **RDS PostgreSQL** y seguridad desde SG de Beanstalk. Configurar variables `DB_URL`, `DB_USER`, `DB_PASS`, `PORT=5000`, `SERVER_PORT=5000`. Ejecutar el script `scripts/crear-paquete-beanstalk.ps1` para generar un ZIP listo para EB con `app.jar` y `Procfile` en la raíz.

**Windows Server 2025 (EC2):** JDK 21, PostgreSQL local o RDS, ejecutar `.jar` en 8080, instalar Nginx, usar `deploy/windows/nginx.conf` para escuchar 80, habilitar SG AWS y Firewall Windows. Detener IIS si ya ocupa el puerto 80.

**Ubuntu 24.04 (EC2):** instalar JRE 21, PostgreSQL, Nginx; copiar JAR a `/opt/crud-app/app.jar`; copiar `deploy/ubuntu/crud-app.service` a `/etc/systemd/system/`, crear `/etc/crud-app.env` **fuera del repositorio** con permisos root:600; copiar `deploy/ubuntu/nginx-crud-app` a `/etc/nginx/sites-available/crud-app` y crear symlink. SG: 22 desde tu IP y 80 público.

Consulta el documento Word adjunto para un procedimiento detallado, capturas exigidas y verificaciones.

## Diagnóstico

- `JAVA_HOME` no definido: configura Java 21 en la terminal/sistema.
- `DB_URL` sin resolver: revisa Environment variables (IntelliJ), Render, EB o archivo de entorno del servicio.
- `password authentication failed`: revisa la contraseña restablecida en Neon o PostgreSQL.
- `No suitable driver`: verifica que Gradle haya descargado PostgreSQL Driver.
- `502 Bad Gateway` en Nginx: revisa que la app esté activa en 8080 (`curl http://127.0.0.1:8080/health`).
- AWS EB en verde pero CRUD falla: revisar RDS Available, SG puerto 5432 y Logs de EB.
- `./gradlew.bat` no encuentra Java: asignar `JAVA_HOME` antes de compilar.

## Seguridad / alcance

Esta API es **didáctica**: Swagger y el CRUD no tienen autenticación; no uses datos sensibles ni dejes recursos públicos funcionando después de la revisión. Para un sistema real se requeriría autenticación, HTTPS, migrations versionadas, límites de acceso y rotación de secretos. `ddl-auto=update` se incluye únicamente para facilitar la práctica.
