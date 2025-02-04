package io.github.bentleypark.sample

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import compose_statusbar.sample.composeapp.generated.resources.Res
import compose_statusbar.sample.composeapp.generated.resources.splash_logo
import io.github.bentleypark.compose_statusbar.ConfigureStatusBar
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource

val GreenOrange = Color(0xFF7CD685)

@Composable
fun SplashScreen(onSplashComplete: () -> Unit) {

    ConfigureStatusBar(color = GreenOrange)

    LaunchedEffect(Unit) {
        delay(2000)
        onSplashComplete()
    }

    Box(
        Modifier
            .fillMaxSize()
            .background(GreenOrange),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.splash_logo),
            contentDescription = null,
            modifier = Modifier.wrapContentSize()
        )
    }
}