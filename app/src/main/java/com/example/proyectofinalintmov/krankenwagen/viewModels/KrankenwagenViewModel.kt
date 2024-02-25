package com.example.proyectofinalintmov.krankenwagen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.data.Clinic
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


/**
 * viewModel de la app
 * @param showMenu se usa para desplegar el menú de opciones
 * @param userRegistered se usa para gestionar el acceso a las funciones de edición de la app
 * @param listAmbulances lista que almacena las ambulancias filtradas
 * @param listCentros lista que almacena los centros filtrados
 * @param listHospitals lista que almacena los hospitales filtrados
 */
class KrankenwagenViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    var message = MutableStateFlow("")

    // variable que se usa para desplegar el menú de opciones
    var showMenu = MutableStateFlow(false)
        private set

    // variable que permite activar la creación de una ambulancia
    var createAmb = MutableStateFlow(false)

    // variable que permite activar la creación de un hospital
    var createHosp = MutableStateFlow(false)

    // variable que vamos a usar para acceder al panel de usario
    var userRegistererd = MutableStateFlow(false)
        private set

    // lista de ambulancias filtradas
    var listAmbulancias = MutableStateFlow(mutableListOf<Ambulance>())

    // lista de centros filtrados
    var listCentros = MutableStateFlow(mutableListOf<Clinic>())

    // lista de hospitales filtrados
    private val _listHospitals = MutableStateFlow(mutableListOf<Hospital>())
    val listHospitals: StateFlow<MutableList<Hospital>> = _listHospitals.asStateFlow()

    // variable que permite activar la edición de un hospital
    var editHosp = MutableStateFlow(false)

    // variable que permite activar la edición de una ambulancia
    val editAmb = MutableStateFlow(false)

    fun getHosp(provincia: String) {
        firestore.collection("Hospitals").whereEqualTo("county", provincia).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listHospitals.value.add(document.toObject(Hospital::class.java))
                }
            }
            .addOnFailureListener { exception ->
                // TODO: añadir mensaje a campo de información, se debe crear campo de información
            }
    }

    /**
     * Filtra las ambulancias por hospital de referencia
     */
    fun getAmb(hospital: String) {
        firestore.collection("Ambulances").whereEqualTo("hospital", hospital).get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    listAmbulancias.value.add(document.toObject(Ambulance::class.java))
                }
            }
            .addOnFailureListener { exception ->
                // TODO: añadir mensaje a campo de información, se debe crear campo de información 
            }
    }

    fun getAllAmb(onSuccess: () -> Unit) {
        listAmbulancias.value.clear()
        viewModelScope.launch {
            firestore.collection("Ambulances")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        listAmbulancias.value.add(document.toObject(Ambulance::class.java))
                    }
                }
                .addOnCompleteListener {
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    message.value = "Error al recuperar las ambulancias"
                }
        }

    }


    /**
     * Muestra o cierra el menu de opciones
     */
    fun openCloseMenu() {
        showMenu.value = !showMenu.value
    }

    /**
     * Muestra el registro de usuario
     */
    fun openCloseSesion() {
        userRegistererd.value = !userRegistererd.value
    }

    /**
     * Cambia el idioma del programa
     */
    private fun cambiarIdioma() {
        // TODO:  seleccionar archivo de idioma
    }

    fun closeApp() {
        exitProcess(0)

    }

    /**
     * cambia el valor  de editHosp
     */
    fun activaEditHosp() {
        editHosp.value = !editHosp.value
    }

    /**
     * cambia el valor  de editAmb
     */
    fun activaEditAmb() {
        editAmb.value = !editAmb.value
    }



    /**
     * Activa o desactiva la creación de ambulancias
     */
    fun acCreateAmb() {
        createAmb.value = !createAmb.value
    }
}
