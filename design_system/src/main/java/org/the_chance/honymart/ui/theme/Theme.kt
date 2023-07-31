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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val localDimens = compositionLocalOf { Dimens() }

private val DarkColorScheme = darkColorScheme(
    primary = primary100,
    onPrimary = darkText87,
    background = darkBackground,
    onBackground = darkText60,
    error = darkError,
    secondaryContainer = darkBackground300,
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
    useDarkIcons :Boolean = true,
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

            /*ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { view, windowInsets ->
                val insets = windowInsets.getInsets(
                    WindowInsetsCompat.Type.systemGestures()
                )
                view.updatePadding(
                    bottom = insets.bottom
                )
                WindowInsetsCompat.CONSUMED
            }*/
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


