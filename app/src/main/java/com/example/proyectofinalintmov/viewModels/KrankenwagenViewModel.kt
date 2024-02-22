package com.example.proyectofinalintmov.viewModels

import androidx.lifecycle.ViewModel
import com.example.proyectofinalintmov.data.Ambulance
import kotlinx.coroutines.flow.MutableStateFlow
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
        private set
    // lista de centros filtrados
    var listCentros = MutableStateFlow(mutableListOf<Ambulance>())
        private set
    // lista de hospitales filtrados
    var listHospitals = MutableStateFlow(mutableListOf<Ambulance>())
        private set
    // Nombre del usuario actual
    var nombreDoc = MutableStateFlow("")
        private set
    // password del usuario actual
    var nuevoPass = MutableStateFlow("")
        private set
    // correo del usuario actual
    var nuevoMail = MutableStateFlow("")
        private set
     fun filterBy(filter: String){
        // TODO: realizar filtro de recursos por provincia
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
}