package com.example.proyectofinalintmov.model

/**
 * Sealed class que representa diferentes rutas dentro de la aplicación.
 * Cada ruta está asociada con un identificador único de tipo cadena.
 */
sealed class Routes(val route: String) {
    // Representa la ruta de la pantalla de bienvenida.
    object PantallaWelcome : Routes("welcome")

    // Representa la ruta de la pantalla de ambulancias.
    object PantallaAmbulances : Routes("ambulances")

   // Representa la ruta de la pantalla de hospitales.
    object PantallaHospitals : Routes("hospitals")

   // Representa la ruta de la pantalla de centros de salud.
    object PantallaDocs : Routes("docs")
}