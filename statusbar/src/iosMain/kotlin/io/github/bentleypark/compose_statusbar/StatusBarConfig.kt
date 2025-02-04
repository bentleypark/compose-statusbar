package io.github.bentleypark.compose_statusbar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.graphics.Color
import kotlinx.cinterop.ExperimentalForeignApi
import platform.UIKit.NSLayoutConstraint
import platform.UIKit.UIApplication
import platform.UIKit.UIColor
import platform.UIKit.UINavigationController
import platform.UIKit.UIStatusBarStyleDarkContent
import platform.UIKit.UIStatusBarStyleLightContent
import platform.UIKit.UITabBarController
import platform.UIKit.UIView
import platform.UIKit.UIViewAutoresizingFlexibleHeight
import platform.UIKit.UIViewAutoresizingFlexibleWidth
import platform.UIKit.setStatusBarStyle

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun ConfigureStatusBar(
    color: Color,
    bottomBarColor: Color?,
    onDispose: (() -> Unit)?
) {
    DisposableEffect(color, bottomBarColor) {
        val window = UIApplication.sharedApplication.keyWindow!!
        val rootVC = window.rootViewController!!
        val layoutGuide = rootVC.view.safeAreaLayoutGuide

        // Create overlay view for the entire screen
        val overlayView = UIView(frame = rootVC.view.bounds).apply {
            backgroundColor = UIColor.clearColor
            tag = OVERLAY_VIEW_TAG
            setAutoresizingMask(UIViewAutoresizingFlexibleWidth or UIViewAutoresizingFlexibleHeight)
            clipsToBounds = false
        }

        // Create status bar view
        val statusBarView = UIView().apply {
            backgroundColor = UIColor(
                red = color.red.toDouble(),
                green = color.green.toDouble(),
                blue = color.blue.toDouble(),
                alpha = color.alpha.toDouble()
            )
            translatesAutoresizingMaskIntoConstraints = false
        }

        // Add views to hierarchy
        overlayView.addSubview(statusBarView)
        rootVC.view.addSubview(overlayView)

        // Setup constraints for status bar view
        NSLayoutConstraint.activateConstraints(
            listOf(
                statusBarView.topAnchor.constraintEqualToAnchor(overlayView.topAnchor),
                statusBarView.leadingAnchor.constraintEqualToAnchor(overlayView.leadingAnchor),
                statusBarView.trailingAnchor.constraintEqualToAnchor(overlayView.trailingAnchor),
                statusBarView.bottomAnchor.constraintEqualToAnchor(layoutGuide.topAnchor)
            )
        )

        // Set status bar style based on color brightness
        UIApplication.sharedApplication.setStatusBarStyle(
            if (color.luminance() < 0.5f) UIStatusBarStyleLightContent else UIStatusBarStyleDarkContent,
            animated = true
        )

        // Handle bottom bar color if provided
        bottomBarColor?.let { barColor ->
            val uiBarColor = UIColor(
                red = barColor.red.toDouble(),
                green = barColor.green.toDouble(),
                blue = barColor.blue.toDouble(),
                alpha = barColor.alpha.toDouble()
            )

            (rootVC as? UITabBarController)?.let { tabController ->
                tabController.tabBar.backgroundColor = uiBarColor
            }

            (rootVC as? UINavigationController)?.let { navController ->
                navController.toolbar.backgroundColor = uiBarColor
            }
        }

        onDispose {
            // Remove overlay view
            rootVC.view.viewWithTag(OVERLAY_VIEW_TAG)?.removeFromSuperview()

            // Reset bottom bar colors if they were modified
            if (bottomBarColor != null) {
                (rootVC as? UITabBarController)?.tabBar?.backgroundColor = null
                (rootVC as? UINavigationController)?.toolbar?.backgroundColor = null
            }

            onDispose?.invoke()
        }
    }
}

private const val OVERLAY_VIEW_TAG: Long = 9999L

private fun Color.luminance(): Float {
    val red = red.coerceIn(0f, 1f)
    val green = green.coerceIn(0f, 1f)
    val blue = blue.coerceIn(0f, 1f)
    return (0.299f * red + 0.587f * green + 0.114f * blue)
}