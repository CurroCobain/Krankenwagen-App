package com.example.proyectofinalintmov.model

sealed class Routes(val route: String) {
    object PantallaWelcome : Routes("welcome")
    object PantallaAmbulances : Routes("ambulances")
    object PantallaHospitals : Routes("hospitals")
    object PantallaDocs : Routes("docs")
}