package org.example.project.model

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.candy
import firstkmpproject.composeapp.generated.resources.main_backgroud
import org.jetbrains.compose.resources.DrawableResource

data class GameLevelItemModel(
    val background: DrawableResource,
    val droppedImage: DrawableResource,
    val intOffset: IntOffset,
    val size: IntSize
){
    companion object{
        val Zero= GameLevelItemModel(
            background = Res.drawable.main_backgroud,
            droppedImage = Res.drawable.candy,
            intOffset = IntOffset.Zero,
            size = IntSize.Zero
        )
    }
}
