package com.example.proyectofinalintmov.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.viewModels.KrankenwagenViewModel


/**
 * Composable que muestra un diálogo de menú.
 * Recibe el siguiente parámetro
 * @param viewModel El ViewModel asociado al diálogo de menú.
 */
@Composable
fun DialogMenu(viewModel: KrankenwagenViewModel) {
    Dialog(
        onDismissRequest = { viewModel.closeMenu() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .fillMaxHeight(0.3f),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.Center
            )
            {
                Text(text = stringResource(R.string.elija_el_idioma_de_la_app))
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                horizontalArrangement = Arrangement.SpaceEvenly
            )
            {
                Button(
                    onClick = { viewModel.closeApp() },
                    colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                ) {
                    Text(text = "Salir")
                }
                Button(
                    onClick = { viewModel.closeMenu() },
                    colors = ButtonDefaults.buttonColors(Color(74, 121, 66))
                ) {
                    Text(text = "Volver")
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
@SuppressLint("RememberReturnType")
@Composable
fun DialogSesion(
    viewModel: KrankenwagenViewModel
) {
    val nombreDoc = remember { mutableStateOf("")}
    var mailDoc = remember { mutableStateOf("")}
    var passDoc = remember { mutableStateOf("")}
    Dialog(
        onDismissRequest = { viewModel.closeSesion() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.5f),
            shape = RoundedCornerShape(10.dp),
        ) {
            Column(modifier = Modifier.background(color = Color(225,241,222))
                .fillMaxSize())
            {
                Spacer(modifier = Modifier.padding(20.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                {
                    TextField(value = nombreDoc.value,
                        onValueChange = {
                                newValue ->
                            nombreDoc.value = newValue
                        },
                        label = { Text(text = "Nombre")},
                        modifier = Modifier.border(width = 2.dp,color = Color.Black ))
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                {
                    TextField(value = mailDoc.value,
                        onValueChange = {
                                newValue ->
                            mailDoc.value = newValue
                        },
                        label = { Text(text = "Mail")},
                        modifier = Modifier.border(width = 2.dp,color = Color.Black ))
                }
                Spacer(modifier = Modifier.padding(10.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally))
                {
                    TextField(value = passDoc.value,
                        onValueChange = {
                                newValue ->
                            passDoc.value = newValue
                        },
                        label = { Text(text = "Password")},
                        modifier = Modifier.border(width = 2.dp,color = Color.Black ))
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Row(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    Button(onClick = {
                        viewModel.cambiaNombre(nombreDoc.value)
                        viewModel.cambiaMail(mailDoc.value)
                        viewModel.cambiaPass(passDoc.value)
                        viewModel.closeSesion() },
                        colors = ButtonDefaults.buttonColors(Color(74, 121, 66)))
                    {
                        Text(text = stringResource(R.string.confirmar))
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                    Button(onClick = { viewModel.closeSesion() },
                        colors = ButtonDefaults.buttonColors(Color(233, 85, 85)))
                    {
                        Text(text = "Volver")
                    }
                }
            }
        }
    }
}
