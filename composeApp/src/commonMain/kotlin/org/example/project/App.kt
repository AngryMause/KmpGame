package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.project.screens.navigation.AppNavigation

@Composable
fun App(
) {
    MaterialTheme {
        AppNavigation()
    }
}


