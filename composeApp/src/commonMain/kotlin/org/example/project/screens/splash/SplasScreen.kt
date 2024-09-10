package org.example.project.screens.splash

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp

@Composable
fun SplashScreen() {
    val splashAnimation = rememberInfiniteTransition().animateValue(
        initialValue = 0.7f,
        targetValue = 1f,
        typeConverter = Float.VectorConverter,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000),
            repeatMode = RepeatMode.Reverse
        )

    )
    Box(Modifier.fillMaxSize().background(Color.LightGray)) {
        Text(
            text = "Splash Screen",
            color = Color.Black,
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White)
                .align(Alignment.Center)
                .graphicsLayer {
                    scaleX = splashAnimation.value
                    scaleY = splashAnimation.value
                }
        )

    }
}