package com.example.proyectofinalintmov.iconmenu

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
import androidx.compose.ui.draw.alpha
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
 * This composable was generated from the UI Package 'icon_menu'.
 * Generated code; do not edit directly
 */
@Composable
fun IconMenu(
    modifier: Modifier = Modifier,
    onMenuTapped: () -> Unit = {}
) {
    TopLevel(
        onMenuTapped = onMenuTapped,
        modifier = modifier
    ) {
        TopLevelSynth {
            A()
        }
        B(modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f))
    }
}

@Preview(widthDp = 80, heightDp = 80)
@Composable
private fun IconMenuPreview() {
    MaterialTheme {
        RelayContainer {
            IconMenu(
                onMenuTapped = {},
                modifier = Modifier.rowWeight(1.0f).columnWeight(1.0f)
            )
        }
    }
}

@Composable
fun A(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.icon_menu_a),
        modifier = modifier.requiredWidth(80.0.dp).requiredHeight(80.0.dp)
    )
}

@Composable
fun TopLevelSynth(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.End,
        crossAxisAlignment = CrossAxisAlignment.Start,
        arrangement = RelayContainerArrangement.Row,
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
        modifier = modifier.height(IntrinsicSize.Min).alpha(alpha = 100.0f)
    )
}

@Composable
fun B(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.icon_menu_b),
        modifier = modifier.padding(
            paddingValues = PaddingValues(
                start = 20.0.dp,
                top = 19.9320068359375.dp,
                end = 20.0.dp,
                bottom = 19.931991577148438.dp
            )
        ).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
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
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = onMenuTapped).fillMaxWidth(1.0f).fillMaxHeight(1.0f)
    )
}
