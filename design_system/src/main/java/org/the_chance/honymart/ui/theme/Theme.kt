package org.the_chance.honymart.ui.theme


import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val localDimens = compositionLocalOf { Dimens() }

private val DarkColorScheme = darkColorScheme(
    primary = primary100,
    onPrimary = darkText87,
    background = background,
    onBackground = darkText60,
    error = darkError,
    secondaryContainer = darkBackground400,
    onSecondaryContainer = darkText37,

)

private val LightColorScheme = lightColorScheme(
    primary = primary100,
    onPrimary = white,
    background = background,
    onBackground = black60,
    error = error,
    secondaryContainer = white100,
    onSecondaryContainer = black37,
)

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = localDimens.current

@Composable
fun HoneyMartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(localDimens provides Dimens()) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            shapes = Shapes,
            content = content,

        )
    }
}


