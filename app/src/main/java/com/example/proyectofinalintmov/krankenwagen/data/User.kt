package com.example.proyectofinalintmov.krankenwagen.data

/**
 * Clase Usuario del sistema
 * @property name almacena el nombre del usuario
 * @property email almacena el email del usuario
 * @property userId almacena el id del usuario
 */
data class User (
    val userId: String,
    val email: String,
    var name: String
    )