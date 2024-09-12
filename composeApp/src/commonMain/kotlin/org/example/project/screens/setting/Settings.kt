package org.example.project.screens.setting

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.back_arrow
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.settings
import firstkmpproject.composeapp.generated.resources.sound_setting_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingScreen(onBack: () -> Unit) {
    val viewModel = remember { SettingViewModel() }

    var checked by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxSize()
            .paint(painter = painterResource(Res.drawable.main_backgroud), contentScale = ContentScale.FillBounds)
    ) {
        Image(painter = painterResource(Res.drawable.back_arrow),
            contentDescription = "Back",
            modifier = Modifier.align(
                Alignment.TopStart
            ).padding(20.dp).size(40.dp).clickable { onBack() })
        Image(painter = painterResource(Res.drawable.settings),
            contentDescription = null,
            modifier = Modifier.align(
                Alignment.TopCenter
            ).padding( top = 20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.align(Alignment.Center).padding(20.dp)
                .paint(painter = painterResource(Res.drawable.sound_setting_back)).padding(20.dp)
        ) {
            Text("Sound ${if (checked) "On" else "Off"}")
            Spacer(Modifier.weight(1f))
            GradientSwitch(
                checked = checked,
                onCheckedChange = { checked = it },
                modifier = Modifier.size(width = 51.dp, height = 31.dp)
            )
        }
    }

}

@Composable
fun GradientSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    checkedTrackColor: Brush = Brush.horizontalGradient(
        colors = listOf(
            Color.Blue, Color.Red
        )
    ),
    uncheckedTrackColor: Brush = Brush.horizontalGradient(
        colors = listOf(
            Color.LightGray, Color.Gray
        )
    ),
    thumbColor: Color = Color.White
) {
    val thumbPosition by animateFloatAsState(targetValue = if (checked) 1f else 0f)
    val circleRadius = remember { 13.5.dp }
    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier = modifier
            .size(width = 51.dp, height = 31.dp)
            .clickable(
                onClick = { onCheckedChange(!checked) },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val trackBrush = if (checked) checkedTrackColor else uncheckedTrackColor
            drawRoundRect(
                brush = trackBrush,
                size = Size(size.width, size.height),
                cornerRadius = CornerRadius(x = 18.dp.toPx(), y = 18.dp.toPx())
            )
            val thumbOffset = calculateThumbOffset(
                start = 16.dp.toPx(),
                stop = size.width - 16.dp.toPx(),
                fraction = thumbPosition
            )

            drawCircle(
                color = thumbColor,
                radius = circleRadius.toPx(),
                center = Offset(x = thumbOffset, y = size.height / 2)
            )
        }
    }
}

private fun calculateThumbOffset(
    start: Float,
    stop: Float,
    fraction: Float
): Float = start + (stop - start) * fraction


@Composable
fun Dp.dpToPx() = with(LocalDensity.current) { this@dpToPx.toPx() }


@Composable
fun Int.pxToDp() = with(LocalDensity.current) { this@pxToDp.toDp() }