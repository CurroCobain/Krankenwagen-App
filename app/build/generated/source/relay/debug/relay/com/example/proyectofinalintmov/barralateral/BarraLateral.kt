package com.example.proyectofinalintmov.barralateral

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectofinalintmov.R
import com.google.relay.compose.MainAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable

/**
 * This composable was generated from the UI Package 'barra_lateral'.
 * Generated code; do not edit directly
 */
@Composable
fun BarraLateral(
    modifier: Modifier = Modifier,
    onWelcTapped: () -> Unit = {},
    onHospTapped: () -> Unit = {},
    onAmbTapped: () -> Unit = {},
    onDocTapped: () -> Unit = {}
) {
    TopLevel(modifier = modifier) {
        Welc(onWelcTapped = onWelcTapped)
        Hosp(onHospTapped = onHospTapped)
        Amb(onAmbTapped = onAmbTapped)
        Doc(onDocTapped = onDocTapped)
    }
}

@Preview(widthDp = 144, heightDp = 790)
@Composable
private fun BarraLateralPreview() {
    MaterialTheme {
        BarraLateral(
            onWelcTapped = {},
            onHospTapped = {},
            onAmbTapped = {},
            onDocTapped = {}
        )
    }
}

@Composable
fun Welc(
    onWelcTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.barra_lateral_welc),
        modifier = modifier.tappable(onTap = onWelcTapped).requiredWidth(100.0.dp).requiredHeight(88.89645385742188.dp)
    )
}

@Composable
fun Hosp(
    onHospTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.barra_lateral_hosp),
        modifier = modifier.tappable(onTap = onHospTapped).requiredWidth(100.0002670288086.dp).requiredHeight(80.0.dp)
    )
}

@Composable
fun Amb(
    onAmbTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.barra_lateral_amb),
        modifier = modifier.tappable(onTap = onAmbTapped).requiredWidth(100.0.dp).requiredHeight(80.0.dp)
    )
}

@Composable
fun Doc(
    onDocTapped: () -> Unit,
    modifier: Modifier = Modifier
) {
    RelayVector(
        vector = painterResource(R.drawable.barra_lateral_doc),
        modifier = modifier.tappable(onTap = onDocTapped).requiredWidth(100.0.dp).requiredHeight(114.28571319580078.dp)
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
            red = 73,
            green = 121,
            blue = 65
        ),
        mainAxisAlignment = MainAxisAlignment.Start,
        padding = PaddingValues(
            start = 22.0.dp,
            top = 80.0.dp,
            end = 22.0.dp,
            bottom = 80.0.dp
        ),
        itemSpacing = 89.0,
        clipToParent = false,
        content = content,
        modifier = modifier
    )
}
