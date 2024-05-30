package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.DropdownMenuItem
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Switch
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.proyectofinalintmov.krankenwagen.data.AmbulanceTypes
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.UrgenciesViewModel


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
                        textStyle = TextStyle(fontSize = 25.sp),
                        modifier = Modifier
                            .padding(8.dp)
                            .sizeIn(minWidth = 300.dp, minHeight = 50.dp),
                        placeholder = { Text("Identificador", fontSize = 25.sp) }
                    )
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Campo de edición para la matrícula
                    TextField(
                        value = plate,
                        onValueChange = { newValue ->
                            ambulancesViewModel.setPlate(newValue)
                        },
                        textStyle = TextStyle(fontSize = 25.sp),
                        placeholder = { Text("Matrícula", fontSize = 25.sp) },
                        modifier = Modifier
                            .padding(8.dp)
                            .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                    )
                }

                // Campo de edición para la disponibilidad
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Disponible", fontSize = 25.sp)
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
                    Text("Tipo de Ambulancia   ", fontSize = 25.sp)
                    Column(modifier = Modifier.clickable(onClick = { expanded = true })) {
                        Text(
                            text = type.name, // Mostrar el tipo de ambulancia actual
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.CenterHorizontally),
                            fontSize = 25.sp
                        )
                        Spacer(modifier = Modifier.padding(start = 8.dp))
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            modifier = Modifier.width(IntrinsicSize.Max)
                        ) {
                            AmbulanceTypes.entries.forEach { type ->
                                DropdownMenuItem(onClick = {
                                    ambulancesViewModel.setType(type)
                                    expanded = false
                                }) {
                                    Text(text = type.name, fontSize = 25.sp)
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
                    textStyle = TextStyle(fontSize = 25.sp),
                    placeholder = { Text("Hospital de referencia", fontSize = 25.sp) },
                    modifier = Modifier
                        .padding(8.dp)
                        .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                )

                // Botones
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    // Botón crear ambulancia
                    Button(
                        onClick = {
                            // crea la ambulancia con los datos recibidos
                            ambulancesViewModel.saveAmbulance() {}
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 40.dp)
                    ) {
                        Text("Crear", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón borrar todos los campos
                    Button(
                        onClick = {
                            // Borra todos los campos
                            ambulancesViewModel.resetFields()
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 40.dp)
                    ) {
                        Text("Limpiar datos", fontSize = 20.sp)
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón cerrar el diálogo
                    Button(
                        onClick = {
                            // Cierra el diálogo
                            ambulancesViewModel.resetFields()
                            viewModel.acCreateAmb()
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 40.dp)
                    ) {
                        Text("Cerrar", fontSize = 20.sp)
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
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    TextField(
                        value = id,
                        onValueChange = { newValue ->
                            hospitalViewModel.setIdHosp(newValue)
                        },
                        textStyle = TextStyle(fontSize = 25.sp),
                        placeholder = { Text("Identificador", fontSize = 25.sp) },
                        modifier = Modifier
                            .padding(8.dp)
                            .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                    )
                }
                // Campo de edición para el nombre
                TextField(
                    value = name,
                    onValueChange = { newValue ->
                        hospitalViewModel.setName(newValue)
                    },
                    textStyle = TextStyle(fontSize = 25.sp),
                    placeholder = { Text("Nombre", fontSize = 25.sp) },
                    modifier = Modifier
                        .padding(8.dp)
                        .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                )

                // Campo de edición para la provincia
                TextField(
                    value = county,
                    onValueChange = { newValue ->
                        hospitalViewModel.setCounty(newValue)
                    },
                    textStyle = TextStyle(fontSize = 25.sp),
                    placeholder = { Text("Provincia", fontSize = 25.sp) },
                    modifier = Modifier
                        .padding(8.dp)
                        .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                )

                // Campo de edición para la ciudad
                TextField(
                    value = city,
                    onValueChange = { newValue ->
                        hospitalViewModel.setCity(newValue)
                    },
                    textStyle = TextStyle(fontSize = 25.sp),
                    placeholder = { Text("Ciudad", fontSize = 25.sp) },
                    modifier = Modifier
                        .padding(8.dp)
                        .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                )

                // Campo de edición para la dirección
                TextField(
                    value = address,
                    onValueChange = { newValue ->
                        hospitalViewModel.setAddress(newValue)
                    },
                    textStyle = TextStyle(fontSize = 25.sp),
                    placeholder = { Text("Dirección", fontSize = 25.sp) },
                    modifier = Modifier
                        .padding(8.dp)
                        .sizeIn(minWidth = 300.dp, minHeight = 50.dp)
                )

                // Botones
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.padding(vertical = 16.dp)
                ) {
                    // Botón crear hospital
                    Button(
                        onClick = {
                            // Guarda el hospital con los datos ingresados
                            hospitalViewModel.saveHospital() {}
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    ) {
                        Text(
                            "Crear", fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón borrar campos
                    Button(
                        onClick = {
                            // Borra todos los campos
                            hospitalViewModel.resetFields()

                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    ) {
                        Text(
                            "Limpiar datos", fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    // Botón volver
                    Button(
                        onClick = {
                            // Cierra el diálogo
                            viewModel.acCreateHosp()
                            hospitalViewModel.resetFields()
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    ) {
                        Text(
                            "Cerrar", fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
                // Mensaje de respuesta del sistema
                Row(horizontalArrangement = Arrangement.Center) {
                    Text(text = message)
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CreateUrgScreen(
    context: Context,
    viewModel: KrankenwagenViewModel,
    urgenciesViewModel: UrgenciesViewModel
) {
    val name by urgenciesViewModel.name.collectAsState()
    val doc by urgenciesViewModel.doc.collectAsState()
    val age by urgenciesViewModel.age.collectAsState()
    val priority by urgenciesViewModel.priority.collectAsState()
    val typeOfStreet by urgenciesViewModel.typeOfStreet.collectAsState()
    val streetName by urgenciesViewModel.streetName.collectAsState()
    val streetNumber by urgenciesViewModel.streetNumber.collectAsState()
    val city by urgenciesViewModel.city.collectAsState()
    val province by urgenciesViewModel.province.collectAsState()
    val postalCode by urgenciesViewModel.postalCode.collectAsState()
    val issues by urgenciesViewModel.issues.collectAsState()

    Dialog(
        onDismissRequest = { viewModel.acCreateUrg() }
    ) {
        val message by urgenciesViewModel.message.collectAsState()
        Box(
            modifier = Modifier
                .size(width = 800.dp, height = 600.dp)
                .background(Color(225, 241, 222)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = priority.toString(),
                        onValueChange = { urgenciesViewModel.setPriority(it) },
                        label = { Text("Prioridad") },
                        modifier = Modifier.weight(0.4f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = doc,
                        onValueChange = { urgenciesViewModel.setDoc(it) },
                        label = { Text("Documento") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = name,
                        onValueChange = { urgenciesViewModel.setName(it) },
                        label = { Text("Nombre") },
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = age.toString(),
                        onValueChange = { urgenciesViewModel.setAge(it) },
                        label = { Text("Edad") },
                        modifier = Modifier.weight(0.2f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }
                Spacer(modifier = Modifier.height(16.dp))
                // Campos de la dirección divididos
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = typeOfStreet,
                        onValueChange = { urgenciesViewModel.setTypeOfStreet(it) },
                        label = { Text("Tipo de vía") },
                        modifier = Modifier.weight(0.4f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = streetName,
                        onValueChange = { urgenciesViewModel.setStreetName(it) },
                        label = { Text("Nombre de la vía") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = streetNumber,
                        onValueChange = { urgenciesViewModel.setStreetNumber(it) },
                        label = { Text("Número") },
                        modifier = Modifier.weight(0.3f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = city,
                        onValueChange = { urgenciesViewModel.setCity(it) },
                        label = { Text("Ciudad") },
                        modifier = Modifier.weight(0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TextField(
                        value = province,
                        onValueChange = { urgenciesViewModel.setProvince(it) },
                        label = { Text("Provincia") },
                        modifier = Modifier.weight(0.5f)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    TextField(
                        value = postalCode,
                        onValueChange = { urgenciesViewModel.setPostalCode(it) },
                        label = { Text("Código postal") },
                        modifier = Modifier.weight(0.5f)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextField(
                    value = issues,
                    onValueChange = { urgenciesViewModel.setIssues(it) },
                    label = { Text("Problemas") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            urgenciesViewModel.setAddress()
                            urgenciesViewModel.createUrg(
                                onSuccess = {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                },
                                onFailure = {
                                    Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                                }
                            )
                        },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    ) {
                        Text(
                            "Crear",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Button(
                        onClick = { urgenciesViewModel.deleteMiUrgencia() },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)),
                        modifier = Modifier.sizeIn(minWidth = 150.dp, minHeight = 50.dp)
                    ) {
                        Text(
                            "Borrar todo",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

