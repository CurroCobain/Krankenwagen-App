package com.example.proyectofinalintmov.menu

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectofinalintmov.R
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable

/**
 * This composable was generated from the UI Package 'menu'.
 * Generated code; do not edit directly
 */
@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onMenuTapped: () -> Unit = {}
) {
    TopLevel(
        onMenuTapped = onMenuTapped,
        modifier = modifier
    ) {
        Group(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)) {
            Vector(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
            Rayas(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
        }
    }
}

@Preview(widthDp = 80, heightDp = 80)
@Composable
private fun MenuPreview() {
    MaterialTheme {
        RelayContainer {
            Menu(
                onMenuTapped = {},
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun Vector(modifier: Modifier = Modifier) {
    RelayVector(modifier = modifier.fillMaxWidth(1.0f).fillMaxHeight(1.0f))
}

@Composable
fun Rayas(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.menu_rayas),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 8.333343505859375.dp,
                top = 11.66668701171875.dp,
                end = 8.333324432373047.dp,
                bottom = 11.666645050048828.dp
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
fun TopLevel(
    onMenuTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onMenuTapped).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}
