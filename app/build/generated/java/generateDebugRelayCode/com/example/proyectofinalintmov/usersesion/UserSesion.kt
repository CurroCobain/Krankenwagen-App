package com.example.proyectofinalintmov.usersesion

import androidx.compose.foundation.layout.IntrinsicSize
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
 * This composable was generated from the UI Package 'user_sesion'.
 * Generated code; do not edit directly
 */
@Composable
fun UserSesion(
    modifier: Modifier = Modifier,
    onUserTapped: () -> Unit = {}
) {
    TopLevel(
        onUserTapped = onUserTapped,
        modifier = modifier
    ) {
        Vector()
    }
}

@Preview(widthDp = 80, heightDp = 80)
@Composable
private fun UserSesionPreview() {
    MaterialTheme {
        UserSesion(onUserTapped = {})
    }
}

@Composable
fun Vector(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.user_sesion_vector),
        modifier = modifier.requiredWidth(80.0.dp).requiredHeight(80.0.dp)
    )
}

@Composable
fun TopLevel(
    onUserTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.End,
        crossAxisAlignment = CrossAxisAlignment.End,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = onUserTapped).height(IntrinsicSize.Min)
    )
}
