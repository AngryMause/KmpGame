package org.example.project.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.pers
import firstkmpproject.composeapp.generated.resources.settings_button
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.LevelProgressState
import org.example.project.model.MenuLevelModel
import org.example.project.screens.elements.LevelBox
import org.jetbrains.compose.resources.painterResource
import org.lighthousegames.logging.logging

@Composable
fun MenuGame(onSettingsOpen: () -> Unit, onGameStar: (String) -> Unit) {
    val log = logging("SplashScreen")
    Box {
        Column(
            modifier = Modifier.fillMaxSize().paint(
                painterResource(Res.drawable.main_backgroud),
                contentScale = ContentScale.FillBounds
            )
        ) {
            Image(
                painterResource(Res.drawable.pers),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                    .align(Alignment.CenterHorizontally)
            )
            LazyRow(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) {
                items(gemeLevelList.size) { index ->
                    LevelBox(
                        modifier = Modifier.padding(10.dp),
                        gemeLevelList[index],
                        onClick = { onGameStar(gemeLevelList[index].levelName.levelName) }
                    )
                }
            }
        }
        Image(
            painter = painterResource(Res.drawable.settings_button),
            contentDescription = "Settings",
            modifier = Modifier.padding(30.dp).size(55.dp).align(Alignment.TopEnd)
                .clickable { onSettingsOpen() })
    }
}

private val gemeLevelList = listOf(
    MenuLevelModel(
        GameLevelStatus.LEVEL_0NE,
        isLevelUnlocked = true,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_TWO,
        isLevelUnlocked = true,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_THREE,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_FOUR,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_FIVE,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_SIX,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_SEVEN,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_EIGHT,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_NINE,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
)


