package com.example.proyectofinalintmov.barramenuusuario

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectofinalintmov.R
import com.example.proyectofinalintmov.sesion.Sesion
import com.google.relay.compose.CrossAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayVector

/**
 * This composable was generated from the UI Package 'barra_menu_usuario'.
 * Generated code; do not edit directly
 */
@Composable
fun BarraMenuUsuario(modifier: Modifier = Modifier) {
    TopLevel(modifier = modifier) {
        Menu {
            Group(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)) {
                Vector(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
                Rayas(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
            }
        }
        SesionInstance()
    }
}

@Preview(widthDp = 1050, heightDp = 104)
@Composable
private fun BarraMenuUsuarioPreview() {
    MaterialTheme {
        BarraMenuUsuario()
    }
}

@Composable
fun Vector(modifier: Modifier = Modifier) {
    RelayVector(modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f))
}

@Composable
fun Rayas(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.barra_menu_usuario_rayas),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 8.3333740234375.dp,
                top = 11.666748046875.dp,
                end = 8.333293914794922.dp,
                bottom = 11.666584014892578.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Group(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun Menu(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.requiredWidth(80.0.dp).requiredHeight(80.0.dp)
    )
}

@Composable
fun SesionInstance(modifier: Modifier = Modifier) {
    Sesion(modifier = modifier)
}

@Composable
fun TopLevel(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 56.0.dp,
            top = 12.0.dp,
            end = 56.0.dp,
            bottom = 12.0.dp
        ),
        itemSpacing = 778.0,
        content = content,
        modifier = modifier.height(IntrinsicSize.Min)
    )
}
