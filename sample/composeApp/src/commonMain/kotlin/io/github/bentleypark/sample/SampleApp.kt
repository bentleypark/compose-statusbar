package io.github.bentleypark.sample

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun SampleApp() {
    val splashViewModel = SplashViewModel()
    var isSplashFinished by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        splashViewModel.isSplashFinished.collect { isFinished ->
            isSplashFinished = isFinished
        }
    }

    if (isSplashFinished) {
        MainScreen()
    } else {
        SplashScreen { isSplashFinished = true }
    }
}