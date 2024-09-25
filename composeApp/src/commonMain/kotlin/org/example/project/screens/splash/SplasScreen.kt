package org.example.project.screens.splash

import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.progressbar_background
import firstkmpproject.composeapp.generated.resources.splash_image
import org.example.project.screens.elements.CustomProgressBar
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.lighthousegames.logging.logging

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SplashScreen(openMenu: () -> Unit) {
    val log = logging("SplashScreen")
    var enabled by remember { mutableStateOf(false) }
    val viewModel = koinViewModel<SplashViewModel>()
    val alpha: Float by animateFloatAsState(
        targetValue = if (enabled) 1f else 0f,
        animationSpec = tween(durationMillis = 2000, easing = LinearOutSlowInEasing),
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
        CustomProgressBar(
            Modifier.align(Alignment.BottomCenter).padding(20.dp),
            progress = alpha,
            Res.drawable.progressbar_background
        )
    }
}




