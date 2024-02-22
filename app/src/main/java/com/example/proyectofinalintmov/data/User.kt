package com.example.proyectofinalintmov.data

/**
 * Clase Usuario del sistema
 * @param name almacena el nombre del usuario
 * @param password almacena el password del usuario
 * @param email almacena el email del usuario
 */
data class User (
    val name: String,
    val password: String,
    val email: String,
)