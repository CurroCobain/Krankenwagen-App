package com.example.proyectofinalintmov.bienvenida

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
    bienvenidoADrHouseTextContent: String = ""
) {
    TopLevel(modifier = modifier) {
        BienvenidoADrHouse(bienvenidoADrHouseTextContent = bienvenidoADrHouseTextContent)
    }
}

@Preview(widthDp = 400, heightDp = 39)
@Composable
private fun BienvenidaPreview() {
    MaterialTheme {
        RelayContainer {
            Bienvenida(
                bienvenidoADrHouseTextContent = "Bienvenido/a Dr. House",
                modifier = Modifier.rowWeight(1.0f)
            )
        }
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
            red = 255,
            green = 255,
            blue = 255
        ),
        height = 1.2102272510528564.em,
        modifier = modifier.wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
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
            red = 80,
            green = 79,
            blue = 132
        ),
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 10.0,
        radius = 8.0,
        content = content,
        modifier = modifier.height(IntrinsicSize.Min).fillMaxWidth(1.0f)
    )
}
