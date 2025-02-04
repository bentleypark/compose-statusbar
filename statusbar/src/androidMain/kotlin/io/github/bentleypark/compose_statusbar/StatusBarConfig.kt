package io.github.bentleypark.compose_statusbar

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun ConfigureStatusBar(
    color: Color,
    bottomBarColor: Color?,
    onDispose: (() -> Unit)?
) {
    val view = LocalView.current
    val context = view.context
    val window = (context as Activity).window
    val isDark = !color.luminance().isLight()

    val controller = WindowCompat.getInsetsController(window, view)
    val originalStatusBarAppearance = controller.isAppearanceLightStatusBars
    val originalNavigationBarAppearance = controller.isAppearanceLightNavigationBars

    DisposableEffect(color, bottomBarColor) {
        WindowCompat.setDecorFitsSystemWindows(window, false)

        @Suppress("DEPRECATION")
        window.statusBarColor = color.toArgb()

        controller.isAppearanceLightStatusBars = !isDark

        bottomBarColor?.let { barColor ->
            val isBarDark = !barColor.luminance().isLight()
            @Suppress("DEPRECATION")
            window.navigationBarColor = barColor.toArgb()
            controller.isAppearanceLightNavigationBars = !isBarDark
        }

        onDispose {
            WindowCompat.setDecorFitsSystemWindows(window, true)

            controller.isAppearanceLightStatusBars = originalStatusBarAppearance
            controller.isAppearanceLightNavigationBars = originalNavigationBarAppearance

            @Suppress("DEPRECATION")
            window.statusBarColor = Color.Transparent.toArgb()
            @Suppress("DEPRECATION")
            window.navigationBarColor = Color.Transparent.toArgb()

            onDispose?.invoke()
        }
    }
}

private fun Float.isLight() = this >= 0.5f

private fun Color.luminance(): Float {
    val red = red.coerceIn(0f, 1f)
    val green = green.coerceIn(0f, 1f)
    val blue = blue.coerceIn(0f, 1f)

    return (0.299f * red + 0.587f * green + 0.114f * blue)
}