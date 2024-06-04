package com.example.proyectofinalintmov.krankenwagen.viewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalintmov.krankenwagen.apis.GoogleGeocodingService
import com.example.proyectofinalintmov.krankenwagen.data.GeocodingResponse
import com.example.proyectofinalintmov.krankenwagen.data.Urgencia
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.time.LocalDateTime

class UrgenciesViewModel : ViewModel() {

    var id = MutableStateFlow("")
    var name = MutableStateFlow("")
    var doc = MutableStateFlow("")
    var age = MutableStateFlow("")
    var priority = MutableStateFlow("")
    var address = MutableStateFlow("")
    var latitude = MutableStateFlow(0.0)
    var longitude = MutableStateFlow(0.0)
    var location = MutableStateFlow<MutableMap<String, Double>>(mutableMapOf())
    @RequiresApi(Build.VERSION_CODES.O)
    var date = MutableStateFlow<LocalDateTime?>(null)
    var issues = MutableStateFlow("")
    var ambulance = MutableStateFlow("No definida")
    var complete = MutableStateFlow(false)
    val message = MutableStateFlow("")
    val typeOfStreet = MutableStateFlow("")
    val streetName = MutableStateFlow("")
    val streetNumber = MutableStateFlow("")
    val city = MutableStateFlow("")
    val province = MutableStateFlow("")
    val postalCode = MutableStateFlow("")
    private val firestore = Firebase.firestore

    // URL base del servicio de geocodificación de Google
    private val BASE_URL = "https://maps.googleapis.com/maps/api/"

    // Clave API de Google Maps
    private val API_KEY = "AIzaSyDPyoQIrr78e7sUzqWcUUFsC0GBLlSNzBc"

    // Servicio de geocodificación
    private val geocodingService: GoogleGeocodingService

    init {
        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear instancia del servicio de geocodificación
        geocodingService = retrofit.create(GoogleGeocodingService::class.java)
    }

    // Función para obtener las coordenadas a partir de una dirección
    private fun getCoordinatesFromAddress(address: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Llamar al método de servicio para obtener las coordenadas
                val response: GeocodingResponse = geocodingService.getCoordinates(address, API_KEY)

                // Procesar la respuesta
                if (response.results.isNotEmpty()) {
                    val location = response.results.first().geometry.location
                    latitude.value = location.lat
                    longitude.value = location.lng
                    onSuccess()
                } else {
                    message.value = "Respuesta vacía"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                message.value = "Error al obtener las coordenadas"
            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.O)
    fun createUrg(onSuccess: (String) -> Unit, onFailure: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Verificar si los valores de edad y prioridad son enteros
                val ageInt = age.value.toIntOrNull() ?: throw IllegalArgumentException("La edad no es un número entero válido")
                val priorityInt = priority.value.toIntOrNull() ?: throw IllegalArgumentException("La prioridad no es un número entero válido")

                // Obtener todas las urgencias para encontrar el id más alto
                val querySnapshot = firestore.collection("Urgencias")
                    .get()
                    .await() // Utilizamos await para esperar el resultado de la consulta

                // Encontrar el número más alto en los ids existentes
                val highestId = querySnapshot.documents
                    .mapNotNull { doc ->
                        val id = doc.getString("id")
                        id?.removePrefix("urg")?.toIntOrNull()
                    }
                    .maxOrNull() ?: 0

                // Asignar el nuevo id basado en el más alto encontrado
                val newId = "urg${highestId + 1}"

                // Obtener las coordenadas antes de crear la urgencia
                getCoordinatesFromAddress(address.value) {
                    try {
                        // Crear la urgencia con las coordenadas obtenidas
                        val newUrg = Urgencia(
                            newId,
                             name.value,
                            doc.value,
                            ageInt,
                            priorityInt,
                            address.value,
                            mutableMapOf("latitude" to latitude.value, "longitude" to longitude.value),
                            Timestamp(System.currentTimeMillis()),
                            issues.value,
                            "No definida",
                            complete.value
                        )

                        // Guardar la urgencia en la base de datos
                        firestore.collection("Urgencias")
                            .add(newUrg)
                            .addOnSuccessListener {
                                // Si se completa correctamente, modificamos el mensaje del sistema
                                val newMessage = "Se actualizó la urgencia en la base de datos"
                                message.value = newMessage
                                onSuccess(newMessage)
                            }
                            .addOnFailureListener {
                                // Si hay fallo en el proceso lo indicamos mediante el mensaje del sistema
                                message.value = "No se pudo guardar la urgencia, revise los datos"
                                onFailure(message.value )
                            }
                    } catch (e: Exception) {
                        // Manejar cualquier excepción durante la creación de la urgencia
                        message.value = "Hubo un fallo al crear la urgencia"
                        onFailure(message.value )
                    }
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción durante la conversión de los valores a Int o consulta de Firestore
                message.value = "Hubo un fallo en la creación de la urgencia, revise los datos"
                onFailure(message.value )
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun updateUrgency(
        urgenciaId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Verificar si los valores de edad y prioridad son enteros
                val ageInt = age.value.toIntOrNull() ?: throw IllegalArgumentException("La edad no es un número entero válido")
                val priorityInt = priority.value.toIntOrNull() ?: throw IllegalArgumentException("La prioridad no es un número entero válido")

                // Consultar para obtener la referencia del documento de la urgencia a actualizar
                val querySnapshot = firestore.collection("Urgencias")
                    .whereEqualTo("id", urgenciaId)
                    .get()
                    .await()

                if (querySnapshot.documents.isNotEmpty()) {
                    val documentSnapshot = querySnapshot.documents[0]
                    val docRef = documentSnapshot.reference

                    // Obtener las coordenadas antes de actualizar la urgencia
                    getCoordinatesFromAddress(address.value) {
                        try {
                            // Crear el mapa de actualización con los nuevos valores
                            val updateData = mapOf(
                                "name" to name.value,
                                "doc" to doc.value,
                                "age" to ageInt,
                                "priority" to priorityInt,
                                "address" to address.value,
                                "location" to mutableMapOf("latitude" to latitude.value, "longitude" to longitude.value),
                                "timestamp" to Timestamp(System.currentTimeMillis()),
                                "issues" to issues.value,
                                "ambulance" to "No definida",
                                "complete" to complete.value
                            )

                            // Actualizar la urgencia en la base de datos
                            docRef.update(updateData)
                                .addOnSuccessListener {
                                    val newMessage = "Urgencia actualizada correctamente"
                                    message.value = newMessage
                                    onSuccess(newMessage)
                                }
                                .addOnFailureListener {
                                    message.value = "No se pudo actualizar la urgencia, revise los datos"
                                    onFailure(message.value)
                                }
                        } catch (e: Exception) {
                            message.value = "Hubo un fallo al actualizar la urgencia"
                            onFailure(message.value)
                        }
                    }
                } else {
                    message.value = "La urgencia con ID $urgenciaId no existe"
                    onFailure(message.value)
                }
            } catch (e: Exception) {
                message.value = "Hubo un fallo al actualizar la urgencia, revise los datos"
                onFailure(message.value)
            }
        }
    }

    fun deleteUrgency(
        urgenciaId: String,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        viewModelScope.launch {
            try {
                // Consultar para obtener la referencia del documento de la urgencia a eliminar
                val querySnapshot = firestore.collection("Urgencias")
                    .whereEqualTo("id", urgenciaId)
                    .get()
                    .await()

                if (querySnapshot.documents.isNotEmpty()) {
                    val documentSnapshot = querySnapshot.documents[0]
                    val docRef = documentSnapshot.reference

                    // Eliminar la urgencia de la base de datos
                    docRef.delete()
                        .addOnSuccessListener {
                            val successMessage = "Urgencia eliminada correctamente"
                            message.value = successMessage
                            onSuccess(successMessage)
                        }
                        .addOnFailureListener { e ->
                            val failureMessage = "Error al eliminar la urgencia: ${e.message}"
                            message.value = failureMessage
                            onFailure(failureMessage)
                        }
                } else {
                    message.value = "La urgencia con ID $urgenciaId no existe"
                    onFailure(message.value)
                }
            } catch (e: Exception) {
                val exceptionMessage = "Hubo un fallo al intentar eliminar la urgencia: ${e.message}"
                message.value = exceptionMessage
                onFailure(exceptionMessage)
            }
        }
    }


    fun setId(value: String){
        id.value = value
    }
    fun setName(data: String) {
        name.value = data
    }

    fun setDoc(data: String) {
        doc.value = data
    }

    fun setAge(data: String) {
        age.value = data
    }

    fun setPriority(data: String) {
        priority.value = data
    }
    fun setTypeOfStreet(value: String) { typeOfStreet.value = value }
    fun setStreetName(value: String) { streetName.value = value }
    fun setStreetNumber(value: String) { streetNumber.value = value }
    fun setCity(value: String) { city.value = value }
    fun setProvince(value: String) { province.value = value }
    fun setPostalCode(value: String) { postalCode.value = value }

    fun setAddress() {
        address.value =
            "${typeOfStreet.value} ${streetName.value}, ${streetNumber.value}, ${city.value}, ${province.value}, ${postalCode.value}"
    }
    private fun deserializeAddres(receivedAddres: String){
        try {
            val listAddress = receivedAddres.split(",")
            val tipoYNombre = separarPrimeraPalabra(listAddress[0])
            setTypeOfStreet(tipoYNombre.first)
            setStreetName(tipoYNombre.second)
            setStreetNumber(listAddress[1])
            setCity(listAddress[2])
            setProvince(listAddress[3])
            setPostalCode(listAddress[4])
        } catch (e: Exception){
            message.value = "Hubo un fallo al convertir la dirección"
        }
    }

    fun setIssues(data: String) {
        issues.value = data
    }

    fun setComplete() {
        complete.value = true
    }

    fun setAll(urgencia: Urgencia){
        setId(urgencia.id)
        setDoc(urgencia.doc)
        setName(urgencia.name)
        setAge(urgencia.age.toString())
        setPriority(urgencia.priority.toString())
        deserializeAddres(urgencia.address)
        setAddress()
        setIssues(urgencia.issues)


    }

    fun resetMiUrgencia() {
        id.value = ""
        name.value = ""
        doc.value = ""
        age.value = ""
        priority.value = ""
        address.value = ""
        location.value = mutableMapOf()
        issues.value = ""
        ambulance.value = "No definida"
        complete.value = false
        typeOfStreet.value = ""
        streetName.value = ""
        streetNumber.value = ""
        city.value = ""
        province.value = ""
        postalCode.value = ""
    }

    private fun separarPrimeraPalabra(cadena: String): Pair<String, String> {
        val indiceEspacio = cadena.indexOf(' ')
        return if (indiceEspacio != -1) {
            val primeraPalabra = cadena.substring(0, indiceEspacio)
            val restoCadena = cadena.substring(indiceEspacio + 1)
            Pair(primeraPalabra, restoCadena)
        } else {
            Pair(cadena, "")
        }
    }
}