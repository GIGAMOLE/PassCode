package com.gigamole.udandroidtask.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val Typography = Typography(
    h1 = TextStyle(
        fontFamily = LatoFonts,
        fontWeight = FontWeight.Black,
        fontSize = 34.sp
    ),
    body1 = TextStyle(
        fontFamily = LatoFonts,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
    ),
    body2 = TextStyle(
        fontFamily = LatoFonts,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontFamily = LatoFonts,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp
    )
)

val PasscodeKeyButtonStyle = TextStyle(
    fontFamily = LatoFonts,
    fontWeight = FontWeight.Bold,
    fontSize = 24.sp
)
