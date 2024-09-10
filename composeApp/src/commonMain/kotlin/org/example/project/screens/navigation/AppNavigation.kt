package org.example.project.screens.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.example.project.screens.SettingScreen
import org.example.project.screens.game.GameScreen
import org.example.project.screens.menu.MenuGame
import org.example.project.screens.splash.SplashScreen

@Composable
fun AppNavigation() {
    val navHost = rememberNavController()
    NavHost(
        navController = navHost,
        startDestination = ScreenRoute.SPLASH.name
    ) {
        // Splash
        composable(ScreenRoute.SPLASH.name) {
            SplashScreen()
        }
        // Menu
        composable(ScreenRoute.MENU.name) {
            MenuGame()
        }
        // Game
        composable(ScreenRoute.GAME.name) {
            GameScreen(onBack = {
                navHost.popBackStack()
            })
        }
        // Setting
        composable(ScreenRoute.SETTING.name) {
            SettingScreen(onBack = {
                navHost.popBackStack()
            })
        }
    }
}