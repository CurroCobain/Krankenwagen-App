package com.example.proyectofinalintmov.krankenwagen.viewModels

import android.annotation.SuppressLint
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
import com.google.firebase.Timestamp
import java.time.LocalDateTime
import java.time.ZoneId

/**
 * ViewModel para la gestión de las urgencias
 *
 * @property id representa el id de la urgencia
 * @property name representa el nombre del paciente
 * @property doc representa el documento de identidad del paciente
 * @property age representa la edad del paciente
 * @property priority representa el nivel de prioridad de la urgencia
 * @property address representa la dirección de la urgencia en formato cadena
 * @property latitude representa la latitud de la localizaión de la urgencia
 * @property longitude representa la longitud de la localizaión de la urgencia
 * @property location variable de tipo LatLng que representa la latitud y longitud de la localizaión de la urgencia
 * @property date representa la fecha de creación de la urgencia
 * @property issues representa la sintomatología del paciente
 * @property complete variable booleana que indica si la urgencia esta finalizada o no
 * @property message representa el mensaje del sistema
 * @property typeOfStreet representa el tipo de vía de la dirección
 * @property streetName representa el nombre de la vía
 * @property streetNumber representa el número de la dirección
 * @property city representa la ciudad de la dirección
 * @property province representa la provincia de la dirección
 * @property postalCode representa el código postal de la dirección
 *
 */
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
    var date = MutableStateFlow<Timestamp?>(null)
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

    /**
     * Función para obtener las coordenadas a partir de una dirección
     */
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


    /**
     * Función para crear una urgencia nueva
     */
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
                        // Obtener la hora actual en la zona horaria local
                        val now = LocalDateTime.now()
                        val localZoneId = ZoneId.systemDefault()
                        val localDateTime = now.atZone(localZoneId).toInstant()

                        // Crear la urgencia con las coordenadas obtenidas
                        val newUrg = Urgencia(
                            newId,
                            name.value,
                            doc.value,
                            ageInt,
                            priorityInt,
                            address.value,
                            mutableMapOf("latitude" to latitude.value, "longitude" to longitude.value),
                            Timestamp(localDateTime.epochSecond, localDateTime.nano),  // Guardar el tiempo local
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
                                onFailure(message.value)
                            }
                    } catch (e: Exception) {
                        // Manejar cualquier excepción durante la creación de la urgencia
                        message.value = "Hubo un fallo al crear la urgencia"
                        onFailure(message.value)
                    }
                }
            } catch (e: Exception) {
                // Manejar cualquier excepción durante la conversión de los valores a Int o consulta de Firestore
                message.value = "Hubo un fallo en la creación de la urgencia, revise los datos"
                onFailure(message.value)
            }
        }
    }


    /**
     * Función para actualizar una urgencia
     */
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
                                "timestamp" to date.value,
                                "issues" to issues.value,
                                "ambulance" to ambulance.value,
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

    /**
     * Función para borrar una urgencia
     */
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


    /**
     * Función para asignar valor la propiedad id
     */
    fun setId(value: String){
        id.value = value
    }

    /**
     * Función para asignar valor la propiedad name
     */
    fun setName(data: String) {
        name.value = data
    }

    /**
     * Función para asignar valor la propiedad doc
     */
    fun setDoc(data: String) {
        doc.value = data
    }

    /**
     * Función para asignar valor la propiedad age
     */
    fun setAge(data: String) {
        age.value = data
    }

    /**
     * Función para asignar valor la propiedad date
     */
     @RequiresApi(Build.VERSION_CODES.O)
     fun setDate(data: Timestamp){
         date.value = data
     }

    /**
     * Función para asignar valor la propiedad priority
     */
    fun setPriority(data: String) {
        priority.value = data
    }

    /**
     * Función para asignar valor la propiedad typeOfStreet
     */
    fun setTypeOfStreet(value: String) { typeOfStreet.value = value }

    /**
     * Función para asignar valor la propiedad streetName
     */
    fun setStreetName(value: String) { streetName.value = value }

    /**
     * Función para asignar valor la propiedad streetNumber
     */
    fun setStreetNumber(value: String) { streetNumber.value = value }

    /**
     * Función para asignar valor la propiedad city
     */
    fun setCity(value: String) { city.value = value }

    /**
     * Función para asignar valor la propiedad province
     */
    fun setProvince(value: String) { province.value = value }

    /**
     * Función para asignar valor la propiedad postalcode
     */
    fun setPostalCode(value: String) { postalCode.value = value }


    /**
     * Función para asignar valor la propiedad address
     */
    fun setAddress() {
        address.value =
            "${typeOfStreet.value} ${streetName.value}, ${streetNumber.value}, ${city.value}, ${province.value}, ${postalCode.value}"
    }

    /**
     * Función para convertir una cadena y asignar los valores a las propiedades necesarias
     */
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

    /**
     * Función para asignar valor la propiedad issues
     */
    fun setIssues(data: String) {
        issues.value = data
    }

    /**
     * Función para asignar valor la propiedad complete, actualmente no se usa pero se mantiene para posbiles mejoras
     */
    fun setComplete() {
        complete.value = true
    }

    /**
     * Función para asignar valor a las propiedades de una urgencia recibida
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun setAll(urgencia: Urgencia){
        setId(urgencia.id)
        setDoc(urgencia.doc)
        setName(urgencia.name)
        setAge(urgencia.age.toString())
        setPriority(urgencia.priority.toString())
        deserializeAddres(urgencia.address)
        setAddress()
        setIssues(urgencia.issues)
        setDate(urgencia.date!!)
    }

    /**
     * Función para resetear los valores de las propiedades a su valor por defecto
     */
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

    /**
     * Función para separar el tipo de vía del resto de la dirección
     */
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