package io.github.bentleypark.compose_statusbar

import android.app.Activity
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
actual fun ConfigureStatusBar(color: Color, onDispose: (() -> Unit)?) {
    val view = LocalView.current
    val context = view.context
    val window = (context as Activity).window
    val isDark = !color.luminance().isLight()

    val originalStatusBarColor = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars
    } else {
        @Suppress("DEPRECATION")
        window.statusBarColor
    }

    DisposableEffect(color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            WindowCompat.getInsetsController(window, view).apply {
                isAppearanceLightStatusBars = !isDark
            }
        } else {
            @Suppress("DEPRECATION")
            window.statusBarColor = color.toArgb()
        }

        onDispose {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                WindowCompat.getInsetsController(window, view).apply {
                    isAppearanceLightStatusBars = originalStatusBarColor as Boolean
                }
            } else {
                @Suppress("DEPRECATION")
                window.statusBarColor = originalStatusBarColor as Int
            }
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