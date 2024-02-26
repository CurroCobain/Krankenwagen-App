package com.example.proyectofinalintmov.krankenwagen.data

/**
 * Clase Hospital
 * @param id almacena el id del hospital
 * @param name almacena el nombre del hospital
 * @param county almacena la provincia del hospital
 * @param city almacena la localidad del hospital
 * @param ambulances de tipo mutableList, almacena las ambulancias que dependen del hospital
 * @param address almacena la direccion del hospital
 */
data class Hospital(
    val id: String,
    var name: String,
    val county: String,
    val city: String,
    val address: String,
    )
{
    constructor(): this("hosp1", "Hospital random de Cádiz", "Cádiz","Cádiz","Calle 1 de Cádiz")
}