package com.example.proyectofinalintmov.krankenwagen.viewModels

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalintmov.krankenwagen.apis.GeocodingService
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.Urgencia
import kotlinx.coroutines.flow.MutableStateFlow
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime

class UrgenciesViewModel : ViewModel () {

    var name = MutableStateFlow("")
    var doc = MutableStateFlow("")
    var age = MutableStateFlow("")
    var priority = MutableStateFlow("")
    var address = MutableStateFlow("")
    var location = MutableStateFlow(LatLng(0.0, 0.0))
    @RequiresApi(Build.VERSION_CODES.O)
    var date = MutableStateFlow<LocalDateTime?>(null)
    var issues = MutableStateFlow("")
    var ambulance = MutableStateFlow(Ambulance())
    var complete = MutableStateFlow(false)
    val message = MutableStateFlow("")
    private val firestore = Firebase.firestore

    // URL base del servicio de geocodificación
    private val BASE_URL = "https://nominatim.openstreetmap.org/search?"

    // Servicio de geocodificación
    private val geocodingService: GeocodingService

    init {
        // Configurar Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear instancia del servicio de geocodificación
        geocodingService = retrofit.create(GeocodingService::class.java)
    }

    // Función para obtener las coordenadas a partir de una dirección
    private fun getCoordinatesFromAddress(address: String): Boolean {
        viewModelScope.launch {
            try {
                // Llamar al método de servicio para obtener las coordenadas
                val response = geocodingService.getCoordinates("json", address)

                // Procesar la respuesta
                if (response.isNotEmpty()) {
                    val firstResult = response.first() // Tomar el primer resultado
                    val latitud = firstResult.lat.toDouble()
                    val longitud = firstResult.lon.toDouble()
                    location.value = LatLng(latitud, longitud)
                }else{
                    message.value = "Respuesta vacía"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } // Esperar a que la corutina termine antes de devolver el resultado
        return true
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun createUrg(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                getCoordinatesFromAddress(address.value)
                val newUrg = Urgencia(
                    name.value,
                    doc.value,
                    age.value.toInt(),
                    priority.value.toInt(),
                    address.value,
                    location.value,
                    LocalDateTime.now(),
                    issues.value,
                    ambulance.value,
                    complete.value
                )
                firestore.collection("Urgencias")
                    .add(newUrg)
                    .addOnSuccessListener {
                        // Si se completa correctamente, modificamos el mensaje del sistema
                        message.value =
                            "Se actualizó la urgencia en la base de datos"
                        onSuccess()
                    }
                    // Si hay fallo en el proceso lo indicamos mediante el mensaje del sistema
                    .addOnFailureListener {
                        message.value =
                            "No se pudo guardar la urgencia, revise los datos"
                    }
            }catch (e: Exception) {
                message.value = "Hubo un fallo en la creación de la uurgencia, revise los datos"

            }
        }
    }

    fun setName(data: String){
        name.value = data
    }
    fun setDoc(data: String){
        doc.value = data
    }
    fun setAge(data: String){
        age.value = data
    }
    fun setPriority(data: String){
        priority.value = data
    }
    fun setAddress(data: String){
        address.value = data
    }
    fun setIssues(data: String){
        issues.value = data
    }
    fun setComplete(){
        complete.value = true
    }
    fun deleteMiUrgencia() {
        name.value = ""
        doc.value = ""
        age.value = ""
        priority.value = ""
        address.value = ""
        location.value = LatLng(0.0, 0.0)
        issues.value = ""
        ambulance.value = Ambulance()
        complete.value = false
    }

}