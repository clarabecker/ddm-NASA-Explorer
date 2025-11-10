package com.example.ddmnasaexplorer.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

// 1. Define o esquema de cores escuras
private val DarkColorScheme = darkColorScheme(
    primary = NasaBlue,
    background = DarkBackground,
    surface = CardBackground, // Cor de fundo dos Cards
    onPrimary = TextWhite,
    onBackground = TextWhite,
    onSurface = TextWhite // Cor do texto em cima dos Cards
)

// 2. Este é o Composable do Tema
@Composable
fun DdmNASAExplorerTheme(
    content: @Composable () -> Unit
) {
    // tema escuro
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}