package com.example.proyectofinalintmov.data

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
    val name: String,
    val county: String,
    val city: String,
    val ambulances: MutableList<Ambulance>,
    val address: String,
)