package com.example.proyectofinalintmov.botoncito

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.proyectofinalintmov.R
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayText
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable

/**
 * This composable was generated from the UI Package 'botoncito'.
 * Generated code; do not edit directly
 */
@Composable
fun Botoncito(
    modifier: Modifier = Modifier,
    iniciarSesiNTextContent: String = "",
    onBotoncitoTapped: () -> Unit = {}
) {
    TopLevel(
        onBotoncitoTapped = onBotoncitoTapped,
        modifier = modifier
    ) {
        Boton(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)) {
            Rectangle1(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
            IniciarSesiN(
                iniciarSesiNTextContent = iniciarSesiNTextContent,
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = 0.0.dp
                    )
                )
            )
        }
    }
}

@Preview(widthDp = 240, heightDp = 64)
@Composable
private fun BotoncitoPreview() {
    MaterialTheme {
        RelayContainer {
            Botoncito(
                onBotoncitoTapped = {},
                iniciarSesiNTextContent = "Iniciar sesión",
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun Rectangle1(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.botoncito_rectangle_1),
        modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}

@Composable
fun IniciarSesiN(
    iniciarSesiNTextContent: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = iniciarSesiNTextContent,
        fontSize = 24.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(700.0.toInt()),
        maxLines = -1,
        modifier = modifier.requiredWidth(240.0.dp).requiredHeight(64.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun Boton(
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
fun TopLevel(
    onBotoncitoTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = onBotoncitoTapped).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}
