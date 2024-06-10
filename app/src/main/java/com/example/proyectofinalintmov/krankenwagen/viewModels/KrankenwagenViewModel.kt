package com.example.proyectofinalintmov.krankenwagen.viewModels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
import com.example.proyectofinalintmov.krankenwagen.data.Urgencia
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlin.system.exitProcess


/**
 * ViewModel de la página principal y que gestiona funciones comunes a varias screens
 * @property listAmbulancias lista que almacena las ambulancias filtradas
 * @property listHospitals lista que almacena los hospitales filtrados
 * @property listUrgencies lista que almacena las urgencias filtradas
 * @property message se usa para mostrar la respuesta del sistema
 * @property createAmb se usa para desplegar el diálogo de creación de ambulancias
 * @property createHosp se usa para desplegar el diálogo de creación de hospitales
 * @property editAmb se usa para desplegar el diálogo de edición de ambulancias
 * @property editHosp se usa para desplegar el diálogo de edición de hospitales
 * @property provinciaFiltrar se usa para almacenar la provincia por la que se filtran los hospitales
 * @property filteredUrgencies se usa para filtrar las urgencias completadas o sin finalizar en función de su valor
 */
class KrankenwagenViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    val message = MutableStateFlow("")

    // variable que permite activar la creación de una ambulancia
    var createAmb = MutableStateFlow(false)

    // variable que permite activar la creación de una urgencia
    var createUrg = MutableStateFlow(false)

    // variable que permite activar la creación de un hospital
    var createHosp = MutableStateFlow(false)


    // lista de ambulancias filtradas
    var listAmbulancias = MutableStateFlow(mutableListOf<Ambulance>())

    // lista de hospitales filtrados
    val listHospitals = MutableStateFlow(mutableListOf<Hospital>())

    // variable que permite activar la edición de una ambulancia
    val editAmb = MutableStateFlow(false)

    // variable que permite activar la edición de un hospital
    var editHosp = MutableStateFlow(false)

    // variable que permite activar la edición de una urgencia
    var editUrg = MutableStateFlow(false)

    //variable que se uso para determinar si se filtra por provincia o no
    val provinciaFiltrar = MutableStateFlow("")

    // lista de urgencias
    val listUrgencies = MutableStateFlow(mutableListOf<Urgencia>())

    // variable booleana que sirve para filtrar las urgencias completas o sin finalizar en función de su valor
    val filteredUrgencies = MutableStateFlow(false)
    val updatedInfo = MutableStateFlow(0)

    /**
     * Función para cambiar el valor de filteredUrgencias
     */
    fun setFiltered(){
        filteredUrgencies.value = !filteredUrgencies.value
    }

    /**
     * Obtiene la lista de hospitales para una provincia específica desde Firestore.
     * @param provincia La provincia para la cual se desean obtener los hospitales.
     * @param onSuccess La acción a ejecutar cuando se obtienen los hospitales exitosamente.
     */
    fun getHosp(provincia: String, onSuccess: () -> Unit) {
        // Se vacía la lista de hospitales para evitar duplicados
        listHospitals.value.clear()

        // Se inicia una corutina para la operación asíncrona
        viewModelScope.launch {
            // Se realiza la consulta a Firestore para obtener los hospitales de la provincia especificada
            firestore.collection("Hospitals").whereEqualTo("county", provincia).get()
                .addOnSuccessListener { documents ->
                    // Cuando se obtienen los documentos exitosamente
                    for (document in documents) {
                        // Se añade cada hospital a la lista
                        listHospitals.value.add(document.toObject(Hospital::class.java))
                        // Se ejecuta la acción onSuccess para manejar el éxito de la operación
                        onSuccess()
                    }
                }
                .addOnFailureListener { exception ->
                    // En caso de fallo al obtener los hospitales
                    message.value = "Error al obtener la lista de hospitales"
                }
        }
    }


    /**
     * Obtiene todas las ambulancias desde Firestore.
     * @param onSuccess La acción a ejecutar cuando se obtienen las ambulancias exitosamente.
     */
    fun getAllAmb(onSuccess: () -> Unit) {
        // Se limpia la caché local de Firestore para asegurar la obtención de los datos más recientes
        firestore.clearPersistence()

        // Se vacía la lista de ambulancias para evitar duplicados
        listAmbulancias.value.clear()

        // Se inicia una corutina para la operación asíncrona
        viewModelScope.launch {
            // Se realiza la consulta a Firestore para obtener todas las ambulancias
            firestore.collection("Ambulances")
                .get()
                .addOnSuccessListener { documents ->
                    // Cuando se obtienen los documentos exitosamente
                    for (document in documents) {
                        // Se añade cada ambulancia a la lista
                        listAmbulancias.value.add(Ambulance.deserializeAmbulance(document))
                    }
                }
                .addOnCompleteListener {
                    // Cuando la operación se completa exitosamente
                    // Se ejecuta la acción onSuccess para manejar el éxito de la operación
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    // En caso de fallo al obtener las ambulancias
                    // Se establece un mensaje de error para manejarlo en la interfaz de usuario
                    message.value = "Error al recuperar las ambulancias"
                }
        }
    }

    /**
     * Filtra las ambulancias por hospital de referencia desde Firestore.
     * @param hospital El hospital de referencia por el cual se desean filtrar las ambulancias.
     * @param onSuccess La acción a ejecutar cuando se obtienen las ambulancias exitosamente.
     */
    fun getAmb(hospital: String, onSuccess: () -> Unit) {
        // Se limpia la caché local de Firestore para asegurar la obtención de los datos más recientes
        firestore.clearPersistence()

        // Se vacía la lista de ambulancias para evitar duplicados
        listAmbulancias.value.clear()

        // Se realiza la consulta a Firestore para obtener las ambulancias filtradas por hospital de referencia
        firestore.collection("Ambulances").whereEqualTo("hospital", hospital).get()
            .addOnSuccessListener { documents ->
                // Cuando se obtienen los documentos exitosamente
                for (document in documents) {
                    // Se añade cada ambulancia filtrada por hospital a la lista
                    listAmbulancias.value.add(Ambulance.deserializeAmbulance(document))
                    // Se ejecuta la acción onSuccess para manejar el éxito de la operación
                    onSuccess()
                }
            }
            .addOnFailureListener { exception ->
                // En caso de fallo al obtener las ambulancias filtradas por hospital
                message.value = "Error al obtener la lista de ambulancias"
            }
    }

    /**
     * Obtiene todos los hospitales desde Firestore.
     * @param onSuccess La acción a ejecutar cuando se obtienen los hospitales exitosamente.
     */
    fun getAllHosp(onSuccess: () -> Unit) {
        // Se inicia una corutina para la operación asíncrona
        viewModelScope.launch {
            // Se limpia la caché local de Firestore para asegurar la obtención de los datos más recientes
            firestore.clearPersistence()

            // Se vacía la lista de hospitales para evitar duplicados
            listHospitals.value.clear()

            // Se realiza la consulta a Firestore para obtener todos los hospitales
            firestore.collection("Hospitals")
                .get()
                .addOnSuccessListener { documents ->
                    // Cuando se obtienen los documentos exitosamente
                    for (document in documents) {
                        // Se añade cada hospital a la lista
                        listHospitals.value.add(document.toObject(Hospital::class.java))
                    }
                }
                .addOnCompleteListener {
                    // Cuando la operación se completa exitosamente
                    // Se ejecuta la acción onSuccess para manejar el éxito de la operación
                    onSuccess()
                }
                .addOnFailureListener { exception ->
                    // En caso de fallo al obtener los hospitales
                    // Se establece un mensaje de error para manejarlo en la interfaz de usuario
                    message.value = "Error al recuperar los hospitales"
                }
        }
    }

    /**
     * Función para obtener la lista de urgencias de la base de datos
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun getUrgencies(onSuccess: () -> Unit) {
        listUrgencies.value.clear()
        viewModelScope.launch {
            firestore.collection(("Urgencias")).whereEqualTo("complete", filteredUrgencies.value)
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        listUrgencies.value.add(Urgencia.fromDocumentSnapshot(document))
                        message.value = "Listado actualizado"
                        onSuccess()
                    }
                }
                .addOnFailureListener {
                    message.value = "Error al recuperar las urgencias"
                }
        }
    }

    /**
     * Cierra la aplicación
     */
    fun closeApp() {
        exitProcess(0)
    }

    /**
     * Cambia el valor  de editAmb
     */
    fun activaEditAmb() {
        editAmb.value = !editAmb.value
    }

    /**
     * Cambia el valor  de editHosp
     */
    fun activaEditHosp() {
        editHosp.value = !editHosp.value
    }

    /**
     * Cambia el valor  de editUrg
     */
    fun activaEditUrg() {
        editUrg.value = !editUrg.value
    }


    /**
     * Activa o desactiva la creación de ambulancias
     */
    fun acCreateAmb() {
        createAmb.value = !createAmb.value
    }

    /**
     * Activa o desactiva la creación de hospitales
     */
    fun acCreateHosp() {
        createHosp.value = !createHosp.value
    }

    /**
     * Activa o desactiva la creación de urgencias
     */
    fun acCreateUrg(){
        createUrg.value = !createUrg.value
    }

    /**
     * Función que modifica el valor de provinciaFiltrar
     */
    fun setProv(text: String) {
        provinciaFiltrar.value = text
    }

    /**
     * Aumenta el valor de updatedInfo, se usa para forzar la actualización de la pantalla
     */
    fun increaseUpdateInfo(){
        updatedInfo.value += 1
    }

}
