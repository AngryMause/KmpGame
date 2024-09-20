package org.example.project.screens.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.Dispatchers
import org.example.project.data.local.media.audioPlayer
import org.example.project.screens.setting.SettingScreen
import org.example.project.screens.game.GameScreen
import org.example.project.screens.menu.MenuGame
import org.example.project.screens.splash.SplashScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI
import org.lighthousegames.logging.logging

private const val PLANET_NAME = "planetName"
private val log = logging("AppNavigation")

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavigation() {
    val navHost = rememberNavController()
    val viewModel = koinViewModel<AppViewModel>()
    val imageResource = viewModel.mainScreen.collectAsState()
    val player by remember { mutableStateOf(audioPlayer()) }
    DisposableEffect(true) {
        onDispose {
            log.e { "AppNavigation: DisposableEffect" }
            audioPlayer().release()
        }
    }

    LaunchedEffect(true) {
        log.e { "AppNavigation: LaunchedEffect" }
        player.playSound(3)
    }
    NavHost(
        modifier = Modifier.fillMaxSize()
            .paint(
                painter = painterResource(imageResource.value),
                contentScale = ContentScale.FillBounds
            ),
        navController = navHost,
        startDestination = ScreenRoute.MENU.name
    ) {
        // Splash
        composable(ScreenRoute.SPLASH.name) {
            SplashScreen(openMenu = {
                navHost.navigate(ScreenRoute.MENU.name)
                {
                    popUpTo(ScreenRoute.SPLASH.name) {
                        inclusive = true
                    }
                }
            })
        }
        // Menu
        composable(ScreenRoute.MENU.name) { backStackEntry ->
            MenuGame(onGameStar = { planetName ->
                navHost.navigate("${ScreenRoute.GAME.name}/$planetName")
            }, onSettingsOpen = { navHost.navigate(ScreenRoute.SETTING.name) })
        }
        // Game
        composable("${ScreenRoute.GAME.name}/{$PLANET_NAME}") { backStackEntry ->
            val str = backStackEntry.arguments?.getString(PLANET_NAME)
            str?.let {
                GameScreen(
                    onBack = {
                        navHost.popBackStack()
                    }, string = it
                )
            }
        }
        // Setting
        composable(ScreenRoute.SETTING.name) {
            SettingScreen(onBack = {
                navHost.popBackStack()
            })
        }
    }
}