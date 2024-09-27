package org.example.project.model

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.main_backgroud
import org.jetbrains.compose.resources.DrawableResource

data class GameLevelItemModel(
    val background: DrawableResource? = null,
    val itemList: List<SingleDroppedItemModel> = emptyList(),
    val singleDroppedItemModel: SingleDroppedItemModel? = null,
) {
    companion object {
        val Zero = GameLevelItemModel(
            background = Res.drawable.main_backgroud,
        )
    }
}

data class SingleDroppedItemModel(
    val drawableResource: DrawableResource? = null,
    val alpha: Float = 1f,
    val intOffset: IntOffset = IntOffset.Zero,
    var size: IntSize = IntSize(100, 100)
)

