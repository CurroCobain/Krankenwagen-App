package com.example.proyectofinalintmov.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Iterms_menu_lateral (
    val icon : ImageVector,
    val tittle: String,
    val route: String
){
    object Item_menu: Iterms_menu_lateral(
        Icons.Outlined.Menu,
        "abrir menú",
        Routes.PantallaWelcome.route
    )
    object Item_sesion: Iterms_menu_lateral(
        Icons.Outlined.Person,
        "sesión usuario",
        Routes.PantallaWelcome.route
    )
}