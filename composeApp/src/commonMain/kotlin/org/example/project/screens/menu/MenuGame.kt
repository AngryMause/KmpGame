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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.level_locked
import firstkmpproject.composeapp.generated.resources.pers
import firstkmpproject.composeapp.generated.resources.settings_button
import org.example.project.screens.elements.LevelBox
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.lighthousegames.logging.logging

@OptIn(KoinExperimentalAPI::class)
@Composable
fun MenuGame(onSettingsOpen: () -> Unit, onGameStar: (String) -> Unit) {
    val log = logging("SplashScreen")
    val viewModel = koinViewModel<MenuGameViewModel>()
    val gameLevelList = viewModel.gameLevelList.collectAsState()
    LaunchedEffect(null) {
        log.e { "MenuGame: LaunchedEffect" }
        viewModel.getUnlockedLevelList()
    }
    Box {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painterResource(Res.drawable.pers),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)
                    .align(Alignment.CenterHorizontally)
            )
            LazyRow(modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f)) {
                items(gameLevelList.value.size) { index ->
                    val levelList = gameLevelList.value[index]
                    log.e { "MenuGame: $levelList" }
                    if (levelList.isLevelUnlocked) {
                        LevelBox(
                            modifier = Modifier.padding(10.dp),
                            gameLevelModel = levelList,
                            onClick = {
                                onGameStar(gameLevelList.value[index].levelName.levelName)
                            }
                        )
                    } else {
                        Image(
                            painter = painterResource(Res.drawable.level_locked),
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
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
