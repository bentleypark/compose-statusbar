package io.github.bentleypark.compose_statusbar

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@Composable
expect fun ConfigureStatusBar(
    color: Color,
    bottomBarColor: Color? = null,
    onDispose: (() -> Unit)? = null
)