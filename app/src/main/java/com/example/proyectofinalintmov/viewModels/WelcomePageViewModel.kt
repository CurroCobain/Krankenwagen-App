package com.example.proyectofinalintmov.viewModels

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class WelcomePageViewModel: ViewModel() {
    // variable que se usa para desplegar el menú de opciones
    var showMenu = MutableStateFlow( false)
        private set
    // variable que vamos a usar para acceder al panel de usario
    var userRegistererd = MutableStateFlow(false)
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
    fun cambiarIdioma(){
        // TODO:  seleccionar archivo de idioma
    }

}