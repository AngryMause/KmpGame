package org.example.project.screens.elements

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.loading
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun LevelBoxes() {
}

@Composable
fun CustomProgressBar(modifier: Modifier = Modifier, progress: Float, background: DrawableResource) {
    Box(
        modifier = modifier
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LinearProgressIndicator(
                progress = progress,
                color = Color.Yellow,
                modifier = Modifier.fillMaxWidth().paint(
                    painterResource(background),
                    contentScale = ContentScale.FillBounds
                ).padding(12.dp)
            )
            Image(painter = painterResource(Res.drawable.loading), contentDescription = null)
        }
    }
}