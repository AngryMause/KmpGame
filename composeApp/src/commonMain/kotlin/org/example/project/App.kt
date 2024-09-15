package org.example.project

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import org.example.project.data.LevelProgress
import org.example.project.model.GameLevelModel
import org.example.project.screens.elements.GameOverAlert
import org.example.project.screens.elements.LevelBox
import org.example.project.screens.elements.LevelCompleteAlert

@Composable
fun App() {
    MaterialTheme {
//        LevelCompleteAlert(
//            Modifier.wrapContentSize(Alignment.Center),
//            0,
//            onClick = {})
        LevelBox(
            modifier = Modifier.fillMaxSize(0.3f),
            gameLevelModel = GameLevelModel("1", true, LevelProgress.ONE_STAR), onClick = {}
        )
        //        GameOverAlert(Modifier.fillMaxSize(), onClick = {}, reload = {}, )
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

//        AppNavigation()

    }
}


//expect fun getFont(
//    name: String,
//    res: String,
//    weight: FontWeight,
//    style: FontStyle
//): Any