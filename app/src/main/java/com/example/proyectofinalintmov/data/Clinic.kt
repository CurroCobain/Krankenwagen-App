package com.example.proyectofinalintmov.data

/**
 * Clase Clinic que representa los centros de salud
 * @param id almacena el id del centro
 * @param name almacena el nombre del centro
 * @param county almacena la provincia del centro
 * @param city almacena la localidad del centro
 * @param address almacena la dirección del centro
 * @param urgencies indica si el centro tiene urgencias disponibles o no
 */
data class Clinic (
    val id: String,
    val name: String,
    val county: String,
    val city: String,
    val address: String,
    val urgencies: Boolean
)

