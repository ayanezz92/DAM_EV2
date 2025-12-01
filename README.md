# 🏥 VidaSaludaApp

## 👥 Integrantes
* **Benjamin Mella - Agustin Yañez** - Desarrollador Full Stack (Android + Backend)


## 📱 Funcionalidades
1.  **Registro de Actividades:** Formularios validados para ingresar ejercicio, sueño y comidas.
2.  **Historial Local:** Almacenamiento offline usando **Room Database**.
3.  **Clima en Tiempo Real:** Integración con API externa (OpenMeteo) para mostrar el clima actual.
4.  **Autenticación:** Pantalla de Login con validaciones y diseño moderno.
5.  **Perfil de Usuario:** Gestión de foto de perfil con acceso a cámara/galería.
6.  **Conexión Backend:** Saludo personalizado desde microservicio Spring Boot.

## 🔗 Endpoints Utilizados

### API Externa (Clima)
* **URL:** `(https://open-meteo.com/)`
* **Método:** GET
* **Uso:** Obtener temperatura y viento según latitud/longitud.

### Microservicio Propio (Spring Boot)
* **URL Local:** `http://localhost:8080/hola` (Mapeado a `10.0.2.2` en Android)
* **Endpoint:** `/hola`
* **Método:** GET
* **Respuesta:** String plano ("¡Hola Benja!...")

## 🚀 Pasos para Ejecutar

### 1. Backend (Microservicio)
1.  Tener instalado Java 17 o superior.
2.  Descomprimir `vidasalud-backend.zip` o clonar la carpeta.
3.  Abrir en Visual Studio Code o IntelliJ.
4.  Ejecutar el archivo `VidasaludBackendApplication.java`.
5.  Verificar que corra en el puerto 8080.

### 2. Aplicación Móvil (Android)
1.  Abrir el proyecto en Android Studio.
2.  Sincronizar Gradle.
3.  Asegurarse de que el Backend esté corriendo.
4.  Ejecutar en Emulador (Pixel API 30+ recomendado).

## 📦 Evidencias de Entrega
* **APK Firmado:** Ubicado en la carpeta `/release` o adjunto en la entrega.
* **Llave de Firma:** `llave_vidasalud.jks` adjunta.
