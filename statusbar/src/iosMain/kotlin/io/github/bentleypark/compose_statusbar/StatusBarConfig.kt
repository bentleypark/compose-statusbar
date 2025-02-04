package io.github.bentleypark.compose_statusbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import platform.UIKit.UIApplication
import platform.UIKit.UIColor

@Composable
actual fun ConfigureStatusBar(color: Color, onDispose: (() -> Unit)?) {
    DisposableEffect(color) {
        val window = UIApplication.sharedApplication.keyWindow
        val originalBackgroundColor = window?.rootViewController?.view?.backgroundColor

        val uiColor = UIColor(
            red = color.red.toDouble(),
            green = color.green.toDouble(),
            blue = color.blue.toDouble(),
            alpha = color.alpha.toDouble()
        )

        window?.rootViewController?.view?.backgroundColor = uiColor

        onDispose {
            window?.rootViewController?.view?.backgroundColor = originalBackgroundColor
            onDispose?.invoke()
        }
    }
}