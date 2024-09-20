package org.example.project.screens.game

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.game_level
import firstkmpproject.composeapp.generated.resources.game_progress_bar_game
import firstkmpproject.composeapp.generated.resources.pers
import firstkmpproject.composeapp.generated.resources.timer_background
import firstkmpproject.composeapp.generated.resources.ultimate
import org.example.project.data.local.OnTapEventModel
import org.example.project.data.local.state.GameStatus
import org.example.project.model.GameLevelItemModel
import org.example.project.screens.elements.CustomProgressBar
import org.example.project.screens.elements.GameOverAlert
import org.example.project.screens.elements.LevelCompleteAlert
import org.example.project.screens.elements.getTypography
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.lighthousegames.logging.logging

@OptIn(KoinExperimentalAPI::class)
@Composable
fun GameScreen(onBack: () -> Unit, string: String) {
    val log = logging("GameScreen")
    val viewModel = koinViewModel<GameViewM0del>()
    val gameStatus = viewModel.gameStatus.collectAsState()
    val gameLevel = viewModel.gameLevel.collectAsState()
    val gameTopBarModel = viewModel.gameTopBarModel.collectAsState()
    val isUltimatePressed = viewModel.isUltimatePressed.collectAsState()
    Box(
        modifier = Modifier.fillMaxSize().onGloballyPositioned {
            viewModel.initGame(it.size, string)
        }.paint(
            painterResource(requireNotNull(gameLevel.value.background)),
            contentScale = ContentScale.FillBounds
        )
    ) {
        GameTopBar(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).padding(20.dp),
            time = gameTopBarModel.value.levelTime,
            levelName = gameTopBarModel.value.levelName,
            gameProgress = gameTopBarModel.value.levelProgress
        )
        when (gameStatus.value) {
            GameStatus.Loading -> {
                Icon(
                    Icons.Filled.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(40.dp).align(Alignment.Center)
                        .clickable {
                            viewModel.startGame()
                            viewModel.setGameStatus(GameStatus.Playing)
                        },
                )
            }

            GameStatus.Playing -> {
                GameArea(
                    canvasModifier = Modifier
                        .fillMaxSize()
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = { onPressOffset ->
                                    viewModel.setTapOffset(
                                        OnTapEventModel(
                                            isOnTap = true,
                                            offset = onPressOffset
                                        )
                                    )
                                    detectDragGesturesAfterLongPress(
                                        onDrag = { tt, dragAmount ->

                                        },
                                        onDragEnd = {
                                            log.e { "onDragEnd" }
                                            viewModel.setTapOffset(
                                                OnTapEventModel(
                                                    isLongPress = false,
                                                    offset = Offset.Zero
                                                )
                                            )
                                        }
                                    )
                                },

                                onLongPress = { ofset ->
                                    log.e { "onLongPress" }
                                    viewModel.setTapOffset(
                                        OnTapEventModel(
                                            isLongPress = true,
                                            offset = ofset
                                        )
                                    )
                                }
                            )
                        }, gameLevel = gameLevel.value
                )
                Image(
                    painter = painterResource(Res.drawable.ultimate),
                    contentDescription = null,
                    modifier = Modifier.padding(30.dp).size(40.dp).align(Alignment.BottomEnd)
                        .graphicsLayer {
                            alpha =
                                if (!isUltimatePressed.value.isFinished || isUltimatePressed.value.progress <= 0.3f) 0.3f else isUltimatePressed.value.progress
                        }
                        .clickable(
                            enabled = isUltimatePressed.value.isFinished && isUltimatePressed.value.progress == 1f,
                        ) {
                            viewModel.pauseDropped()
                        },
                )
            }

            is GameStatus.GameOver -> {
                GameOverAlert(
                    modifier = Modifier.fillMaxSize(),
                    onClick = {
                        onBack()
                    }, reload = {
                        viewModel.restartGame()
                    }
                )
            }

            is GameStatus.LevelCompleted -> {
                viewModel.stopGame()
                LevelCompleteAlert(
                    modifier = Modifier.fillMaxSize(),
                    levelProgress = (gameStatus.value as GameStatus.LevelCompleted).level,
                    onClick = {
                        onBack()
                    }
                )
            }
        }
    }
}

@Composable
fun GameArea(canvasModifier: Modifier, gameLevel: GameLevelItemModel) {
    val imade = if (gameLevel.singleDroppedItemModel != null) {
        imageResource(gameLevel.singleDroppedItemModel.drawableResource)
    } else {
        null
    }
    val list: List<DroppedItemList> = if (gameLevel.itemList.isNotEmpty()) {
        gameLevel.itemList.map {
            DroppedItemList(
                drawableResource = imageResource(it.drawableResource),
                intOffset = it.intOffset,
                size = IntSize(it.size.width, it.size.height)
            )
        }
    } else {
        emptyList()
    }
    Canvas(
        modifier = canvasModifier
    ) {
        when {
            list.isNotEmpty() -> {
                list.forEach {
                    drawImage(
                        image = it.drawableResource,
                        dstOffset = it.intOffset,
                        dstSize = it.size
                    )
                }
            }

            imade != null -> {
                drawImage(
                    image = imade,
                    alpha = gameLevel.singleDroppedItemModel!!.alpha,
                    dstOffset = gameLevel.singleDroppedItemModel.intOffset,
                    dstSize = gameLevel.singleDroppedItemModel.size
                )
            }
        }
    }
}

data class DroppedItemList(
    val drawableResource: ImageBitmap,
    val intOffset: IntOffset = IntOffset.Zero,
    val size: IntSize = IntSize(100, 100)
)


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
