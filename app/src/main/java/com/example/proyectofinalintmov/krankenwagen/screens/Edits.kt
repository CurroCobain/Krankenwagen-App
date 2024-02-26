package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Switch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel

@Composable
fun EditarHosp(
    viewModel: KrankenwagenViewModel,
    hospital: Hospital
) {
    Dialog(
        onDismissRequest = { viewModel.activaEditHosp() })
    {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(225, 241, 222))
            ) {
                Text(text = hospital.name)
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun EditarAmb(
    viewModel: KrankenwagenViewModel,
    ambulancesViewModel: AmbulancesViewModel
) {
    val context = LocalContext.current
    val id by ambulancesViewModel.idAmb.collectAsState()
    val plate by ambulancesViewModel.plate.collectAsState()
    val isFree by ambulancesViewModel.isFree.collectAsState()
    val type by ambulancesViewModel.type.collectAsState()
    val hosp by ambulancesViewModel.hosp.collectAsState()
    // Estado para controlar la expansión del DropdownMenu
    var expanded by remember { mutableStateOf(false) }
    val message by ambulancesViewModel.ambulanceMessage.collectAsState()

    Dialog(
        onDismissRequest = {
            viewModel.activaEditAmb()
            ambulancesViewModel.setMessage("")
        },
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
                Text(
                    text = "Matrícula: $plate",
                    fontSize = 30.sp,
                    modifier = Modifier.background(color = Color.LightGray)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de edición para el hospital de referencia
                TextField(
                    value = hosp,
                    onValueChange = { newValue ->
                        ambulancesViewModel.setHosp(newValue)
                    },
                    label = {
                        Text(
                            "Hospital de referencia",
                            fontSize = 30.sp
                        )
                    },
                    modifier = Modifier.padding(8.dp),
                    textStyle = TextStyle(fontSize = 30.sp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Campo de edición para la disponibilidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Disponible",
                        fontSize = 30.sp,
                        modifier = Modifier.background(color = Color.LightGray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Switch(
                        checked = isFree,
                        onCheckedChange = { newValue ->
                            ambulancesViewModel.setIsFree(newValue)
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row {
                    Text(
                        "Tipo de Ambulancia   ",
                        fontSize = 30.sp,
                        modifier = Modifier.background(color = Color.LightGray)
                    )
                    Column(modifier = Modifier.clickable(onClick = { expanded = true })) {
                        Text(
                            text = type.name, // Mostrar el tipo de ambulancia actual
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 30.sp
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
                                    Text(
                                        text = type.name,
                                        fontSize = 30.sp
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(30.dp))
                Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(
                        onClick = {
                            ambulancesViewModel.updateAmbulance(id) {
                                Toast.makeText(
                                    context,
                                    "Ambulancia actualizada correctamente",
                                    Toast.LENGTH_SHORT
                                )
                                viewModel.getAllAmb { viewModel.activaEditAmb() }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                    ) {
                        Text(
                            text = "Guardar",
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(
                        onClick = {
                            ambulancesViewModel.deleteAmbulance(id) {
                                Toast.makeText(
                                    context,
                                    "Ambulancia borrada correctamente",
                                    Toast.LENGTH_SHORT
                                )
                                viewModel.getAllAmb { viewModel.activaEditAmb() }
                            }
                            viewModel.activaEditAmb()
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                    ) {
                        Text(
                            text = "Borrar ambulancia",
                            fontSize = 20.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.padding(8.dp))

                Row(horizontalArrangement = Arrangement.Center) {
                    Button(
                        onClick = {
                            ambulancesViewModel.resetFields()
                            viewModel.activaEditAmb()
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                    ) {
                        Text(
                            text = "Volver",
                            fontSize = 20.sp
                        )
                    }
                }
                Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                {
                    Text(
                        text = message!!,
                        color = Color.Red
                    )
                }
            }
        }
    }
}

/**
 * Todo
 * agregar texto resultado a crear ambulances
 * al crear cargar de nuevo las ambulances de la base de datos
 * al borrar desactivar edicion
 */