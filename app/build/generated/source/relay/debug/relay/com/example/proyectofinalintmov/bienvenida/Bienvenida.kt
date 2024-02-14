package com.example.proyectofinalintmov.bienvenida

import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.google.relay.compose.CrossAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayText

/**
 * This composable was generated from the UI Package 'bienvenida'.
 * Generated code; do not edit directly
 */
@Composable
fun Bienvenida(
    modifier: Modifier = Modifier,
    bienvenidoADrHouseTextContent: String
) {
    TopLevel(modifier = modifier) {
        BienvenidoADrHouse(bienvenidoADrHouseTextContent = bienvenidoADrHouseTextContent)
    }
}

@Preview(widthDp = 353, heightDp = 39)
@Composable
private fun BienvenidaPreview() {
    MaterialTheme {
        Bienvenida(bienvenidoADrHouseTextContent = "Bienvenido/a Dr. House")
    }
}

@Composable
fun BienvenidoADrHouse(
    bienvenidoADrHouseTextContent: String,
    modifier: Modifier = Modifier
) {
    RelayText(
        content = bienvenidoADrHouseTextContent,
        fontSize = 32.0.sp,
        fontFamily = inter,
        color = Color(
            alpha = 255,
            red = 8,
            green = 7,
            blue = 7
        ),
        height = 1.2102272510528564.em,
        modifier = modifier.wrapContentHeight(align = Alignment.CenterVertically)
    )
}

@Composable
fun TopLevel(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        backgroundColor = Color(
            alpha = 255,
            red = 158,
            green = 234,
            blue = 146
        ),
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 10.0,
        radius = 8.0,
        content = content,
        modifier = modifier
    )
}
