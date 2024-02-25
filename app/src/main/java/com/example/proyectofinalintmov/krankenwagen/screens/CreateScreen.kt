package com.example.proyectofinalintmov.krankenwagen.screens

import android.graphics.Paint.Align
import android.text.Layout
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel

@Composable
fun CreateAmbulance(
    ambulancesViewModel: AmbulancesViewModel,
    viewModel: KrankenwagenViewModel
) {
    val idAmb by ambulancesViewModel.idAmb.collectAsState()
    val plate by ambulancesViewModel.plate.collectAsState()
    val isFree by ambulancesViewModel.isFree.collectAsState()
    val type by ambulancesViewModel.type.collectAsState()
    val hosp by ambulancesViewModel.hosp.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    val message by viewModel.message.collectAsState()

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
                    Button(onClick = {
                        // crea la ambulancia con los datos recibidos
                        ambulancesViewModel.saveAmbulance()
                    }) {
                        Text("Crear")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = {
                        // Borra todos los campos
                        ambulancesViewModel.resetFields()

                    }) {
                        Text("Borrar Todo")
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(onClick = {
                        // Cierra el diálogo
                        ambulancesViewModel.resetFields()
                        viewModel.acCreateAmb()
                    }) {
                        Text("Cerrar")
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 8.dp)
                ){
                    Text(text = message,
                        color = Color.Red)
                }
            }
        }
    }
}