# Bibliomedia - Control de Prestamos

Proyecto de practica en Java para gestionar el prestamo de peliculas y videojuegos entre alumnos y profesores.

Permite registrar personas, consultar articulos multimedia y controlar las fechas de entrega tanto desde la consola de comandos como desde una interfaz web basica que se ejecuta en el navegador.

---

## Que utiliza el proyecto

* Lenguaje: Java (configurado con Maven para gestion automatica de dependencias)
* Entorno de ejecucion recomendado: IntelliJ IDEA (permite ajustar facilmente el SDK y la JVM sin conflictos de Build Path)
* Base de datos: MariaDB / MySQL gestionada localmente mediante XAMPP y phpMyAdmin
* Driver de persistencia: mysql-connector-j descargado automaticamente por Maven via pom.xml
* Interfaz web: Paginas estaticas en HTML, CSS y JavaScript conectadas a un servidor interno en el puerto 8080

---

## Estructura del Proyecto

```text
src/
|-- main/
|   |-- java/
|       |-- org/example/
|           |-- database/         # Conexion a la base de datos (ConexionDB)
|           |-- model/            # Clases del dominio y herencia
|               |-- AbstractArticulo
|               |-- AbstractUsuario
|               |-- Alumno
|               |-- Docente
|               |-- Pelicula
|               |-- VideoJuego
|               |-- Prestamo
|           |-- repository/       # Capa DAO (consultas SQL a la base de datos)
|               |-- ArticuloDao
|               |-- UsuarioDao
|               |-- PrestamoDao
|               |-- Query
|           |-- service/          # Logica del programa y servidores
|               |-- GestionAlquiler
|               |-- MenuConsola
|               |-- ServidorWeb
|           |-- utils/            # Validacion de datos (ValidacionesUtil)
|           |-- Main.java         # Archivo principal de ejecucion
|-- resources/                    # Scripts SQL (schema.sql, data.sql)
|-- webapp/                       # Archivos de la interfaz web (HTML, CSS, JS)

---

## Base de datos

Usa una base de datos llamada library_db con tres tablas principales:

1. usuarios: Guarda alumnos y profesores usando el DNI/NIE como identificador principal.
2. articulos: Guarda videojuegos y peliculas identificados por su numero de serie.
3. prestamos: Registra que usuario se llevo que articulo, con su fecha de salida, fecha de entrega limite y fecha en la que realmente lo devolvio (para saber si se entrego a tiempo o con retraso).

---

## Como ejecutar el proyecto

1. Clonar el repositorio:
   git clone https://github.com/leobazan126-prog/alquiler-multimedia-java.git

2. Iniciar la base de datos:
   * Abrir el panel de control de XAMPP e iniciar el modulo de MySQL.
   * Entrar a phpMyAdmin en el navegador (http://localhost/phpmyadmin).
   * Ejecutar src/main/resources/schema.sql (crea la base de datos library_db y la estructura de tablas).
   * Ejecutar src/main/resources/data.sql (inserta los datos iniciales de prueba).

3. Abrir el proyecto en el IDE:
   * Se recomienda abrir la carpeta del proyecto directamente con IntelliJ IDEA.
   * Esperar a que Maven cargue y descargue automaticamente las dependencias del archivo pom.xml (no hace falta mover archivos .jar manualmente).
   * Asegurarse de que el SDK del proyecto este asignado correctamente en la estructura del proyecto.

4. Ejecucion:
   * Ejecutar el archivo Main.java.
   * El programa iniciara el menu por consola y tambien estara disponible en el navegador en http://localhost:8080.
