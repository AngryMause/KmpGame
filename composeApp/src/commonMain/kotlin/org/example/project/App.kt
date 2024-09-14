package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import org.example.project.screens.navigation.AppNavigation
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun App() {
    MaterialTheme {
//        var showContent by remember { mutableStateOf(false) }
//        Column(
//            Modifier.fillMaxSize().background(Color.Red.copy(alpha = 0.6f)).padding(20.dp)
//                .background(Color.Green.copy(alpha = 0.6f)),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me!")
//            }
//
//        }

        AppNavigation()

    }
}