package com.example.proyectofinalintmov.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectofinalintmov.model.Routes
import com.example.proyectofinalintmov.screens.Ambulances
import com.example.proyectofinalintmov.screens.Clinics
import com.example.proyectofinalintmov.screens.Hospitals
import com.example.proyectofinalintmov.screens.WelcomePage
import com.example.proyectofinalintmov.viewModels.WelcomePageViewModel

@Composable
fun NavManager(viewModel: WelcomePageViewModel) {
    val navController = rememberNavController()
    val showMenu by viewModel.showMenu.collectAsState()
    val userRegistered by viewModel.userRegistererd.collectAsState()
    NavHost(
    navController = navController,
    startDestination = Routes.PantallaWelcome.route
    ) {
        composable(Routes.PantallaWelcome.route) { WelcomePage(navController, viewModel, showMenu,userRegistered) }
        composable(Routes.PantallaAmbulances.route) { Ambulances(navController, viewModel, showMenu, userRegistered) }
        composable(Routes.PantallaHospitals.route) { Hospitals(navController, viewModel, showMenu,userRegistered) }
        composable(Routes.PantallaDocs.route) { Clinics(navController, viewModel, showMenu,userRegistered) }
    }
}