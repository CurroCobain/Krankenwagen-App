package com.example.proyectofinalintmov.krankenwagen.viewModels

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalintmov.krankenwagen.data.User
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class SesionViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore
    private val krankenwagenViewModel = KrankenwagenViewModel()

    var nombreDoc = MutableStateFlow("")
        private set

    var inirOrReg = MutableStateFlow(false)

    // password del usuario actual
    var nuevoPass = MutableStateFlow("")
        private set

    // correo del usuario actual
    var nuevoMail = MutableStateFlow("")
        private set

    /**
     * Asigna el nombre del usuario
     */
    fun cambiaNombre(value: String) {
        nombreDoc.value = value
    }

    /**
     * Asigna el password del usuario
     */
    fun cambiaPass(value: String) {
        nuevoPass.value = value
    }

    /**
     * Asigna el correo del usuario
     */
    fun cambiaMail(valor: String) {
        nuevoMail.value = valor
    }

    fun cambiaInit() {
        inirOrReg.value = !inirOrReg.value
    }

    /**
     * Recupera el nombre del usuario
     */
    fun getUser() {
        sesionInit {
            firestore.collection("Users").whereEqualTo("userId", auth.currentUser?.uid).get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        nombreDoc.value = document.getString("name")!!
                    }
                }
                .addOnFailureListener { exception ->
                    Log.w(TAG, "Error getting documents: ", exception)
                }
        }
    }

    /**
     * Inicia sesión
     */
    fun sesionInit(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Utiliza el servicio de autenticación de Firebase para validar al usuario
                // por email y contraseña
                auth.signInWithEmailAndPassword(nuevoMail.value, nuevoPass.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "Usuario y/o contrasena incorrectos")
                            krankenwagenViewModel.message.value =
                                "Usuario y/o contrasena incorrectos"
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
                krankenwagenViewModel.message.value = "Usuario y/o contrasena incorrectos"
            }
        }
    }

    /**
     * Registra un usuario nuevo
     */
    fun createUser(onSuccess: () -> Unit) {
        viewModelScope.launch {
            try {
                // Utiliza el servicio de autenticación de Firebase para registrar al usuario
                // por email y contraseña
                auth.createUserWithEmailAndPassword(nuevoMail.value, nuevoPass.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Si se realiza con éxito, almacenamos el usuario en la colección "Users"
                            saveUser(nombreDoc.value)
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "Error al crear usuario")
                            // showAlert = true
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR CREAR USUARIO", "ERROR: ${e.localizedMessage}")
            }
        }
    }

    /**
     * Guarda un usuario
     */
    private fun saveUser(username: String) {
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = User(
                userId = id.toString(),
                email = email.toString(),
                name = username
            )
            // Añade el usuario a la colección "Users" en la base de datos Firestore
            firestore.collection("Users")
                .add(user)
                .addOnSuccessListener {
                    Log.d(
                        "GUARDAR OK",
                        "Se guardó el usuario correctamente en Firestore"
                    )
                }
                .addOnFailureListener { Log.d("ERROR AL GUARDAR", "ERROR al guardar en Firestore") }
        }
    }

    /**
     * Cierra la sesión del usuario actual
     */
    fun cerrarSesion() {
        auth.signOut()
        nombreDoc.value = ""
        nuevoMail.value = ""
        nuevoPass.value = ""
    }
}
