package com.example.proyectofinalintmov.scrollprovincias

import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.example.proyectofinalintmov.R
import com.google.relay.compose.RelayContainer
import com.google.relay.compose.RelayContainerArrangement
import com.google.relay.compose.RelayContainerScope
import com.google.relay.compose.RelayImage
import com.google.relay.compose.RelayText
import com.google.relay.compose.tappable

/**
 * This composable was generated from the UI Package 'scroll_provincias'.
 * Generated code; do not edit directly
 */
@Composable
fun ScrollProvincias(
    modifier: Modifier = Modifier,
    onAlmerATapped: () -> Unit = {},
    onCDizTapped: () -> Unit = {},
    onCRdobaTapped: () -> Unit = {},
    onGranadaTapped: () -> Unit = {},
    onHuelvaTapped: () -> Unit = {},
    onJaenTapped: () -> Unit = {},
    onMLagaTapped: () -> Unit = {},
    onSevillaTapped: () -> Unit = {}
) {
    TopLevel(modifier = modifier) {
        AlmerA(onAlmerATapped = onAlmerATapped) {
            Image5(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = -77.0.dp
                    )
                )
            )
            ALMERA(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
        CDiz(onCDizTapped = onCDizTapped) {
            Image6(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = -87.5.dp
                    )
                )
            )
            CDIZ(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
        CRdoba(onCRdobaTapped = onCRdobaTapped) {
            Image1(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = -72.0.dp
                    )
                )
            )
            CRDOBA(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
        Granada(onGranadaTapped = onGranadaTapped) {
            Image7(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 20.0.dp,
                        y = -74.5.dp
                    )
                )
            )
            GRANADA(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
        Huelva(onHuelvaTapped = onHuelvaTapped) {
            Image8(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 6.5.dp,
                        y = -73.0.dp
                    )
                )
            )
            HUELVA(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
        Jaen(onJaenTapped = onJaenTapped) {
            Image9(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 20.0.dp,
                        y = -87.0.dp
                    )
                )
            )
            JAEN(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
        MLaga(onMLagaTapped = onMLagaTapped) {
            Image10(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 39.5.dp,
                        y = -87.0.dp
                    )
                )
            )
            MLAGA(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
        Sevilla(onSevillaTapped = onSevillaTapped) {
            Image2(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = 0.0.dp,
                        y = -87.5.dp
                    )
                )
            )
            SEVILLA(
                modifier = Modifier.boxAlign(
                    alignment = Alignment.Center,
                    offset = DpOffset(
                        x = -0.5.dp,
                        y = 141.5.dp
                    )
                )
            )
        }
    }
}

@Preview(widthDp = 2637, heightDp = 489)
@Composable
private fun ScrollProvinciasPreview() {
    MaterialTheme {
        ScrollProvincias(
            onAlmerATapped = {},
            onCDizTapped = {},
            onCRdobaTapped = {},
            onGranadaTapped = {},
            onHuelvaTapped = {},
            onJaenTapped = {},
            onMLagaTapped = {},
            onSevillaTapped = {}
        )
    }
}

@Composable
fun Image5(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_5),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(271.0.dp).requiredHeight(283.0.dp)
    )
}

@Composable
fun ALMERA(modifier: Modifier = Modifier) {
    RelayText(
        content = "ALMERÍA",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun AlmerA(
    onAlmerATapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onAlmerATapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun Image6(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_6),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(271.0.dp).requiredHeight(262.0.dp)
    )
}

@Composable
fun CDIZ(modifier: Modifier = Modifier) {
    RelayText(
        content = "CÁDIZ",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun CDiz(
    onCDizTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onCDizTapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun Image1(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_1),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(271.0.dp).requiredHeight(293.0.dp)
    )
}

@Composable
fun CRDOBA(modifier: Modifier = Modifier) {
    RelayText(
        content = "CÓRDOBA",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun CRdoba(
    onCRdobaTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onCRdobaTapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun Image7(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_7),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(311.0.dp).requiredHeight(288.0.dp)
    )
}

@Composable
fun GRANADA(modifier: Modifier = Modifier) {
    RelayText(
        content = "GRANADA",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun Granada(
    onGranadaTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onGranadaTapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun Image8(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_8),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(284.0.dp).requiredHeight(291.0.dp)
    )
}

@Composable
fun HUELVA(modifier: Modifier = Modifier) {
    RelayText(
        content = "HUELVA",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun Huelva(
    onHuelvaTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onHuelvaTapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun Image9(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_9),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(311.0.dp).requiredHeight(255.0.dp)
    )
}

@Composable
fun JAEN(modifier: Modifier = Modifier) {
    RelayText(
        content = "JAEN",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun Jaen(
    onJaenTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onJaenTapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun Image10(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_10),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(350.0.dp).requiredHeight(243.0.dp)
    )
}

@Composable
fun MLAGA(modifier: Modifier = Modifier) {
    RelayText(
        content = "MÁLAGA",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun MLaga(
    onMLagaTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onMLagaTapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun Image2(modifier: Modifier = Modifier) {
    RelayImage(
        image = painterResource(R.drawable.scroll_provincias_image_2),
        contentScale = ContentScale.Crop,
        modifier = modifier.requiredWidth(271.0.dp).requiredHeight(262.0.dp)
    )
}

@Composable
fun SEVILLA(modifier: Modifier = Modifier) {
    RelayText(
        content = "SEVILLA",
        fontSize = 48.0.sp,
        fontFamily = inter,
        height = 1.2102272510528564.em,
        fontWeight = FontWeight(600.0.toInt()),
        italic = true,
        maxLines = -1,
        modifier = modifier.requiredWidth(248.0.dp).requiredHeight(154.0.dp).wrapContentHeight(
            align = Alignment.CenterVertically,
            unbounded = true
        )
    )
}

@Composable
fun Sevilla(
    onSevillaTapped: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        isStructured = false,
        content = content,
        modifier = modifier.tappable(onTap = onSevillaTapped).requiredWidth(271.0.dp).requiredHeight(437.0.dp)
    )
}

@Composable
fun TopLevel(
    modifier: Modifier = Modifier,
    content: @Composable RelayContainerScope.() -> Unit
) {
    RelayContainer(
        arrangement = RelayContainerArrangement.Row,
        padding = PaddingValues(
            start = 0.0.dp,
            top = 26.0.dp,
            end = 0.0.dp,
            bottom = 26.0.dp
        ),
        itemSpacing = 67.0,
        content = content,
        modifier = modifier.height(IntrinsicSize.Min)
    )
}
