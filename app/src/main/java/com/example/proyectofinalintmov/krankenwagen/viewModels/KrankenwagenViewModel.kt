package com.example.proyectofinalintmov.krankenwagen.viewModels

import androidx.lifecycle.ViewModel
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.Clinic
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
import com.example.proyectofinalintmov.krankenwagen.data.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.system.exitProcess


/**
 * viewModel de la app
 * @param showMenu se usa para desplegar el menú de opciones
 * @param userRegistered se usa para gestionar el acceso a las funciones de edición de la app
 * @param listAmbulances lista que almacena las ambulancias filtradas
 * @param listCentros lista que almacena los centros filtrados
 * @param listHospitals lista que almacena los hospitales filtrados
 */
class KrankenwagenViewModel: ViewModel() {
    // variable que se usa para desplegar el menú de opciones
    var showMenu = MutableStateFlow( false)
        private set
    // variable que vamos a usar para acceder al panel de usario
    var userRegistererd = MutableStateFlow(false)
        private set
    // lista de ambulancias filtradas
    var listAmbulancias = MutableStateFlow(mutableListOf<Ambulance>())

    // lista de centros filtrados
    var listCentros = MutableStateFlow(mutableListOf<Clinic>())

    // lista de hospitales filtrados
    private val  _listHospitals = MutableStateFlow(mutableListOf<Hospital>())
    val listHospitals: StateFlow<MutableList<Hospital>> = _listHospitals.asStateFlow()

    // Nombre del usuario actual
    var nombreDoc = MutableStateFlow("")
        private set
    // password del usuario actual
    var nuevoPass = MutableStateFlow("")
        private set
    // correo del usuario actual
    var nuevoMail = MutableStateFlow("")
        private set
    var editHosp = MutableStateFlow(false)

     fun filterBy(filter: String){
        // TODO: realizar filtro de recursos por provincia
    }
    fun getHosp():MutableList<Hospital>{
        val newList : MutableList<Hospital> = mutableListOf()
        listHospitals.value.add(Hospital("h2","hosp2","ccc", "ccc", mutableListOf<Ambulance>(),"calle2"))
        newList.addAll(listHospitals.value)
        return newList
    }

    /**
     * Muestra el menu de opciones
     */
     fun openMenu(){
        showMenu.value = true;
    }

    /**
     * Cierra el menu de opciones
     */
     fun closeMenu(){
        showMenu.value = false;
    }

    /**
     * Muestra el registro de usuario
     */
     fun openSesion(){
        userRegistererd.value = true;
    }

    /**
     * Cierra el registro de usuario
     */
     fun closeSesion(){
        userRegistererd.value = false;
    }

    /**
     * Cambia el idioma del programa
     */
    private fun cambiarIdioma(){
        // TODO:  seleccionar archivo de idioma
    }

    fun closeApp(){
        exitProcess(0)
    }

    /**
     * Asigna el nombre del usuario
     */
     fun cambiaNombre(value: String){
         nombreDoc.value = value
     }

    /**
     * Asigna el password del usuario
     */
    fun cambiaPass(value: String){
        nuevoPass.value = value
    }

    /**
     * Asigna el correo del usuario
     */
    fun cambiaMail(valor: String){
        nuevoMail.value = valor
    }
    fun activaEditHosp(){
        editHosp.value = true
    }

    fun desactivaEditHosp(){
        editHosp.value = false
    }

    // -------------------!!!!!!!!!!!!!!!!!!!!!!!!!!!!! completar esta función !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!------------------------------------
    fun getUser(){
        val user: User? = null
        nombreDoc.value = user!!.name
    }
}