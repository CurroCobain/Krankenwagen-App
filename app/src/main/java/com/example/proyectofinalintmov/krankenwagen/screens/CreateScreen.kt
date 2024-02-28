package com.example.proyectofinalintmov.krankenwagen.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel


/**
 * Composable para la creación de ambulancias
 */
@Composable
fun CreateAmbulance(
    ambulancesViewModel: AmbulancesViewModel,
    viewModel: KrankenwagenViewModel
) {
    // Variables para la creación de ambulancias
    val idAmb by ambulancesViewModel.idAmb.collectAsState() // Id de la ambulancia
    val plate by ambulancesViewModel.plate.collectAsState() // Matrícula de la ambulancia
    val isFree by ambulancesViewModel.isFree.collectAsState() // Estado de la ambulancia
    val type by ambulancesViewModel.type.collectAsState() // Tipo de ambulancia
    val hosp by ambulancesViewModel.hosp.collectAsState() // Hospital de referencia de la ambulancia
    // Variable que se usa para controlar el DropdownMenu
    var expanded by remember { mutableStateOf(false) }
    // Variable que almacena el mensaje del sistema
    val message by ambulancesViewModel.ambulanceMessage.collectAsState()

    // Diálogo de creación de ambulancia
    Dialog(
        onDismissRequest = { viewModel.acCreateAmb() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(225, 241, 222)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Campo de edición para el id
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = idAmb,
                        onValueChange = { newValue ->
                            ambulancesViewModel.setIdAmb(newValue)
                        },
                        label = { Text("Identificador") },
                        modifier = Modifier.padding(8.dp)
                    )
                }
                // Campo de edición para la matrícula
                TextField(
                    value = plate,
                    onValueChange = { newValue ->
                        ambulancesViewModel.setPlate(newValue)
                    },
                    label = { Text("Matrícula") },
                    modifier = Modifier.padding(8.dp)
                )

                // Campo de edición para la disponibilidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Disponible")
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isFree,
                        onCheckedChange = { newValue ->
                            ambulancesViewModel.setIsFree(newValue)
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }

                // Campo de edición para el tipo de ambulancia
                Row {
                    Text("Tipo de Ambulancia   ")
                    Column(modifier = Modifier.clickable(onClick = { expanded = true })) {
                        Text(
                            text = type.name, // Mostrar el tipo de ambulancia actual
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.width(IntrinsicSize.Max)
                        ) {
                            AmbulanceTypes.values().forEach { type ->
                                DropdownMenuItem(onClick = {
                                    ambulancesViewModel.setType(type)
                                    expanded = false
                                }) {
                                    Text(text = type.name)
                                }
                            }
                        }
                    }

                }
                // Campo de edición para el hospital de referencia
                TextField(
                    value = hosp,
                    onValueChange = { newValue ->
                        ambulancesViewModel.setHosp(newValue)
                    },
                    label = { Text("Hospital de referencia") },
                    modifier = Modifier.padding(8.dp)
                )

                // Botones
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    // Botón crear ambulancia
                    Button(onClick = {
                        // crea la ambulancia con los datos recibidos
                        ambulancesViewModel.saveAmbulance() {}
                    }) {
                        Text("Crear")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón borrar todos los campos
                    Button(onClick = {
                        // Borra todos los campos
                        ambulancesViewModel.resetFields()
                    }) {
                        Text("Borrar Todo")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón cerrar el diálogo
                    Button(onClick = {
                        // Cierra el diálogo
                        ambulancesViewModel.resetFields()
                        viewModel.acCreateAmb()
                    }) {
                        Text("Cerrar")
                    }
                }
                // Mensaje del sistema
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(text = message)
                }
            }
        }
    }
}


/**
 * Composable para la creación de hospitales
 */
@Composable
fun CreateHospital(
    hospitalViewModel: HospitalViewModel,
    viewModel: KrankenwagenViewModel
) {
    // Variables para la creación de hospitales
    val id by hospitalViewModel.idHosp.collectAsState() // Id del hospital
    val name by hospitalViewModel.name.collectAsState() // Nombre del hospital
    val county by hospitalViewModel.county.collectAsState() // Provincia del hospital
    val city by hospitalViewModel.city.collectAsState() // Ciudad del hospital
    val address by hospitalViewModel.address.collectAsState() // Dirección del hospital
    // Variable que almacena el mensaje del sistema
    val message by hospitalViewModel.hospMessage.collectAsState()

    // Diálogo de creación de hospital
    Dialog(
        onDismissRequest = { viewModel.acCreateHosp() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(225, 241, 222)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Campo de edición para el id
                Row(verticalAlignment = Alignment.CenterVertically) {
                    TextField(
                        value = id,
                        onValueChange = { newValue ->
                            hospitalViewModel.setIdHosp(newValue)
                        },
                        label = { Text("Identificador") },
                        modifier = Modifier.padding(8.dp)
                    )
                }
                // Campo de edición para el nombre
                TextField(
                    value = name,
                    onValueChange = { newValue ->
                        hospitalViewModel.setName(newValue)
                    },
                    label = { Text("Nombre") },
                    modifier = Modifier.padding(8.dp)
                )

                // Campo de edición para la provincia
                TextField(
                    value = county,
                    onValueChange = { newValue ->
                        hospitalViewModel.setCounty(newValue)
                    },
                    label = { Text("Provincia") },
                    modifier = Modifier.padding(8.dp)
                )

                // Campo de edición para la ciudad
                TextField(
                    value = city,
                    onValueChange = { newValue ->
                        hospitalViewModel.setCity(newValue)
                    },
                    label = { Text("Ciudad") },
                    modifier = Modifier.padding(8.dp)
                )

                // Campo de edición para la dirección
                TextField(
                    value = address,
                    onValueChange = { newValue ->
                        hospitalViewModel.setAddress(newValue)
                    },
                    label = { Text("Dirección") },
                    modifier = Modifier.padding(8.dp)
                )

                // Botones
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    // Botón crear hospital
                    Button(onClick = {
                        // Guarda el hospital con los datos ingresados
                        hospitalViewModel.saveHospital() {}
                    }) {
                        Text("Crear")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón borrar campos
                    Button(onClick = {
                        // Borra todos los campos
                        hospitalViewModel.resetFields()

                    }) {
                        Text("Limpiar")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón volver
                    Button(onClick = {
                        // Cierra el diálogo
                        viewModel.acCreateHosp()
                        hospitalViewModel.resetFields()
                    }) {
                        Text("Cerrar")
                    }
                }
                // Mensaje de respuesta del sistema
                Row (horizontalArrangement = Arrangement.Center){
                    Text(text = message)
                }
            }
        }
    }
}

