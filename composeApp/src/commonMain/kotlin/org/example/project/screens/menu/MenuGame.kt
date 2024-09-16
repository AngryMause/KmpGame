package org.example.project.screens.menu

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.pers
import firstkmpproject.composeapp.generated.resources.progressbar_background
import firstkmpproject.composeapp.generated.resources.settings_button
import firstkmpproject.composeapp.generated.resources.star
import org.example.project.data.LevelProgress
import org.example.project.model.GameLevelModel
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
                items(planetList.size) { index ->
                    LevelBox(
                        modifier = Modifier.padding(10.dp),
                        gemeLevelList[index],
                        onClick = { onGameStar(gemeLevelList[index].levelName) }
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MenuGameContent(modifier: Modifier, name: String, onSelect: (String) -> Unit) {
    Column(
        modifier = modifier.background(Color.Blue.copy(0.6f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
//        Image(painterResource(Res.drawable), contentDescription = "Star", modifier = Modifier.size(50.dp))
        Text(name, color = Color.Red, modifier = Modifier)
        FlowRow(maxItemsInEachRow = 3, modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(Res.drawable.progressbar_background),
                contentDescription = "Planet",
                modifier = Modifier.size(20.dp).clickable { onSelect(name) }
            )
        }
    }
}

private val gemeLevelList = listOf(
    GameLevelModel(" 1", isLevelUnlocked = true, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 2", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 3", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 4", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 5", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 6", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 7", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 8", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
    GameLevelModel(" 9", isLevelUnlocked = false, levelProgress = LevelProgress.NOT_COMPLETED),
)

private val planetList = listOf(
    "Mercury",
    "Venus",
    "Earth",
    "Mars",
    "Jupiter",
    "Saturn",
    "Uranus",
    "Neptune",
    "Pluto"
)

