package org.example.project.screens.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import candypopuniverse.composeapp.generated.resources.Res
import candypopuniverse.composeapp.generated.resources.game_over
import candypopuniverse.composeapp.generated.resources.home_button
import candypopuniverse.composeapp.generated.resources.jomhuria_regular
import candypopuniverse.composeapp.generated.resources.level_complete
import candypopuniverse.composeapp.generated.resources.loading
import candypopuniverse.composeapp.generated.resources.lose_frame
import candypopuniverse.composeapp.generated.resources.menu_level
import candypopuniverse.composeapp.generated.resources.not_completed_level
import candypopuniverse.composeapp.generated.resources.reload_game
import candypopuniverse.composeapp.generated.resources.shine_background
import candypopuniverse.composeapp.generated.resources.star1
import candypopuniverse.composeapp.generated.resources.star2
import candypopuniverse.composeapp.generated.resources.star3
import candypopuniverse.composeapp.generated.resources.win_frame
import org.example.project.data.local.state.LevelProgressState
import org.example.project.model.MenuLevelModel
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LevelCompleteAlert(modifier: Modifier, levelProgress: LevelProgressState, onClick: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize().background(Color.Unspecified.copy(alpha = 0.6f))
    ) {
        Image(
            painter = painterResource(Res.drawable.shine_background),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.6f).align(Alignment.TopCenter)
        )
        Column(
            modifier = Modifier.paint(
                painter = painterResource(Res.drawable.win_frame),
            ).padding(20.dp).align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(Res.drawable.level_complete),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.25f).padding(top = 50.dp)
            )
            StarProgressRow(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).padding(5.dp),
                levelProgress = levelProgress
            )
            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Image(
                painter = painterResource(Res.drawable.home_button),
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .clip(RoundedCornerShape(20.dp)).size(80.dp).clickable() { onClick() },
                contentDescription = null
            )
        }
    }
}

@Composable
fun LevelBox(
    modifier: Modifier, gameLevelModel: MenuLevelModel, onClick: () -> Unit
) {
    Box(modifier = modifier.paint(
        painter = painterResource( Res.drawable.menu_level ),
        contentScale = ContentScale.FillBounds
    ).clickable(enabled = gameLevelModel.isLevelUnlocked) { onClick() }) {
        if (gameLevelModel.isLevelUnlocked) {
            Text(
                text = gameLevelModel.levelName.levelName,
                modifier = Modifier.align(Alignment.Center).padding(top = 10.dp),
                color = Color.White,
                fontSize = 38.sp,
                style = getTypography().h1
            )
            Image(
                painterResource(getStarImage(gameLevelModel.levelProgress)),
                contentDescription = null,
                modifier = Modifier.align(Alignment.BottomCenter).fillMaxWidth(0.8f)
                    .fillMaxHeight(0.2f).padding(bottom = 10.dp)
            )
        }
    }
}

@Composable
fun getTypography(): Typography {
    val font = org.jetbrains.compose.resources.Font(
        Res.font.jomhuria_regular, weight = FontWeight.Bold, style = FontStyle.Normal
    )

    val jomhuria_regular = FontFamily(
        font
    )
    return Typography(
        h1 = TextStyle(
            fontFamily = jomhuria_regular,
            fontSize = 38.sp,
            fontWeight = FontWeight.Bold,
        ), body1 = TextStyle(
            fontFamily = jomhuria_regular,
            fontSize = 14.sp,
            fontWeight = FontWeight.Normal,
        )
    )
}

@Composable
fun GameOverAlert(modifier: Modifier, onClick: () -> Unit, reload: () -> Unit) {
    Box(
        modifier = modifier.fillMaxSize().background(Color.Unspecified.copy(alpha = 0.6f))
    ) {
        Column(
            modifier = Modifier.paint(
                painter = painterResource(Res.drawable.lose_frame),
            ).padding(20.dp).align(Alignment.Center)
        ) {
            Image(
                painter = painterResource(Res.drawable.game_over),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.25f).padding(top = 50.dp)

            )
            Image(
                painterResource(getStarImage(LevelProgressState.NOT_COMPLETED)),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.2f).padding(5.dp)
            )

            Spacer(modifier = Modifier.fillMaxHeight(0.1f))
            Row(
                horizontalArrangement = Arrangement.spacedBy(30.dp),
                modifier = Modifier.wrapContentSize(Alignment.Center)
                    .align(Alignment.CenterHorizontally)
            ) {
                Image(
                    painter = painterResource(Res.drawable.home_button),
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(20.dp))
                        .clickable() { onClick() },
                    contentDescription = null
                )
                Image(
                    painter = painterResource(Res.drawable.reload_game),
                    modifier = Modifier.size(80.dp).clip(RoundedCornerShape(20.dp))
                        .clickable() { reload() },
                    contentDescription = null
                )
            }
        }
    }
}

@Composable
fun StarProgressRow(modifier: Modifier, levelProgress: LevelProgressState) {
    Image(
        painterResource(getStarImage(levelProgress)),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = modifier
    )
}

private fun getStarImage(levelProgress: LevelProgressState): DrawableResource {
    return when (levelProgress) {
        LevelProgressState.ONE_STAR -> Res.drawable.star1
        LevelProgressState.TWO_STARS -> Res.drawable.star2
        LevelProgressState.THREE_STARS -> Res.drawable.star3
        LevelProgressState.NOT_COMPLETED -> Res.drawable.not_completed_level
    }
}

@Composable
fun CustomProgressBar(
    modifier: Modifier = Modifier,
    progress: Float,
    background: DrawableResource,
    isShowLoading: Boolean = true
) {
    Box(
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LinearProgressIndicator(
                progress = progress, color = Color.Yellow, modifier = Modifier.fillMaxWidth().paint(
                    painterResource(background), contentScale = ContentScale.FillBounds
                ).padding(12.dp)
            )
            if (isShowLoading) Image(
                painter = painterResource(Res.drawable.loading),
                contentDescription = null
            )
        }
    }
}