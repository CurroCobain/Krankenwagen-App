package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.Text
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
import com.example.proyectofinalintmov.krankenwagen.data.Ambulance
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.data.Hospital
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
    viewModel: KrankenwagenViewModel
) {
    val myAmbulance by viewModel.actualIsFree.collectAsState()
    val ambActual by viewModel.ambActual.collectAsState()
    var expanded by remember { mutableStateOf(false) } // Estado para controlar la expansión del DropdownMenu

    Dialog(
        onDismissRequest = { viewModel.activaEditAmb() }
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(225, 241, 222)),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Placa de Ambulancia: ${ambActual.plate}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = {
                    viewModel.isFreeActualAmbul()
                },
                    colors = if (myAmbulance)
                        ButtonDefaults.buttonColors(Color(74, 121, 66))
                     else
                        ButtonDefaults.buttonColors( Color.Red)
                ) {
                    Text(text = "Disponible: $myAmbulance")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Usamos el estado "expanded" para controlar la expansión del DropdownMenu
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    AmbulanceTypes.values().forEach { type ->
                        DropdownMenuItem(onClick = {
                            ambActual.types = type
                            expanded = false // Cerrar el menú cuando se seleccione una opción
                        }) {
                            Text(text = type.name)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Tipo de Ambulancia: ${ambActual.types}",
                    // Usamos clickable para hacer clic en el texto "Placa de Ambulancia"
                    modifier = Modifier.clickable { expanded = true })

                Spacer(modifier = Modifier.height(16.dp))
                Row( horizontalArrangement = Arrangement.SpaceEvenly) {
                    Button(onClick = {
                        // Aquí puedes llamar a alguna función del ViewModel para guardar los cambios, si es necesario
                        viewModel.activaEditAmb()
                    },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                    ) {
                        Text(text = "Guardar Cambios")
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    Button(onClick = {
                        // Aquí puedes llamar a alguna función del ViewModel para guardar los cambios, si es necesario
                        viewModel.activaEditAmb()
                    },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                    ) {
                        Text(text = "volver")
                    }
                }

            }
        }
    }
}