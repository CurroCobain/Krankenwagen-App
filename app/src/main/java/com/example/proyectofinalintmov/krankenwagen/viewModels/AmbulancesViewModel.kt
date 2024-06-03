package com.example.proyectofinalintmov.krankenwagen.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.toObject

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel para la gestión de las Ambulancias
 * @property idAmb se usa para almacenar el id de la ambulancia actual
 * @property plate se usa para almacenar la matrícula de la ambulancia actual
 * @property isFree se usa para almacenar el estado de la ambulancia actual
 * @property type se usa para almacenar el tipo de la ambulancia actual
 * @property hosp se usa para almacenar el hospital de referencia de la ambulancia actual
 * @property ambulanceMessage se usa para mostrar la respuesta del sistema
 */
class AmbulancesViewModel : ViewModel() {
    // Inicialización del objeto de Firestore para acceder a la base de datos Firestore de Firebase
    private val firestore = Firebase.firestore

    // Almacena el id de la ambulancia actual
    var idAmb = MutableStateFlow("")
        private set

    // Almacena la matrícula de la ambulancia actual
    var plate = MutableStateFlow("")
        private set

    // Almacena el estado de la ambulancia actual
    var isFree = MutableStateFlow(false)
        private set

    // Almacena el tipo de la ambulancia actual
    var type = MutableStateFlow<AmbulanceTypes>(AmbulanceTypes.doctor)
        private set

    // Almacena el hospital de referencia de la ambulancia actual
    var hosp = MutableStateFlow("")
        private set

    // Almacena el mensaje de respuesta del sistema
    var ambulanceMessage = MutableStateFlow("")
        private set


    /**
     * Función para guardar una ambulancia en la base de datos
     */
    fun saveAmbulance(onSuccess: () -> Unit) {
        if (idAmb.value.isEmpty() || plate.value.isEmpty() || hosp.value.isEmpty()) {
            ambulanceMessage.value = "Debe rellenar todos los campos"
        } else {
            viewModelScope.launch {
                // Creamos un objeto de tipo Ambulance con los valores actuales
                val myAmbulance = Ambulance(
                    idAmb.value,
                    plate.value,
                    isFree.value,
                    type.value,
                    hosp.value,
                    mutableMapOf("latitude" to 0.0, "longitude" to 0.0)
                )
                // Verificamos si ya existe un hospital con el mismo ID que hosp.value
                firestore.collection("Hospitals")
                    .whereEqualTo(
                        "id",
                        myAmbulance.hospital
                    ) // Buscamos el documento con el mismo ID que hosp.value
                    .get()
                    .addOnSuccessListener { querySnapshot ->
                        if (!querySnapshot.isEmpty()) {
                            // Si existe un hospital con el mismo ID, continuamos verificando la ambulancia
                            firestore.collection("Ambulances")
                                .whereEqualTo(
                                    "id",
                                    myAmbulance.id
                                ) // Buscamos documentos con el mismo ID
                                .get()
                                .addOnSuccessListener { querySnapshot ->
                                    if (!querySnapshot.isEmpty) {
                                        // Si existe una ambulancia con el mismo ID, mostramos un mensaje de error
                                        ambulanceMessage.value =
                                            "Ya existe una ambulancia con este ID en la base de datos"
                                    } else {
                                        // Si no existe, verificamos si ya existe una ambulancia con la misma matrícula
                                        firestore.collection("Ambulances")
                                            .whereEqualTo(
                                                "plate",
                                                myAmbulance.plate
                                            ) // Buscamos documentos con la misma matrícula
                                            .get()
                                            .addOnSuccessListener { querySnapshot ->
                                                if (!querySnapshot.isEmpty) {
                                                    // Si existe una ambulancia con la misma matrícula, mostramos un mensaje de error
                                                    ambulanceMessage.value =
                                                        "Ya existe una ambulancia con este ID en la base de datos"
                                                } else {
                                                    // Si no existe, agregamos la nueva ambulancia a la base de datos
                                                    firestore.collection("Ambulances")
                                                        .add(myAmbulance)
                                                        .addOnSuccessListener {
                                                            // Si se completa correctamente, modificamos el mensaje del sistema
                                                            ambulanceMessage.value =
                                                                "Se actualizó la ambulancia en la base de datos"
                                                            onSuccess()
                                                        }
                                                        // Si hay fallo en el proceso lo indicamos mediante el mensaje del sistema
                                                        .addOnFailureListener {
                                                            ambulanceMessage.value =
                                                                "No se pudo guardar la ambulancia, revise los datos"
                                                        }
                                                }
                                            }
                                            .addOnFailureListener {
                                                // Manejar errores de lectura
                                                ambulanceMessage.value =
                                                    "Error al verificar la existencia de la ambulancia en la base de datos"
                                            }
                                    }
                                }
                                .addOnFailureListener {
                                    // Manejar errores de lectura
                                    ambulanceMessage.value =
                                        "Error al verificar la existencia de la ambulancia en la base de datos"
                                }
                        } else {
                            // Si no existe un hospital con el mismo ID, mostramos un mensaje de error
                            ambulanceMessage.value =
                                "No existe un hospital con este ID en la base de datos"
                        }
                    }
                    .addOnFailureListener {
                        // Manejar errores de lectura
                        ambulanceMessage.value =
                            "Error al verificar la existencia de la ambulancia en la base de datos"
                    }
            }
        }

    }

    /**
     * Función para actualizar una ambulancia en la base de datos
     */
    fun updateAmbulance(onSuccess: () -> Unit) {
        // Consultamos la base de datos para encontrar la ambulancia con el campo "id" igual a idAmb.value
        firestore.collection("Ambulances")
            .whereEqualTo("id", idAmb.value)
            .get()
            .addOnSuccessListener { querySnapshot ->
                // Si la consulta devuelve resultados
                if (!querySnapshot.isEmpty) {
                    // Iteramos sobre los documentos encontrados (en caso de que haya más de uno)
                    querySnapshot.documents.forEach { doc ->
                        // Convertimos el documento a un objeto de tipo Ambulance
                        val ambulance = doc.toObject<Ambulance>()
                        ambulance?.let {
                            // Actualizamos los valores del objeto
                            val updatedAmbulance = Ambulance(
                                idAmb.value,
                                plate.value,
                                isFree.value,
                                type.value,
                                hosp.value,
                                mutableMapOf("latitude" to 0.0, "longitude" to 0.0)
                            )
                            // Guardamos los nuevos valores en el documento
                            doc.reference.set(updatedAmbulance)
                                .addOnSuccessListener {
                                    // La actualización fue exitosa
                                    ambulanceMessage.value =
                                        "Se actualizó la ambulancia en la base de datos"
                                    onSuccess()
                                }
                                .addOnFailureListener { e ->
                                    // Ocurrió un error durante la actualización
                                    ambulanceMessage.value =
                                        "No se pudo guardar la ambulancia, revise los datos"
                                }
                        }
                    }
                } else {
                    // No se encontró ninguna ambulancia con el campo "id" igual a idAmb.value
                    ambulanceMessage.value =
                        "La ambulancia con el ID indicado no existe en la base de datos"
                }
            }
            .addOnFailureListener { e ->
                // Ocurrió un error al realizar la consulta
                ambulanceMessage.value = "Error al acceder a la base de datos"
            }

    }

    /**
     * Función para borrar una ambulancia en la base de datos
     */
    fun deleteAmbulance(onSuccess: () -> Unit) {
        // Buscamos en la base de datos una ambulancia con el id recibido
        val ambulanceRef = firestore.collection("Ambulances").whereEqualTo("id", idAmb.value)
        ambulanceRef.get().addOnSuccessListener { querySnapshot ->
            // Si la consulta devuelve una respuesta positiva
            if (!querySnapshot.isEmpty) {
                val document = querySnapshot.documents.firstOrNull()
                document?.let { doc ->
                    // Borramos la ambulancia de la base de datos
                    doc.reference.delete()
                        .addOnSuccessListener {
                            ambulanceMessage.value = "Se eliminó la ambulancia de la base de datos"
                            onSuccess()
                        }
                        .addOnFailureListener {
                            ambulanceMessage.value =
                                "No se pudo eliminar la ambulancia, intente nuevamente"
                        }
                }
            } else {
                ambulanceMessage.value =
                    "La ambulancia con el ID indicado no existe en la base de datos"
            }
        }.addOnFailureListener {
            ambulanceMessage.value = "Error al acceder a la base de datos"
        }

    }

    /**
     * Función para cambiar el valor de idAmb
     */
    fun setIdAmb(newId: String) {
        idAmb.value = newId
    }

    /**
     * Función para cambiar el valor de plate
     */
    fun setPlate(newPlate: String) {
        plate.value = newPlate
    }

    /**
     * Función para cambiar el valor de isFree
     */
    fun setIsFree(newValue: Boolean) {
        isFree.value = newValue
    }

    /**
     * Función para cambiar el valor de type
     */
    fun setType(newType: AmbulanceTypes) {
        type.value = newType
    }

    /**
     * Función para cambiar el valor de hosp
     */
    fun setHosp(newHosp: String) {
        hosp.value = newHosp
    }

    /**
     * Reestablece todos los valores
     */
    fun resetFields() {
        idAmb.value = ""
        plate.value = ""
        isFree.value = false
        type.value = AmbulanceTypes.doctor
        hosp.value = ""
        ambulanceMessage.value = ""
    }

    /**
     * Función para asignar los valores de una ambulancia recibida
     */
    fun asignAmbFields(ambulance: Ambulance) {
        idAmb.value = ambulance.id
        plate.value = ambulance.plate
        isFree.value = ambulance.free
        type.value = ambulance.types
        hosp.value = ambulance.hospital
    }
}
