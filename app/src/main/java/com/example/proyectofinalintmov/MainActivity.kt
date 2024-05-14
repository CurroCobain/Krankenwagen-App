package com.example.proyectofinalintmov

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.proyectofinalintmov.krankenwagen.navigation.NavManager
import com.example.proyectofinalintmov.krankenwagen.viewModels.AmbulancesViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.HospitalViewModel
import com.example.proyectofinalintmov.ui.theme.ProyectoFinalIntMovTheme
import com.example.proyectofinalintmov.krankenwagen.viewModels.KrankenwagenViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.SesionViewModel
import com.example.proyectofinalintmov.krankenwagen.viewModels.UrgenciesViewModel


class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel : KrankenwagenViewModel by viewModels()
        val sesionViewModel: SesionViewModel by viewModels()
        val ambulancesViewModel: AmbulancesViewModel by viewModels()
        val hospitalViewModel : HospitalViewModel by viewModels()
        val urgenciesViewModel: UrgenciesViewModel by viewModels()
        setContent {
            ProyectoFinalIntMovTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(viewModel, sesionViewModel, ambulancesViewModel, hospitalViewModel, urgenciesViewModel)
                }
            }
        }
    }
}
