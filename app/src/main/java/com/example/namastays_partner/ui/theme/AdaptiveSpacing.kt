package com.example.namastays_partner.ui.theme

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
data class AdaptiveSpacing(
    val edge: Dp,
    val large: Dp,
    val medium: Dp,
    val small: Dp,
    val xs: Dp
)

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun rememberAdaptiveSpacing(): AdaptiveSpacing {

    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    return remember(screenHeight) {
        AdaptiveSpacing(
            edge = (screenHeight * 0.05f).coerceIn(20.dp, 60.dp),
            large = (screenHeight * 0.045f).coerceIn(18.dp, 50.dp),
            medium = (screenHeight * 0.03f).coerceIn(12.dp, 32.dp),
            small = (screenHeight * 0.02f).coerceIn(8.dp, 20.dp),
            xs = (screenHeight * 0.012f).coerceIn(4.dp, 12.dp)
        )
    }
}