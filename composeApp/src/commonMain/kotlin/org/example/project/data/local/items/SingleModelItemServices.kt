package org.example.project.data.local.items

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.example.project.data.local.OnTapEventModel
import org.example.project.data.local.repository.DROP_DOWN_UPDATE_STEP
import org.example.project.data.local.repository.ITEM_EXPLOSION_SIZE
import org.example.project.data.local.repository.MOVE_UP_STEP
import org.example.project.data.local.repository.SCREEN_START_POSITION
import org.example.project.data.local.repository.SIZE_UPDATE_STEP
import org.example.project.data.local.repository.TOB_BAR_PROGRESS_STEP
import org.example.project.model.SingleDroppedItemModel
import org.lighthousegames.logging.logging

private val log = logging("SingleModelItemServices")

// in future will be used for single item
class SingleModelItemServices {
//
//    private val _singleModel = MutableStateFlow(SingleDroppedItemModel())
//    val singleModel = _singleModel.asStateFlow()
//
//    suspend fun updateItemByLongPress(
//        singleDroppedItemModel: SingleDroppedItemModel,
//        tapOffset: Offset
//    ) {
//        val itemOffset = singleDroppedItemModel.intOffset
//        val itemSize = singleDroppedItemModel.size
//        if (isTapOnItem(
//                itemSize = itemSize,
//                itemOffset = itemOffset,
//                tapOffset = tapOffset,
//            )
//        ) {
//            log.e { "singleDroppedItemModel.size.width ${singleDroppedItemModel.size.width}" }
//            updateItemSize(singleDroppedItemModel)
//        } else {
//            moveItemUpOrDown(singleDroppedItemModel)
//        }
//    }
//
//    private suspend fun moveItemUpOrDown(
//        singleDroppedItemModel: SingleDroppedItemModel,
//        _tapOffset: StateFlow<OnTapEventModel>
//    ) {
//        val maxItemSizeToMoveUp = singleDroppedItemModel.size.width + ITEM_MOVE_UP_SIZE
//        if ((!_tapOffset.value.isLongPress || !_tapOffset.value.isOnTap) && maxItemSizeToMoveUp >= 170 && maxItemSizeToMoveUp <= ITEM_EXPLOSION_SIZE) {
//            moveUp(singleDroppedItemModel)
//            log.e { " if maxItemSizeToMoveUp $maxItemSizeToMoveUp" }
//        } else {
//            updateItemY(singleDroppedItemModel)
//        }
//    }
//
//    private fun isTapOnItem(
//        itemSize: IntSize,
//        itemOffset: IntOffset,
//        tapOffset: Offset,
//        _tapOffset: StateFlow<OnTapEventModel>
//    ): Boolean {
//        return tapOffset.x >= itemOffset.x &&
//                tapOffset.x <= itemOffset.x + itemSize.width &&
//                tapOffset.y >= itemOffset.y &&
//                tapOffset.y <= itemOffset.y + itemSize.height &&
//                _tapOffset.value.isOnTap
//    }
//
//
//    private suspend fun updateItemY(singleDroppedItemModel: SingleDroppedItemModel) {
//        val singleDroppedItem = singleDroppedItemModel.copy(
//            intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y + DROP_DOWN_UPDATE_STEP)
//        )
//        _singleModel.emit(_singleModel.value.copy(singleDroppedItem))
//    }
//
//    private suspend fun updateItemSize(singleDroppedItemModel: SingleDroppedItemModel) {
//        if (_tapOffset.value.isLongPress && singleDroppedItemModel.size.width < ITEM_EXPLOSION_SIZE) {
//            val singleDroppedItem = singleDroppedItemModel.copy(
//                size = IntSize(
//                    width = singleDroppedItemModel.size.width + SIZE_UPDATE_STEP,
//                    height = singleDroppedItemModel.size.height + SIZE_UPDATE_STEP
//                )
//            )
//            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
//        } else if (_tapOffset.value.isLongPress && singleDroppedItemModel.size.width >= ITEM_EXPLOSION_SIZE) {
//            resetPosition(singleDroppedItemModel)
//        }
//    }
//
//
//    private suspend fun moveUp(singleDroppedItemModel: SingleDroppedItemModel): Boolean {
//        return if (singleDroppedItemModel.intOffset.y >= SCREEN_START_POSITION) {
//            val singleDroppedItem = singleDroppedItemModel.copy(
//                alpha = 0.3f,
//                intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y - MOVE_UP_STEP)
//            )
//            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
//            true
//        } else {
//            updateTopBarProgress()
//            resetPosition(singleDroppedItemModel)
//            false
//        }
//    }
}