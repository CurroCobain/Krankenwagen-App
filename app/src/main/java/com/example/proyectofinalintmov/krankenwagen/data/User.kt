package com.example.proyectofinalintmov.krankenwagen.data

/**
 * Clase Usuario del sistema
 * @param name almacena el nombre del usuario
 * @param email almacena el email del usuario
 */
data class User (
    val userId: String,
    val email: String,
    var name: String
    )