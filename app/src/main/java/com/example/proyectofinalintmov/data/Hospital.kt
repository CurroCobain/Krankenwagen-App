package com.example.proyectofinalintmov.data

data class Hospital(
    val id: String,
    val name: String,
    val county: String,
    val city: String,
    val ambulances: MutableList<Ambulance>,
    val address: String,
    val beds: Int
)