package com.example.proyectofinalintmov

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalintmov.model.Routes
import com.example.proyectofinalintmov.screens.Ambulances
import com.example.proyectofinalintmov.screens.Clinics
import com.example.proyectofinalintmov.screens.Hospitals
import com.example.proyectofinalintmov.screens.WelcomePage
import com.example.proyectofinalintmov.ui.theme.ProyectoFinalIntMovTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoFinalIntMovTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Routes.PantallaWelcome.route
                    ) {
                        composable(Routes.PantallaWelcome.route) { WelcomePage(navController) }
                        composable(Routes.PantallaAmbulances.route) { Ambulances(navController) }
                        composable(Routes.PantallaHospitals.route) { Hospitals(navController) }
                        composable(Routes.PantallaDocs.route) { Clinics(navController) }
                    }
                }
            }
        }
    }
}
