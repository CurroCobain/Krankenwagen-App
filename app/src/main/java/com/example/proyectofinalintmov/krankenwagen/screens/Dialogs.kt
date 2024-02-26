package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState


/**
 * Composable que muestra un diálogo de menú.
 * Recibe el siguiente parámetro
 * @param viewModel El ViewModel asociado al diálogo de menú.
 */
@Composable
fun DialogMenu(viewModel: KrankenwagenViewModel, sesionViewModel: SesionViewModel) {
    Dialog(
        onDismissRequest = { viewModel.openCloseMenu() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.3f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .padding(top = 10.dp),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            )
            {// ------------------------- Texto menú  -------------------------
                Text(text = "Menú")
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f)
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.Center
            )
            {
                Button(
                    onClick = { viewModel.closeApp() },
                    colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                ) {
                    Text(text = "Salir")
                }
            }
            Spacer(modifier = Modifier.padding(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                horizontalArrangement = Arrangement.Center
            )
            {
                Button(
                    onClick = {
                        viewModel.openCloseMenu()
                        sesionViewModel.cambiaNombre("")
                        sesionViewModel.cerrarSesion()
                        viewModel.openCloseSesion()
                    },
                    colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                ) {
                    Text(text = "Cerrar sesión")
                }
            }
        }
    }
}


/**
 * Composable que muestra un diálogo de sesión de usuario.
 * Recibe el siguiente parámetro
 * @param viewModel El ViewModel asociado al diálogo de sesión de usuario.
 */
@SuppressLint("RememberReturnType", "StateFlowValueCalledInComposition")
@Composable
fun DialogSesion(
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel
) {
    val nombreDoc by sesionViewModel.nombreDoc.collectAsState()
    val mailDoc by sesionViewModel.nuevoMail.collectAsState()
    val passDoc by sesionViewModel.nuevoPass.collectAsState()
    val initOrReg by sesionViewModel.inirOrReg.collectAsState()
    val context = LocalContext.current
    val message by sesionViewModel.sesionMessage.collectAsState()
    Dialog(
        onDismissRequest = { viewModel.openCloseSesion() })
    {   // Tarjeta principal que alberga las dos variantes registro e inicio de sesión
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            shape = RoundedCornerShape(10.dp),
        ) {
            // si la sesión no se ha iniciado aparece la opción de registrarse
            if (!initOrReg) {
                Column(
                    modifier = Modifier
                        .background(color = Color(225, 241, 222))
                        .fillMaxSize()
                )
                {
                    Spacer(modifier = Modifier.padding(8.dp))
                    // ---------------------- TextField nombre ---------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        TextField(
                            value = nombreDoc,
                            onValueChange = { newValue ->
                                sesionViewModel.cambiaNombre(newValue)
                            },
                            label = { Text(text = "Nombre") },
                            modifier = Modifier.border(width = 2.dp, color = Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    // ------------------------ TextField mail -----------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        TextField(
                            value = mailDoc,
                            onValueChange = { newValue ->
                                sesionViewModel.cambiaMail(newValue)
                            },
                            label = { Text(text = "Mail") },
                            modifier = Modifier.border(width = 2.dp, color = Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.padding(8.dp))
                    // ------------------------ TextField password -----------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        TextField(
                            value = passDoc,
                            onValueChange = { newValue ->
                                sesionViewModel.cambiaPass(newValue)
                            },
                            label = { Text(text = "Password") },
                            modifier = Modifier.border(width = 2.dp, color = Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    // --------------------------- Botón confirmar ---------------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Button(
                            onClick = {
                                sesionViewModel.createUser {
                                    Toast.makeText(context, "Usuario registrado correctamente", Toast.LENGTH_LONG).show()
                                    sesionViewModel.cambiaInit()
                                    sesionViewModel.setMessage("")
                                    viewModel.openCloseSesion()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                        )
                        {
                            Text(text = stringResource(R.string.confirmar))
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        // -------------------------- Botón volver ---------------------------------
                        Button(
                            onClick = {
                                viewModel.openCloseSesion()
                                sesionViewModel.setMessage("")
                            },
                            colors = ButtonDefaults.buttonColors(Color(233, 85, 85))
                        )
                        {
                            Text(text = "Volver")
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    // -------------------------------- Ir a inicio de sesión ----------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Text(text = "Ya está registrado?")
                    }
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {// ---------------------- Botón iniciar sesion ------------------------------
                        Button(
                            onClick = {
                                sesionViewModel.cambiaInit()
                                sesionViewModel.setMessage("")
                            },
                            colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                        )
                        {
                            Text(text = "Iniciar sesión")
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
            } else { // Si el usuario ya está registrado puede acceder a inicio de sesión
                Column(
                    modifier = Modifier
                        .background(color = Color(225, 241, 222))
                        .fillMaxSize()
                )
                {
                    Spacer(modifier = Modifier.padding(40.dp))
                    // ------------------------ TextField mail -----------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        TextField(
                            value = mailDoc,
                            onValueChange = { newValue ->
                                sesionViewModel.cambiaMail(newValue)
                            },
                            label = { Text(text = "Mail") },
                            modifier = Modifier.border(width = 2.dp, color = Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    // ------------------------ TextField password -----------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        TextField(
                            value = passDoc,
                            onValueChange = { newValue ->
                                sesionViewModel.cambiaPass(newValue)
                            },
                            label = { Text(text = "Password") },
                            modifier = Modifier.border(width = 2.dp, color = Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    // --------------------- Botón confirmar --------------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Button(
                            onClick = {
                                sesionViewModel.sesionInit {
                                    sesionViewModel.getUser()
                                    viewModel.openCloseSesion()
                                    Toast.makeText(
                                        context,
                                        "Sesión iniciada correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    sesionViewModel.setMessage("")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                        )
                        {
                            Text(text = stringResource(R.string.confirmar))
                        }
                        Spacer(modifier = Modifier.padding(20.dp))
                        // ------------------------ Botón volver a registro ------------------------
                        Button(
                            onClick = {
                                sesionViewModel.cambiaInit()
                                sesionViewModel.setMessage("")
                            },
                            colors = ButtonDefaults.buttonColors(Color(233, 85, 85))
                        )
                        {
                            Text(text = "Volver a registro")
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    // Mensaje que aparece si hay error al iniciar sesión --------------------------
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
}

