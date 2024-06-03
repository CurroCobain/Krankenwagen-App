package com.example.proyectofinalintmov.arrowback

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.width
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
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayVector
import com.google.relay.compose.tappable

/**
 * This composable was generated from the UI Package 'arrow_back'.
 * Generated code; do not edit directly
 */
@Composable
fun ArrowBack(
    modifier: Modifier = Modifier,
    onArrowBackTapped: () -> Unit = {}
) {
    TopLevel(
        onArrowBackTapped = onArrowBackTapped,
        modifier = modifier
    ) {
        Vector()
    }
}

@Preview(widthDp = 79, heightDp = 75)
@Composable
private fun ArrowBackPreview() {
    MaterialTheme {
        ArrowBack(onArrowBackTapped = {})
    }
}

@Composable
fun Vector(modifier: Modifier = Modifier) {
    RelayVector(
        vector = painterResource(R.drawable.arrow_back_vector),
        modifier = modifier.requiredWidth(61.37999725341797.dp).requiredHeight(59.02333450317383.dp)
    )
}

@Composable
fun TopLevel(
    onArrowBackTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        mainAxisAlignment = MainAxisAlignment.End,
        crossAxisAlignment = CrossAxisAlignment.End,
        padding = PaddingValues(
            start = 9.0.dp,
            top = 8.0.dp,
            end = 9.0.dp,
            bottom = 8.0.dp
        ),
        itemSpacing = 10.0,
        clipToParent = false,
        content = content,
        modifier = modifier.tappable(onTap = onArrowBackTapped).width(IntrinsicSize.Min)
    )
}
