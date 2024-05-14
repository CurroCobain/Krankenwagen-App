package com.example.proyectofinalintmov.krankenwagen.data

import android.annotation.SuppressLint
import com.google.android.gms.maps.model.LatLng
import java.time.LocalDateTime

/**
* Clase "Urgencia", que almacena la información de la emergencia para transmitirla al
* servicio de emergencias
* @property name: nombre y apellidos del paciente
* @property doc: documento de identidad del usuario
* @property age: edad del usuario
* @property priority: indica el nivel de prioridad de la emergencia
* @property location: localización del usuario
* @property date: fecha de la urgencia
* @property issues: descripción de la urgencia
*/
data class Urgencia(
    var name: String,
    var doc: String,
    var age: Int,
    var priority: Int,
    var address: String,
    var location: LatLng,
    var date: LocalDateTime,
    var issues: String,
    var ambulance: Ambulance?,
    var complete: Boolean
){



    @SuppressLint("NewApi")
    constructor() : this(
        "Paciente1",
        "30876923H",
        24,
        1,
        "",
        LatLng(0.0, 0.0),
        LocalDateTime.now(),
        "Parada cardio respiratoria",
        Ambulance(),
        false
    )
}