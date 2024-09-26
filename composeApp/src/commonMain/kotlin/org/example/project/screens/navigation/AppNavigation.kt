package org.example.project.screens.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.multiplatform.lifecycle.LifecycleEvent
import com.multiplatform.lifecycle.LifecycleObserver
import com.multiplatform.lifecycle.LocalLifecycleTracker
import org.example.project.screens.game.GameScreen
import org.example.project.screens.menu.MenuGame
import org.example.project.screens.setting.SettingScreen
import org.example.project.screens.splash.SplashScreen
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

private const val LEVEL_KEY_NAME = "level_key_name"

@Composable
private fun LifecycleTest(lifeCycle: (LifecycleEvent) -> Unit) {
    val lifecycleTracker = LocalLifecycleTracker.current
    DisposableEffect(Unit) {
        val listener =
            object : LifecycleObserver {
                override fun onEvent(event: LifecycleEvent) {
                    lifeCycle(event)
                }
            }
        lifecycleTracker.addObserver(listener)
        onDispose {
            lifecycleTracker.removeObserver(listener)
        }
    }
}

@OptIn(KoinExperimentalAPI::class)
@Composable
fun AppNavigation() {
    val navHost = rememberNavController()
    val viewModel = koinViewModel<AppViewModel>()
    val imageResource = viewModel.mainScreen.collectAsState()

    LifecycleTest { lifecycleEvent ->
        when (lifecycleEvent) {
            LifecycleEvent.OnPauseEvent -> {
                viewModel.pauseSong()
            }

            LifecycleEvent.OnResumeEvent -> {
                viewModel.playSound()
            }

            else -> Unit
        }
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
        composable("${ScreenRoute.GAME.name}/{$LEVEL_KEY_NAME}") { backStackEntry ->
            val str = backStackEntry.arguments?.getString(LEVEL_KEY_NAME)
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