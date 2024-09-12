package org.example.project.screens.menu

import androidx.collection.mutableIntSetOf
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.star
import org.jetbrains.compose.resources.painterResource
import org.lighthousegames.logging.logging

@Composable
fun MenuGame(onSettingsOpen: () -> Unit, onGameStar: (String) -> Unit) {
    val log = logging("SplashScreen")
    Box {
        Column(modifier = Modifier.fillMaxSize()) {
            planetList.forEachIndexed { index, name ->
                MenuGameContent(modifier = Modifier.wrapContentSize(), name = name, onSelect = {
                    onGameStar(it)
                })
            }

//
        }
        Icon(
            Icons.Default.Settings,
            contentDescription = "Settings",
            modifier = Modifier.padding(30.dp).size(55.dp).align(Alignment.BottomStart)
                .clickable { onSettingsOpen() })
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MenuGameContent(modifier: Modifier, name: String, onSelect: (String) -> Unit) {
    Column(
        modifier = modifier.background(Color.Blue.copy(0.6f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically)
    ) {
        Text(name, color = Color.Red, modifier = Modifier)
        FlowRow(maxItemsInEachRow = 3, modifier = Modifier.padding(10.dp)) {
            Image(
                painter = painterResource(Res.drawable.star),
                contentDescription = "Planet",
                modifier = Modifier.size(20.dp).clickable { onSelect(name) }
            )
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

