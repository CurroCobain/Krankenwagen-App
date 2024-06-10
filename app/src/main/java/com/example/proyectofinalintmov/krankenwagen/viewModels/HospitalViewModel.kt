package com.example.proyectofinalintmov.krankenwagen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la gestión de los Hospitales
 * @property idHosp se usa para almacenar el id del hospital actual
 * @property name se usa para almacenar el nombre del hospital actual
 * @property county se usa para almacenar la provincia del hospital actual
 * @property city se usa para almacenar la ciudad del hospital actual
 * @property address se usa para almacenar la dirección del hospital actual
 * @property hospMessage se usa para mostrar la respuesta del sistema
 */
class HospitalViewModel : ViewModel() {
    // Inicialización del objeto de Firestore para acceder a la base de datos Firestore de Firebase
    private val firestore = Firebase.firestore

    // Almacena el id del hospital actual
    var idHosp = MutableStateFlow("")
        private set

    // Almacena el nombre del hospital actual
    var name = MutableStateFlow("")
        private set

    // Almacena la provincia del hospital actual
    var county = MutableStateFlow("")
        private set

    // Almacena la ciudad del hospital actual
    var city = MutableStateFlow("")
        private set

    // Almacena la dirección del hospital actual
    var address = MutableStateFlow("")
        private set

    // Almacena el mensaje de respuesta del sistema
    var hospMessage = MutableStateFlow("")
        private set


    /**
     * Función para guardar un hospital en la base de datos
     */
    fun saveHospital(onSuccess: () -> Unit) {
        if (idHosp.value.isEmpty() || name.value.isEmpty() || county.value.isEmpty() || city.value.isEmpty() || address.value.isEmpty()) {
            hospMessage.value = "Debe rellenar todos los campos"
        } else {
            viewModelScope.launch {
                // Creamos un objeto de tipo Hospital con los valores actuales
                val myHosp = Hospital(
                    idHosp.value,
                    name.value,
                    county.value,
                    city.value,
                    address.value
                )
                // Verificamos si ya existe un hospital con el mismo nombre
                firestore.collection("Hospitals")
                    .whereEqualTo("id", myHosp.id) // Buscamos documentos con el mismo nombre
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty) {
                            // Si existe un hospital con el mismo nombre, mostramos un mensaje de error
                            hospMessage.value =
                                "Ya existe un hospital con este nombre en la base de datos"
                        } else {
                            firestore.collection("Hospitals")
                                // Guardamos el objeto en la base de datos
                                .add(myHosp)
                                .addOnSuccessListener {
                                    // Si se completa correctamente modificamos el mensaje del sistema
                                    hospMessage.value = "Se guardó el hospital en la base de datos"
                                    onSuccess()
                                }
                                // Si hay fallo en el proceso lo indicamos mediante el mensaje del sistema
                                .addOnFailureListener {
                                    hospMessage.value =
                                        "No se pudo guardar el hospital, revise los datos"
                                }
                        }
                    }
            }
        }

    }

    /**
     * Función para actualizar un hospital en la base de datos
     */
    fun updateHosp(onSuccess: () -> Unit) {
        // Consultamos la base de datos para encontrar el hospital con el campo "id" igual a idHosp.value
        firestore.collection("Hospitals")
            .whereEqualTo("id", idHosp.value)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Si la consulta devuelve resultados
                if (!querySnapshot.isEmpty) {
                    // Iteramos sobre los documentos encontrados (en caso de que haya más de uno)
                    querySnapshot.documents.forEach { doc ->
                        // Convertimos el documento a un objeto de tipo Hospital
                        val hospital = doc.toObject<Hospital>()
                        hospital?.let {
                            // Actualizamos los valores del objeto
                            val updatedHosp = Hospital(
                                idHosp.value,
                                name.value,
                                county.value,
                                city.value,
                                address.value
                            )
                            // Guardamos los nuevos valores en el documento
                            doc.reference.set(updatedHosp)
                                .addOnSuccessListener {
                                    // La actualización fue exitosa
                                    hospMessage.value = "Se actualizó el hospital correctamente"
                                    onSuccess()
                                }
                                .addOnFailureListener { e ->
                                    // Ocurrió un error durante la actualización
                                    hospMessage.value =
                                        "No se puedo actualizar el hospital, revise los datos"
                                }
                        }
                    }
                } else {
                    // No se encontró ningún hospital con el campo "id" igual a idHosp.value
                    hospMessage.value = "El hospital con ID indicado no existe en la base de datos"
                }
            }
            .addOnFailureListener { e ->
                // Ocurrió un error al realizar la consulta
                hospMessage.value = "Error al acceder a la base de datos"
            }
    }

    /**
     * Función para dar coherencia a la base de datos de ambulancias después de borrar un hospital
     */
    fun ambulanceCoherence(idHosp: String) {
        viewModelScope.launch {
            // Referencia a la colección de ambulancias que pertenecen al hospital borrado
            val ambulancesRef = firestore.collection("Ambulances").whereEqualTo("hospital", idHosp)
            ambulancesRef.get().addOnSuccessListener { querySnapshot ->
                // Si hay ambulancias asociadas al hospital borrado
                if (!querySnapshot.isEmpty) {
                    // Iteramos sobre cada documento de ambulancia
                    querySnapshot.documents.forEach { doc ->
                        // Actualizamos el campo "hosp" de cada ambulancia al valor por defecto "hosp0"
                        doc.reference.update("hospital", "hosp0")
                            .addOnSuccessListener {
                                // Se actualizó correctamente el campo "hosp" de la ambulancia
                                hospMessage.value = "Se actualizó la coherencia de las ambulancias en la base de datos"
                            }
                            .addOnFailureListener { e ->
                                // Hubo un error al actualizar el campo "hosp" de la ambulancia
                                hospMessage.value = "Error al actualizar la coherencia de las ambulancias en la base de datos"
                            }
                    }
                } else {
                    // No hay ambulancias asociadas al hospital borrado
                    hospMessage.value = "No hay ambulancias asociadas al hospital borrado"
                }
            }.addOnFailureListener { e ->
                // Error al acceder a la base de datos para obtener las ambulancias asociadas al hospital borrado
                hospMessage.value = "Error al acceder a la base de datos para obtener las ambulancias asociadas al hospital borrado"
            }
        }
    }

    /**
     * Función para borrar un hospital en la base de datos
     */
    fun deleteHosp(id: String, onSuccess: () -> Unit) {
        // Buscamos en la base de datos un hospital con el id recibido
        val hospRef = firestore.collection("Hospitals").whereEqualTo("id", id)
        hospRef.get().addOnSuccessListener { querySnapshot ->
            // Si la consulta devuelve una respuesta positiva
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.firstOrNull()
                document?.let { doc ->
                    // Borramos el hospital de la base de datos
                    doc.reference.delete()
                        .addOnSuccessListener {
                            hospMessage.value = "Hospital borrado correctamente"
                            onSuccess()
                        }
                        .addOnFailureListener {
                            hospMessage.value = "No se pudo borrar el hospital"
                        }
                }
            } else {
                hospMessage.value = "El hospital con ID indicado no existe en la base de datos"
            }
        }.addOnFailureListener {
            hospMessage.value = "Error al acceder a la base de datos"
        }

    }

    /**
     * Función para cambiar el valor de idHosp
     */
    fun setIdHosp(text: String) {
        idHosp.value = text
    }

    /**
     * Función para cambiar el valor de name
     */
    fun setName(text: String) {
        name.value = text
    }

    /**
     * Función para cambiar el valor de county
     */
    fun setCounty(text: String) {
        county.value = text
    }

    /**
     * Función para cambiar el valor de county
     */
    fun setCity(text: String) {
        city.value = text
    }

    /**
     * Función para cambiar el valor de address
     */
    fun setAddress(text: String) {
        address.value = text
    }

    /**
     * Resetea todos los valores
     */
    fun resetFields() {
        idHosp.value = ""
        name.value = ""
        county.value = ""
        city.value = ""
        address.value = ""
        hospMessage.value = ""
    }

    /**
     * Función para asignar los valores de un Hospital recibido
     */
    fun asignHospFields(hospital: Hospital, onSuccess: () -> Unit) {
        idHosp.value = hospital.id
        name.value = hospital.name
        county.value = hospital.county
        city.value = hospital.city
        address.value = hospital.address
        onSuccess()
    }
}