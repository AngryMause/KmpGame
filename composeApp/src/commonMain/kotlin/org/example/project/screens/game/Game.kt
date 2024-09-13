package org.example.project.screens.game

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.game_progress_bar_game
import org.example.project.screens.elements.CustomProgressBar

@Composable
fun GameScreen(onBack: () -> Unit, string: String) {
    Box(modifier = Modifier.fillMaxSize().background(Color.Blue.copy(alpha = 0.6f))) {

        CustomProgressBar(
            Modifier.fillMaxWidth().padding(top = 30.dp),
            0.5f,
            Res.drawable.game_progress_bar_game
        )

        Icon(
            Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = null,
            modifier = Modifier.padding(20.dp).clickable {
                onBack()
            })
        Text(string, color = Color.Black, fontSize = 35.sp, modifier = Modifier.align(Alignment.Center))
    }


}