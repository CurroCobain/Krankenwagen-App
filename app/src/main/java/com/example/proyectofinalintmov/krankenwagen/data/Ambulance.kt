package com.example.proyectofinalintmov.krankenwagen.data

import com.google.firebase.firestore.DocumentSnapshot

/**
 * Clase que enumera los tipos de ambulancias
 */
enum class AmbulanceTypes { doctor, nurse, driver }
/**
 * Clase que representa las Ambulancias
 * @property id almacena el id de la ambulancia
 * @property plate almacena la matrícula de la ambulancia
 * @property free indica si la ambulancia está disponible
 * @property types indica de que tipo es la ambulancia
 * @property hospital indica el hospital de referencia
 * @property ambLocation indica la localización actual de la ambulancia
 */
data class Ambulance(
    val id: String,
    val plate: String,
    var free: Boolean,
    var types: AmbulanceTypes,
    var hospital: String,
    var ambLocation: MutableMap<String, Double>
){
    constructor() : this("amb1",
        "No definida",
        true, AmbulanceTypes.doctor,
        "",
        mutableMapOf("latitude" to 0.0, "longitude" to 0.0))

    companion object{
        /**
         * Función para deserializar un objeto Ambulance desde Firestore
         * @param documentSnapshot El documento Firestore que contiene los datos de la ambulancia
         * @return Una instancia de Ambulance deserializada desde Firestore
         */
        fun deserializeAmbulance(documentSnapshot: DocumentSnapshot): Ambulance {
            val data = documentSnapshot.data ?: throw IllegalArgumentException("Documento vacío")

            val id = data["id"] as? String ?: throw IllegalArgumentException("Campo 'id' no encontrado")
            val plate = data["plate"] as? String ?: throw IllegalArgumentException("Campo 'plate' no encontrado")
            val isFree = data["free"] as? Boolean ?: throw IllegalArgumentException("Campo 'isFree' no encontrado")
            val types = AmbulanceTypes.valueOf((data["types"] as? String ?: throw IllegalArgumentException("Campo 'types' no encontrado")).toLowerCase())
            val hospital = data["hospital"] as? String ?: throw IllegalArgumentException("Campo 'hospital' no encontrado")

            val locationData = documentSnapshot.get("ambLocation") as? Map<*, *>
            val location = if(locationData != null){
                val latitude = locationData["latitude"] as? Double ?: 0.0
                val longitude = locationData["longitude"] as? Double ?: 0.0
                mutableMapOf("latitude" to latitude,"longitude" to longitude)
            } else{
                mutableMapOf("latitude" to 0.0, "longitude" to 0.0)
            }
            return Ambulance(id, plate, isFree, types, hospital, location)
        }
    }
}
