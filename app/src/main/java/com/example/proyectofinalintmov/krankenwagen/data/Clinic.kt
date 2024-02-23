package com.example.proyectofinalintmov.krankenwagen.data

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
    var name: String,
    val county: String,
    val city: String,
    val address: String,
    var urgencies: Boolean
)

