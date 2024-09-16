package org.example.project.screens.game

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.game_level
import firstkmpproject.composeapp.generated.resources.game_progress_bar_game
import firstkmpproject.composeapp.generated.resources.pers
import firstkmpproject.composeapp.generated.resources.timer_background
import firstkmpproject.composeapp.generated.resources.ultimate
import org.example.project.repository.GameLevelStatus
import org.example.project.screens.elements.CustomProgressBar
import org.example.project.screens.elements.getTypography
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.lighthousegames.logging.logging

@OptIn(KoinExperimentalAPI::class, ExperimentalComposeUiApi::class)
@Composable
fun GameScreen(onBack: () -> Unit, string: String) {
    val log = logging("GameScreen")

    val viewModel = koinViewModel<GameViewM0del>()
    var timer by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = timer) {
        log.e { "timer $timer" }
        viewModel.createTimer() {
            timer = it.toInt()
        }

    }
    Box(modifier = Modifier.fillMaxSize().onGloballyPositioned {
        viewModel.initGame(it.size, string)
    }.background(Color.Blue.copy(alpha = 0.6f))) {
        GameTopBar(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).padding(20.dp),
            time = timer,
            levelName = string
        )
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures()
                    detectTapGestures(
                        onLongPress = {
                            timer += 1
                        }
                    )
                }
//                .pointerInteropFilter { event ->
//                    when (event.action) {
//                        MotionEvent.ACTION_MOVE -> {
//                            viewModel.setMoveX(event.x.toInt())
//                        }
//
//                        else -> Unit
//                    }
//                    true
//                }
        ) {

        }
        Image(
            painter = painterResource(Res.drawable.ultimate),
            contentDescription = null,
            modifier = Modifier.size(40.dp).align(Alignment.BottomEnd).padding(30.dp),
        )
    }
}

@Composable
fun GameTopBar(modifier: Modifier, time: Int, levelName: String, gameProgress: Float = 0.5f) {
    val persAnimation = rememberInfiniteTransition().animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                levelName.toString(),
                color = Color.White,
                fontSize = 40.sp,
                style = getTypography().h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.size(60.dp).paint(painterResource(Res.drawable.game_level))
                    .padding(top = 20.dp)
            )
            Text(
                time.toString(),
                color = Color.White,
                fontSize = 40.sp,
                style = getTypography().h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.size(width = 100.dp, height = 60.dp)
                    .paint(painterResource(Res.drawable.timer_background))
                    .padding(top = 14.dp, start = 20.dp)
            )
            Image(
                painter = painterResource(Res.drawable.pers),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.size(80.dp).graphicsLayer {
                    rotationZ = persAnimation.value * 23
                }
            )
        }
        CustomProgressBar(
            Modifier.fillMaxWidth(),
            gameProgress,
            Res.drawable.game_progress_bar_game, isShowLoading = false
        )
    }
}