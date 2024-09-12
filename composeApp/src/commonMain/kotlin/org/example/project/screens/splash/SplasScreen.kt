package org.example.project.screens.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.loading
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.progressbar_background
import firstkmpproject.composeapp.generated.resources.splash_image
import org.jetbrains.compose.resources.painterResource
import org.lighthousegames.logging.logging

@Composable
fun SplashScreen(openMenu: () -> Unit) {
    val log = logging("SplashScreen")
    var enabled by remember { mutableStateOf(false) }
    val alpha: Float by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f,
        animationSpec = tween(durationMillis = 3000, easing = LinearOutSlowInEasing),
        label = "alpha",
        finishedListener = {
            openMenu()
        }
    )
    LaunchedEffect(Unit) {
        enabled = true
    }
    Box(
        Modifier.fillMaxSize().paint(
            painter = painterResource(Res.drawable.main_backgroud),
            contentScale = ContentScale.FillBounds
        )
    ) {
        Image(
            painterResource(Res.drawable.splash_image),
            contentDescription = null,
            modifier = Modifier.align(Alignment.Center).fillMaxWidth()
        )
        SplashProgress(
            Modifier.align(Alignment.BottomCenter).padding(20.dp),
            progress = alpha
        )
    }
}

@Composable
fun SplashProgress(modifier: Modifier = Modifier, progress: Float) {
    Box(
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LinearProgressIndicator(
                progress = progress,
                color = Color.Yellow,
                modifier = Modifier.fillMaxWidth().paint(
                    painterResource(Res.drawable.progressbar_background),
                    contentScale = ContentScale.FillBounds
                ).padding(12.dp)
            )
            Image(painter = painterResource(Res.drawable.loading), contentDescription = null)
        }
    }
}


