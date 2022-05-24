package com.gigamole.udandroidtask.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = LightBlue,
    primaryVariant = LightBlue,
    secondary = DarkGrey,
    background = Color.Black
)
private val LightColorPalette = lightColors(
    primary = Blue,
    primaryVariant = Blue,
    secondary = Grey,
    background = Color.White
)

@Composable
fun UDAndroidTaskTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    SideEffect {
        systemUiController.setSystemBarsColor(
            color = colors.background,
            darkIcons = !darkTheme
        )
        systemUiController.setNavigationBarColor(
            color = colors.background,
            darkIcons = !darkTheme
        )
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
