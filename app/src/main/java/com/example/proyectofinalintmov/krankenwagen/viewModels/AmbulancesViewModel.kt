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

class AmbulancesViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val krankenwagenViewModel = KrankenwagenViewModel()

    var idAmb = MutableStateFlow("")
    var plate = MutableStateFlow("")
        private set

    var isFree = MutableStateFlow(false)

    var type = MutableStateFlow<AmbulanceTypes>(AmbulanceTypes.doctor)

    var hosp = MutableStateFlow("")


    fun saveAmbulance() {
        viewModelScope.launch {
            val myAmbulance = Ambulance(
                idAmb.value,
                plate.value,
                isFree.value,
                type.value,
                hosp.value
            )
            firestore.collection("Ambulances")
                .add(myAmbulance)
                .addOnSuccessListener {
                    krankenwagenViewModel.message.value = "Se guardó la ambulancia en la base de datos"
                }
                .addOnFailureListener {
                    krankenwagenViewModel.message.value = " No se pudo guardar la ambulancia, revise los datos"
                }
        }

    }

    fun updateAmbulance(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val ambulanceRef = firestore.collection("Ambulances").whereEqualTo("id", id)
            ambulanceRef.get().addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents.firstOrNull()
                    document?.let { doc ->
                        val ambulance = doc.toObject<Ambulance>()
                        ambulance?.let {
                            val updatedAmbulance = Ambulance(
                                id,
                                plate.value,
                                isFree.value,
                                type.value,
                                hosp.value
                            )
                            doc.reference.set(updatedAmbulance)
                                .addOnSuccessListener {
                                    krankenwagenViewModel.message.value = "Se actualizó la ambulancia en la base de datos"
                                    onSuccess()
                                }
                                .addOnFailureListener {
                                    krankenwagenViewModel.message.value = "No se pudo actualizar la ambulancia, revise los datos"
                                }
                        }
                    }
                } else {
                    krankenwagenViewModel.message.value = "La ambulancia con ID $id no existe en la base de datos"
                }
            }.addOnFailureListener {
                krankenwagenViewModel.message.value = "Error al acceder a la base de datos"
            }
        }
    }

    fun deleteAmbulance(id: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val ambulanceRef = firestore.collection("Ambulances").whereEqualTo("id", id)
            ambulanceRef.get().addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    val document = querySnapshot.documents.firstOrNull()
                    document?.let { doc ->
                        doc.reference.delete()
                            .addOnSuccessListener {
                                krankenwagenViewModel.message.value = "Se eliminó la ambulancia de la base de datos"
                                onSuccess()
                            }
                            .addOnFailureListener {
                                krankenwagenViewModel.message.value = "No se pudo eliminar la ambulancia, intente nuevamente"
                            }
                    }
                } else {
                    krankenwagenViewModel.message.value = "La ambulancia con ID $id no existe en la base de datos"
                }
            }.addOnFailureListener {
                krankenwagenViewModel.message.value = "Error al acceder a la base de datos"
            }
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
        krankenwagenViewModel.message.value = ""
    }

    fun asignAmbFields(ambulance: Ambulance){
        idAmb.value = ambulance.id
        plate.value = ambulance.plate
        isFree.value = ambulance.isFree
        type.value = ambulance.types
        hosp.value = ambulance.hospital
    }
}