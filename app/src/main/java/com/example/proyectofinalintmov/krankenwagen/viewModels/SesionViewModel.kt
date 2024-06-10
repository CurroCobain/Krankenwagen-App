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
import kotlinx.coroutines.tasks.await

/**
 * ViewModel para la gestión del inicio de sesión y el registro de usuarios
 * @property sesionMessage se usa para mostrar el mensaje del sistema
 * @property nombreDoc se usa para almacenar el nombre del doctor activo
 * @property initOrReg se usa para cambiar entre registro e inicio de sesión
 * @property nuevoPass se usa para almacenar la contraseña del doctor
 * @property nuevoMail se usa para almacenar el email del doctor
 */
class SesionViewModel : ViewModel() {
    // Instancias de Firebase
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    // Estado del mensaje de sesión
    var sesionMessage = MutableStateFlow("")

    // Nombre del Doctor actual
    var nombreDoc = MutableStateFlow("")
        private set

    // Estado de inicio o registro de sesión
    var initOrReg = MutableStateFlow(false)

    // Password del usuario actual
    var nuevoPass = MutableStateFlow("")
        private set

    // Correo del usuario actual
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

    /**
     * Modifica el valor de initOrReg
     */
    fun cambiaInit() {
        initOrReg.value = !initOrReg.value
    }

    /**
     * Recupera el nombre del usuario desde Firestore.
     * Se requiere que la sesión del usuario esté inicializada antes de llamar a esta función.
     */
    fun getUser() {
        // Inicia la sesión del usuario y ejecuta el bloque de código proporcionado cuando la sesión está activa
        sesionInit {
            // Realiza la consulta a Firestore para obtener el nombre del usuario actual
            firestore.collection("Users").whereEqualTo("userId", auth.currentUser?.uid).get()
                .addOnSuccessListener { documents ->
                    // Cuando se obtienen los documentos exitosamente
                    for (document in documents) {
                        // Asigna el nombre del usuario a la variable `nombreDoc`
                        nombreDoc.value = document.getString("name")!!
                    }
                }
                .addOnFailureListener { exception ->
                    // En caso de fallo al obtener el nombre del usuario
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
                            sesionMessage.value = "Usuario y/o contrasena incorrectos"
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR EN JETPACK", "ERROR: ${e.localizedMessage}")
                sesionMessage.value = "Usuario y/o contrasena incorrectos"
            }
        }
    }

    /**
     * Registra un usuario nuevo
     */
    fun createUser(onSuccess: () -> Unit) {
        if (nuevoMail.value.isEmpty() || nuevoPass.value.isEmpty() || nombreDoc.value.isEmpty()) {
            sesionMessage.value = "Debe rellenar todos los campos"
        } else {
            try {
                // Utiliza el servicio de autenticación de Firebase para registrar al usuario
                // por email y contraseña
                auth.createUserWithEmailAndPassword(nuevoMail.value, nuevoPass.value)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Si se registra correctamente, guardamos el usuario en Firestore
                            saveUser(nombreDoc.value)
                            onSuccess()
                        } else {
                            Log.d("ERROR EN FIREBASE", "Error al registrar usuario: ${task.exception?.message}")
                            sesionMessage.value = "Error al registrar usuario: ${task.exception?.message}"
                        }
                    }
            } catch (e: Exception) {
                Log.d("ERROR CREAR USUARIO", "ERROR: ${e.localizedMessage}")
                sesionMessage.value = "Error al registrar usuario: ${e.localizedMessage}"
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
    fun cerrarSesion(onSuccess: () -> Unit) {
        auth.signOut()
        nombreDoc.value = ""
        nuevoMail.value = ""
        nuevoPass.value = ""
        sesionMessage.value = ""
        onSuccess()
    }

    /**
     * Modifica el valor del mensaje del sistema
     */
    fun setMessage(text: String) {
        sesionMessage.value = text
    }

    /**
     * Resetea todos los valores
     */
    fun borrarTodo(){
        nombreDoc.value = ""
        nuevoMail.value = ""
        nuevoPass.value = ""
        sesionMessage.value = ""
    }
}


