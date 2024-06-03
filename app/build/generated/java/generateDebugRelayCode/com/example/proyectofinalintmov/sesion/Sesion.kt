package com.example.proyectofinalintmov.sesion

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectofinalintmov.R
import com.google.relay.compose.CrossAxisAlignment
import com.google.relay.compose.MainAxisAlignment
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable

/**
 * This composable was generated from the UI Package 'sesion'.
 * Generated code; do not edit directly
 */
@Composable
fun Sesion(
    modifier: Modifier = Modifier,
    onSesionTapped: () -> Unit = {}
) {
    TopLevel(
        onSesionTapped = onSesionTapped,
        modifier = modifier
    ) {
        Vector()
    }
}

@Preview(widthDp = 80, heightDp = 77)
@Composable
private fun SesionPreview() {
    MaterialTheme {
        Sesion(onSesionTapped = {})
    }
}

@Composable
fun Vector(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.sesion_vector),
        modifier = modifier.requiredWidth(60.0.dp).requiredHeight(63.33333206176758.dp)
    )
}

@Composable
fun TopLevel(
    onSesionTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.End,
        crossAxisAlignment = CrossAxisAlignment.End,
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 10.0.dp,
            top = 7.0.dp,
            end = 10.0.dp,
            bottom = 7.0.dp
        ),
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = onSesionTapped).height(IntrinsicSize.Min)
    )
}
