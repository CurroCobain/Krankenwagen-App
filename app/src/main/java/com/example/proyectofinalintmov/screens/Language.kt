package com.example.proyectofinalintmov.screens
/*
import android.content.Context
import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

// Creamos un CompositionLocal para almacenar el idioma seleccionado
private val LocalAppLanguage = staticCompositionLocalOf<String> { error("No se ha proporcionado ningún idioma") }

@Composable
fun ProvideAppLanguage(language: String, content: @Composable () -> Unit) {
    // Almacenamos el idioma en el CompositionLocal
    val appLanguage = remember { language }
    CompositionLocalProvider(LocalAppLanguage provides appLanguage) {
        content()
    }
}

@Composable
fun GetAppLanguage(): String {
    // Obtenemos el idioma almacenado en el CompositionLocal
    return LocalAppLanguage.current
}

fun changeAppLanguage(context: Context, language: String) {
    // Creamos una configuración con el idioma seleccionado
    val locale = java.util.Locale(language)
    val configuration = Configuration(context.resources.configuration)
    configuration.setLocale(locale)

    // Actualizamos la configuración de la aplicación con el nuevo idioma
    context.createConfigurationContext(configuration)
}

*/