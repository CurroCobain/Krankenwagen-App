package com.example.proyectofinalintmov.krankenwagen.data

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.firestore.DocumentSnapshot
import java.sql.Timestamp
import java.time.LocalDateTime
import java.time.ZoneOffset

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
    var id: String,
    var name: String,
    var doc: String,
    var age: Int,
    var priority: Int,
    var address: String,
    var location: Map<String, Double>,
    var date: Timestamp?,
    var issues: String,
    var ambulance: String,
    var complete: Boolean
){


    companion object {
        @SuppressLint("NewApi")
        fun fromDocumentSnapshot(documentSnapshot: DocumentSnapshot): Urgencia {
            val id = documentSnapshot.getString("id") ?: ""
            val name = documentSnapshot.getString("name") ?: ""
            val doc = documentSnapshot.getString("doc") ?: ""
            val age = documentSnapshot.getLong("age")?.toInt() ?: 0
            val priority = documentSnapshot.getLong("priority")?.toInt() ?: 0
            val adress = documentSnapshot.getString("address") ?: ""
            val locationMap = documentSnapshot.get("location") as? Map<*, *>
            val location = if (locationMap != null) {
                val latitude = locationMap["latitude"] as? Double ?: 0.0
                val longitude = locationMap["longitude"] as? Double ?: 0.0
                mutableMapOf("latitude" to latitude, "longitude" to longitude)             } else {
                mutableMapOf("latitude" to 0.0, "longitude" to 0.0)            }
            var date = documentSnapshot.getDate("date") as? Timestamp
            if(date == null){
                date = Timestamp(System.currentTimeMillis())
            }
            val issues = documentSnapshot.getString("issues") ?: ""
            val ambulance = documentSnapshot.getString("ambulance") ?: ""
            return Urgencia(id, name, doc, age, priority, adress, location, date, issues, ambulance,false)
        }
    }

    @SuppressLint("NewApi")
    constructor() : this(
        "urg0",
        "Paciente1",
        "30876923H",
        24,
        1,
        "",
        mutableMapOf("latitude" to 0.0, "longitude" to 0.0),
        Timestamp(System.currentTimeMillis()),
        "Parada cardio respiratoria",
        "No definida",
        false
    )
}