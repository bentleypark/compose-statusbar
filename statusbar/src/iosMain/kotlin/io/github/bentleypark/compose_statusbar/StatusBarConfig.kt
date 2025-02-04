package io.github.bentleypark.compose_statusbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UINavigationController
import platform.UIKit.UITabBarController

@Composable
actual fun ConfigureStatusBar(
    color: Color,
    bottomBarColor: Color?,
    onDispose: (() -> Unit)?
) {
    DisposableEffect(color, bottomBarColor) {
        val window = UIApplication.sharedApplication.keyWindow
        val originalBackgroundColor = window?.rootViewController?.view?.backgroundColor

        val uiColor = UIColor(
            red = color.red.toDouble(),
            green = color.green.toDouble(),
            blue = color.blue.toDouble(),
            alpha = color.alpha.toDouble()
        )

        window?.rootViewController?.view?.backgroundColor = uiColor

        bottomBarColor?.let { barColor ->
            val uiBarColor = UIColor(
                red = barColor.red.toDouble(),
                green = barColor.green.toDouble(),
                blue = barColor.blue.toDouble(),
                alpha = barColor.alpha.toDouble()
            )

            (window?.rootViewController as? UITabBarController)?.let { tabController ->
                tabController.tabBar.backgroundColor = uiBarColor
            }

            (window?.rootViewController as? UINavigationController)?.let { navController ->
                navController.toolbar.backgroundColor = uiBarColor
            }
        }

        onDispose {
            window?.rootViewController?.view?.backgroundColor = originalBackgroundColor

            if (bottomBarColor != null) {
                (window?.rootViewController as? UITabBarController)?.tabBar?.backgroundColor = null
                (window?.rootViewController as? UINavigationController)?.toolbar?.backgroundColor =
                    null
            }

            onDispose?.invoke()
        }
    }
}