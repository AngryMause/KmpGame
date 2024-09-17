package org.example.project.model

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.candy
import firstkmpproject.composeapp.generated.resources.main_backgroud
import org.jetbrains.compose.resources.DrawableResource

data class GameLevelItemModel(
    val background: DrawableResource? = null,
    val droppedImage: DrawableResource? = null,
    val itemList: List<ItemListModel> = emptyList(),
    val intOffset: IntOffset,
    val size: IntSize = IntSize(100, 100)
) {
    companion object {
        val Zero = GameLevelItemModel(
            background = Res.drawable.main_backgroud,
            droppedImage = Res.drawable.candy,
            intOffset = IntOffset.Zero,
        )
    }
}

data class ItemListModel(
    val drawableResource: DrawableResource,
    val intOffset: IntOffset = IntOffset.Zero,
    val size: IntSize = IntSize(100, 100)
)
