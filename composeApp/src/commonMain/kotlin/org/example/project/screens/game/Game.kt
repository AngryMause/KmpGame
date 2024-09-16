package org.example.project.screens.game

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.game_level
import firstkmpproject.composeapp.generated.resources.game_progress_bar_game
import firstkmpproject.composeapp.generated.resources.pers
import firstkmpproject.composeapp.generated.resources.timer_background
import firstkmpproject.composeapp.generated.resources.ultimate
import org.example.project.screens.elements.CustomProgressBar
import org.example.project.screens.elements.getTypography
import org.jetbrains.compose.resources.painterResource

@Composable
fun GameScreen(onBack: () -> Unit, string: String = "1") {
    var timer by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize().background(Color.Blue.copy(alpha = 0.6f))) {
        GameTopBar(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.3f).padding(20.dp),
            time = timer,
            levelName = string
        )
        Image(
            painter = painterResource(Res.drawable.ultimate),
            contentDescription = null,
            modifier = Modifier.size(40.dp).align(Alignment.BottomEnd).padding(30.dp),
        )
    }
}

@Composable
fun GameTopBar(modifier: Modifier, time: Int, levelName: String, gameProgress: Float = 0.5f) {
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
                modifier = Modifier.paint(painterResource(Res.drawable.game_level))
                    .padding(top = 20.dp, end = 10.dp)
            )
            Text(
                time.toString(),
                color = Color.White,
                fontSize = 40.sp,
                style = getTypography().h1,
                textAlign = TextAlign.Center,
                modifier = Modifier.paint(painterResource(Res.drawable.timer_background))
                    .padding(top = 20.dp, start = 10.dp)
            )
            Image(
                painter = painterResource(Res.drawable.pers),
                contentScale = ContentScale.FillBounds,
                contentDescription = null,
                modifier = Modifier.padding(10.dp)
            )
        }
        CustomProgressBar(
            Modifier.fillMaxWidth(),
            gameProgress,
            Res.drawable.game_progress_bar_game, isShowLoading = false
        )
    }
}