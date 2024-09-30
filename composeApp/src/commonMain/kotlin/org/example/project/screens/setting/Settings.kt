package org.example.project.screens.setting

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import candypopuniverse.composeapp.generated.resources.Res
import candypopuniverse.composeapp.generated.resources.Vector
import candypopuniverse.composeapp.generated.resources.back_arrow
import candypopuniverse.composeapp.generated.resources.background_list
import candypopuniverse.composeapp.generated.resources.list_image_back
import candypopuniverse.composeapp.generated.resources.lock
import candypopuniverse.composeapp.generated.resources.off
import candypopuniverse.composeapp.generated.resources.settings
import candypopuniverse.composeapp.generated.resources.sound_setting_back
import candypopuniverse.composeapp.generated.resources.sounds
import candypopuniverse.composeapp.generated.resources.switch_back_off
import candypopuniverse.composeapp.generated.resources.switch_back_on
import candypopuniverse.composeapp.generated.resources.switch_off
import candypopuniverse.composeapp.generated.resources.switch_on
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.imageResource
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@OptIn(KoinExperimentalAPI::class)
@Composable
fun SettingScreen(onBack: () -> Unit) {
    val viewModel = koinViewModel<SettingViewModel>()
    val isSoundEnabled = viewModel.isSoundEnabled.collectAsState()
    var checked by remember { mutableStateOf(false) }
    val levelList = viewModel.mainBackGround.collectAsState()
    LaunchedEffect(isSoundEnabled.value) {
        checked = isSoundEnabled.value
    }
    Box(
        modifier = Modifier.fillMaxSize()
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
        Column(
            Modifier.fillMaxWidth().wrapContentSize().align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, bottom = 60.dp)
                    .paint(painter = painterResource(Res.drawable.sound_setting_back))
                    .padding(20.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.sounds),
                    contentDescription = "Sounds",
                    modifier = Modifier.size(width = 80.dp, height = 60.dp)
                )
                Spacer(Modifier.weight(1f))
                SettingSwitch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        viewModel.saveSoundEnabled(it)
                    },
                    modifier = Modifier.size(width = 51.dp, height = 31.dp)
                )
            }
            Backgrounds(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.8f),
                levelList = levelList.value,
                onBackChoisen = { background ->
                    viewModel.saveNewBackGroundImage(background)
                }
            )
        }
    }
}

@Composable
fun Backgrounds(
    modifier: Modifier = Modifier,
    levelList: List<BackgroundsUnlockedModel>,
    onBackChoisen: (Int) -> Unit
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .paint(
                painter = painterResource(Res.drawable.background_list),
                contentScale = ContentScale.FillBounds
            )
    ) {
        Text(
            text = "Backgrounds",
            color = Color.White,
            style = TextStyle(fontSize = 20.sp),
            modifier = Modifier.align(Alignment.TopCenter).padding(12.dp)
        )
        LazyRow(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize().padding(10.dp)
        ) {
            items(levelList.size) { index ->
                val model = levelList[index]
                BackgroundsUnlockedItem(
                    modifier = Modifier.align(Alignment.Center).matchParentSize()
                        .clickable(enabled = levelList.get(index).isUnlocked) { onBackChoisen(index) },
                    model,

                    )
            }
        }
    }
}

@Composable
fun BackgroundsUnlockedItem(
    modifier: Modifier = Modifier,
    model: BackgroundsUnlockedModel,
) {
    Box(
        modifier = modifier.padding(4.dp)
    ) {
        Image(
            painter = painterResource(requireNotNull(model.background)),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.align(Alignment.Center).size(width = 140.dp, height = 250.dp)
                .paint(
                    painter = painterResource(Res.drawable.list_image_back),
                    contentScale = ContentScale.FillBounds
                ).padding(6.dp).clip(RoundedCornerShape(10.dp)),
            colorFilter = if (model.isUnlocked) null else ColorFilter.colorMatrix(ColorMatrix().apply {
                setToSaturation(
                    0f
                )
            })
        )
        if (!model.isUnlocked) {
            Image(
                painter = painterResource(Res.drawable.lock),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(40.dp).align(Alignment.Center),
            )
        }
    }
}


data class BackgroundsUnlockedModel(
    val background: DrawableResource? = null,
    var isUnlocked: Boolean = false
)

@Composable
fun SettingSwitch(
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
            val height = (size.height.toInt()) - 20
            val animatedElementSize = IntSize(size.width.toInt() / 2, height)
            drawImage(
                image = backImage,
                dstSize = IntSize(size.width.toInt(), size.height.toInt())
            )
            drawImage(
                image = onOff,
                dstSize = IntSize(size.width.toInt() / 2, height),
                dstOffset = IntOffset(onOffOffset.toInt(), 10)
            )
            val thumbOffset = calculateThumbOffset(
                start = 10f,
                stop = size.width - (animatedElementSize.width - 16),
                fraction = thumbPosition
            )
            drawImage(
                image = thumbImage,
                dstOffset = IntOffset(thumbOffset.toInt(), 10),
                dstSize = IntSize(size.width.toInt() / 2 - 26, height)
            )
        }
    }
}

private fun calculateThumbOffset(
    start: Float,
    stop: Float,
    fraction: Float
): Float = start + (stop - start) * fraction
