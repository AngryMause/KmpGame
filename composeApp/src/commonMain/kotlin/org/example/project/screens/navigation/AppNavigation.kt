package org.example.project.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.screens.setting.SettingScreen
import org.example.project.screens.game.GameScreen
import org.example.project.screens.menu.MenuGame
import org.example.project.screens.splash.SplashScreen

private const val PLANET_NAME="planetName"
@Composable
fun AppNavigation() {
    val navHost = rememberNavController()
    NavHost(
        navController = navHost,
        startDestination = ScreenRoute.SPLASH.name
    ) {
        // Splash
        composable(ScreenRoute.SPLASH.name) {
            SplashScreen(openMenu = {
                navHost.navigate(ScreenRoute.MENU.name)
            })
        }
        // Menu
        composable(ScreenRoute.MENU.name) { backStackEntry->
            MenuGame(onGameStar = {planetName->
                navHost.navigate("${ScreenRoute.GAME.name}/$planetName")
            }, onSettingsOpen = { navHost.navigate(ScreenRoute.SETTING.name) })
        }
        // Game
        composable("${ScreenRoute.GAME.name}/{$PLANET_NAME}") { backStackEntry->
            val str= backStackEntry.arguments?.getString(PLANET_NAME)
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