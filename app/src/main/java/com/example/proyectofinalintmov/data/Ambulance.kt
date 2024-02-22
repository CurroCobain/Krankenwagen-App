package com.example.proyectofinalintmov.data

/**
 * Clase que enumera los tipos de ambulancias
 */
enum class AmbulanceTypes { doctor, nurse, driver }
/**
 * Clase que representa las Ambulancias
 * @param id almacena el id de la ambulancia
 * @param plate almacena la matrícula de la ambulancia
 * @param isFree indica si la ambulancia está disponible
 * @param types indica de que tipo es la ambulancia
 */
data class Ambulance(
    val id: String,
    val plate: String,
    val isFree: Boolean,
    val types: AmbulanceTypes
)

