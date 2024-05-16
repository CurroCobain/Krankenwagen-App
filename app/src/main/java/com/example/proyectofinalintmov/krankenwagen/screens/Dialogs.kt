package com.example.proyectofinalintmov.krankenwagen.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel

/**
 * Composable que muestra un diálogo de sesión de usuario.
 */
@SuppressLint("RememberReturnType", "StateFlowValueCalledInComposition")
@Composable
fun DialogSesion(
    viewModel: KrankenwagenViewModel,
    sesionViewModel: SesionViewModel
) {
    // Variables para gestionar el registro y el inicio de sesión
    val nombreDoc by sesionViewModel.nombreDoc.collectAsState() // Nombre del doctor
    val mailDoc by sesionViewModel.nuevoMail.collectAsState() // Email del doctor
    val passDoc by sesionViewModel.nuevoPass.collectAsState() // Contraseña del doctor
    // Variable que gestiona el cambio entre registro e inicio de sesión
    val initOrReg by sesionViewModel.initOrReg.collectAsState()
    val context = LocalContext.current

    // Variable que almacena la respuesta del sistema
    val message by sesionViewModel.sesionMessage.collectAsState()
    Column(
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
    {   // Tarjeta principal que alberga las dos variantes registro e inicio de sesión
        // si la sesión no se ha iniciado aparece la opción de registrarse
        if (!initOrReg) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.bannersup),
                    contentDescription = "banner",
                    modifier = Modifier.fillMaxWidth()
                        .height(40.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxSize()
                        .background(color = Color(74, 121, 66)),
                    verticalArrangement = Arrangement.Center
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
                            label = { Text(text = "Nombre", fontSize = 18.sp) },
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
                            label = { Text(text = "Mail", fontSize = 18.sp) },
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
                            label = { Text(text = "Password", fontSize = 18.sp) },
                            modifier = Modifier.border(width = 2.dp, color = Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    // --------------------------- Botón confirmar ---------------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                        Button(
                            onClick = {
                                // Lanzamos createUSer para crear el usuario, si finaliza correctamente se indica mediante un Toast
                                sesionViewModel.createUser {
                                    Toast.makeText(
                                        context,
                                        "Usuario registrado correctamente",
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        )
                        {
                            Text(
                                text = stringResource(R.string.confirmar),
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Spacer(modifier = Modifier.padding(10.dp))
                        // -------------------------- Botón volver ---------------------------------
                        Button(
                            onClick = {
                                // Cierra el diálogo y resetea el valor del mensaje del sistema
                                sesionViewModel.setMessage("")
                            },
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        )
                        {
                            Text(
                                text = "Borrar datos",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    // -------------------------------- Ir a inicio de sesión ----------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Text(
                            text = "Ya está registrado?",
                            fontSize = 18.sp,
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {// ---------------------- Botón iniciar sesion ------------------------------
                        Button(
                            onClick = {
                                // Cambia al modo inicio de sesión para usuarios ya registrados
                                sesionViewModel.cambiaInit()
                                sesionViewModel.setMessage("")
                            },
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        )
                        {
                            Text(
                                text = "Iniciar sesión",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Text(
                            // Mensaje del sistema
                            text = message, fontSize = 18.sp,
                            color = Color.LightGray,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        } else { // Si el usuario ya está registrado puede acceder a inicio de sesión
            Box(modifier = Modifier.fillMaxSize())
            {
                Image(
                    painter = painterResource(id = R.drawable.bannersup),
                    contentDescription = "banner",
                    modifier = Modifier.fillMaxWidth()
                        .height(40.dp)
                )
                Column(
                    modifier = Modifier
                        .padding(top = 40.dp)
                        .fillMaxSize()
                        .background(color = Color(74, 121, 66)),
                    verticalArrangement = Arrangement.Center
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
                            label = { Text(text = "Mail", fontSize = 18.sp) },
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
                            label = { Text(text = "Password", fontSize = 18.sp) },
                            modifier = Modifier.border(width = 2.dp, color = Color.Black)
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    // --------------------- Botón confirmar --------------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Button(
                            onClick = {
                                // Se lanza sesionÍnit y si finaliza correctamente se indica mediante un Toast
                                sesionViewModel.sesionInit {
                                    sesionViewModel.getUser()
                                    Toast.makeText(
                                        context,
                                        "Sesión iniciada correctamente",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                    sesionViewModel.setMessage("")
                                }
                            },
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        )
                        {
                            Text(
                                text = stringResource(R.string.confirmar),
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                        Spacer(modifier = Modifier.padding(20.dp))
                        // ------------------------ Botón volver a registro ------------------------
                        Button(
                            onClick = {
                                // Cambia al modo de registro de usuarios
                                sesionViewModel.cambiaInit()
                                sesionViewModel.setMessage("")
                            },
                            colors = ButtonDefaults.buttonColors(Color.LightGray)
                        )
                        {
                            Text(
                                text = "Volver a registro",
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.ExtraBold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.padding(10.dp))
                    // Mensaje que aparece si hay error al iniciar sesión --------------------------
                    Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                    {
                        Text(
                            // Mensaje del sistema
                            text = message,
                            fontSize = 18.sp,
                            color = Color.Red,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }
            }
        }
    }
}

