package com.example.proyectofinalintmov.data


enum class AmbulanceTypes { doctor, nurse, driver }
data class Ambulance(
    val id: String,
    val plate: String,
    val type: Enum<AmbulanceTypes>,
    val isFree: Boolean,
    val services: Int
)

