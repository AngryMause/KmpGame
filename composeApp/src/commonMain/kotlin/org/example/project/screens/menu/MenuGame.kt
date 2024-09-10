package org.example.project.screens.menu

import androidx.collection.mutableIntSetOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource

@Composable
fun MenuGame() {
    var onPlanetSelected = remember { mutableIntSetOf(-1) }

    Box(modifier = Modifier.fillMaxSize()) {
        // Menu
    }
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MenuGameContent(modifier: Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        Text("List of planets", color = Color.Red, modifier = Modifier)
        FlowRow(modifier = Modifier.fillMaxWidth().padding(10.dp)) {
//            Image(
//                painter = painterResource(Re),
//                contentDescription = "Planet",
//                modifier = Modifier.size(50.dp)
//            )
        }
    }
}

private val planetList = listOf(
    "Mercury",
    "Venus",
    "Earth",
    "Mars",
    "Jupiter",
    "Saturn",
    "Uranus",
    "Neptune",
    "Pluto"
)
