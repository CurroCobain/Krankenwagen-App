package com.example.proyectofinalintmov.krankenwagen.data

/**
 * Clase Usuario del sistema
 * @param name almacena el nombre del usuario
 * @param password almacena el password del usuario
 * @param email almacena el email del usuario
 */
data class User (
    var name: String,
    var password: String,
    val email: String,
)