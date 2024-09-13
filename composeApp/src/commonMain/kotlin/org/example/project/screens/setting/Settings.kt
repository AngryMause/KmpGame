package org.example.project.screens.setting

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.Vector
import firstkmpproject.composeapp.generated.resources.back_arrow
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.off
import firstkmpproject.composeapp.generated.resources.settings
import firstkmpproject.composeapp.generated.resources.sound_setting_back
import firstkmpproject.composeapp.generated.resources.switch_back_off
import firstkmpproject.composeapp.generated.resources.switch_back_on
import firstkmpproject.composeapp.generated.resources.switch_off
import firstkmpproject.composeapp.generated.resources.switch_on
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingScreen(onBack: () -> Unit) {
    val viewModel = remember { SettingViewModel() }

    var checked by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier.fillMaxSize()
            .paint(
                painter = painterResource(Res.drawable.main_backgroud),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Image(painter = painterResource(Res.drawable.back_arrow),
            contentDescription = "Back",
            modifier = Modifier.align(
                Alignment.TopStart
            ).padding(20.dp).size(40.dp).clickable { onBack() })
        Image(
            painter = painterResource(Res.drawable.settings),
            contentDescription = null,
            modifier = Modifier.align(
                Alignment.TopCenter
            ).padding(top = 20.dp)
        )
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
) {
    val thumbPosition by animateFloatAsState(targetValue = if (checked) 1f else 0f)
    val interactionSource = remember { MutableInteractionSource() }
    val switchBackOn = imageResource(Res.drawable.switch_back_on)
    val switchBackOff = imageResource(Res.drawable.switch_back_off)
    val switchOff = imageResource(Res.drawable.switch_off)
    val switchOn = imageResource(Res.drawable.switch_on)
    val imageOn = imageResource(Res.drawable.Vector)
    val imageOff = imageResource(Res.drawable.off)
    Box(
        modifier = modifier
            .size(width = 120.dp, height = 50.dp)
            .clickable(
                onClick = { onCheckedChange(!checked) },
                interactionSource = interactionSource,
                indication = null
            )
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val backImage = if (checked) switchBackOn else switchBackOff
            val thumbImage = if (checked) switchOn else switchOff
            val onOff = if (checked) imageOn else imageOff
            val onOffOffset = if (!checked) size.width / 2 - 10 else 10
            val animatedElementSize=IntSize(size.width.toInt() /2, (size.height.toInt()) - 20)
            drawImage(
                image = backImage,
                dstSize = IntSize(size.width.toInt(), size.height.toInt())
            )
            drawImage(
                image = onOff,
                dstSize = IntSize(size.width.toInt() /2, (size.height.toInt()) - 20),
                dstOffset = IntOffset(onOffOffset.toInt(), 10)
            )
            val thumbOffset = calculateThumbOffset(
                start = 10f,
                stop = size.width - (animatedElementSize.width-10),
                fraction = thumbPosition
            )
            drawImage(
                image = thumbImage,
                dstOffset = IntOffset(thumbOffset.toInt(), 10),
                dstSize = IntSize(size.width.toInt() /2-20, (size.height.toInt()) - 20)
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