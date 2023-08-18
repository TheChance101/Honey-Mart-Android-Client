package org.the_chance.honymart.ui.theme


import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val localDimens = compositionLocalOf { Dimens() }

private val DarkColorScheme = darkColorScheme(
    primary = primary100,
    onPrimary = darkText87,
    secondary = darkBackground200,
    onSecondary = white,
    background = darkBackground200,
    onBackground = black60,
    error = darkError,
    secondaryContainer = darkBackground400,
    onSecondaryContainer = darkText37,
    tertiary = darkBackground400,
    onTertiary = darkBackground300,
    onTertiaryContainer = black37,
    tertiaryContainer = darkBackground300,
    onSurfaceVariant = blackOn60,
    errorContainer = darkBackground400,
    inverseOnSurface = darkBackground300,
    onError = primary100,
    onErrorContainer = black37,
    outlineVariant = white,
)

private val LightColorScheme = lightColorScheme(
    primary = primary100,
    onPrimary = white,
    secondary = white,
    onSecondary = black87,
    background = background,
    onBackground = black60,
    error = error,
    secondaryContainer = white100,
    onSecondaryContainer = black37,
    tertiary = white,
    onTertiary = white,
    onTertiaryContainer = black16,
    tertiaryContainer = white30,
    onSurfaceVariant = white87,
    errorContainer = primary100,
    inverseOnSurface = primary100,
    onError = white,
    onErrorContainer = primary100,
    outlineVariant = black8,

    )

val MaterialTheme.dimens: Dimens
    @Composable
    @ReadOnlyComposable
    get() = localDimens.current

@Composable
fun HoneyMartTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    useDarkIcons: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val systemUiController = rememberSystemUiController()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons,
            isNavigationBarContrastEnforced = false
        )

        onDispose {}
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            WindowCompat.setDecorFitsSystemWindows(window, false)

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


