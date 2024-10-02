package org.example.project.data.local.repository

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.example.project.data.local.OnTapEvent
import org.example.project.data.local.OnTapEventModel
import org.example.project.data.local.ProgressCountDownTimer
import org.example.project.data.local.items.ItemLevelCreateManager
import org.example.project.data.local.state.GameStatus
import org.example.project.data.local.state.LevelProgressState
import org.example.project.model.GameLevelItemModel
import org.example.project.model.GameTopBarModel
import org.example.project.model.SingleDroppedItemModel
import kotlin.random.Random

private const val GAME_TIME = 40
const val SIZE = 100
const val SCREEN_START_POSITION = 400
const val TOB_BAR_PROGRESS_STEP = 0.1f
const val DROP_DOWN_UPDATE_STEP = 20
const val SIZE_UPDATE_STEP = 4
const val MOVE_UP_STEP = 70
const val ITEM_EXPLOSION_SIZE = 320
const val MIN_ITEM_SIZE_TO_MOVE_UP = 280
const val MINUS = 30

class GameRepository(
    private val progressCountDownTimer: ProgressCountDownTimer,
    private val onTapEvent: OnTapEvent,
    private val itemCreateManager: ItemLevelCreateManager,
) {
    private val _gameStatus = MutableStateFlow<GameStatus>(GameStatus.Loading)
    val gameStatus = _gameStatus.asStateFlow()
    private val _gameLevel = MutableStateFlow(GameLevelItemModel.Zero)
    val gameLevel = _gameLevel.asStateFlow()
    private val _gameTopBarModel = MutableStateFlow(GameTopBarModel("", 0f, 0))
    val gameTopBarModel = _gameTopBarModel.asStateFlow()
    private val _tapOffset = onTapEvent.onTapEvent
    private var screenSize = IntSize.Zero
    private var reloadGameItem: GameLevelItemModel = GameLevelItemModel.Zero
    private var reloadToolbar: GameTopBarModel = GameTopBarModel("", 0f, 0)
    val isUltimatePressed = progressCountDownTimer.timer
    suspend fun initGame(screenSize: IntSize, gameLevel: String) {
        if (gameStatus.value == GameStatus.Loading) {
            this.screenSize = screenSize
            val gameLevelItemModel = itemCreateManager.getGameLevel(gameLevel, screenSize)
            reloadGameItem = gameLevelItemModel
            _gameLevel.emit(gameLevelItemModel)
            setLevelItem(gameLevel)
        }
    }

    fun setTapOffset(onTapEventModel: OnTapEventModel) {
        onTapEvent.setOnTapEvent(onTapEventModel)
    }

    suspend fun setUltimatePressed() {
        progressCountDownTimer.startTimer()
    }

    suspend fun setGameStatus(status: GameStatus) {
        _gameStatus.emit(status)
    }

    private suspend fun setLevelItem(
        level: String,
        time: Int = GAME_TIME,
    ) {
        reloadToolbar = GameTopBarModel(level, levelTime = time)
        _gameLevel.emit(
            reloadGameItem
        )
        _gameTopBarModel.emit(reloadToolbar)
    }

    suspend fun updateGame() {
        coroutineScope {
            launch(Dispatchers.Default) {
                while (gameTopBarModel.value.levelTime >= 0) {
                    checkGameStatus()
                    if (gameTopBarModel.value.levelTime == 0) {
                        break
                    }
                    updateItemDroppedInList()
                    updateSingleDroppedItem()
                }
            }
            launch(Dispatchers.Default) {
                updateGameTimer()
            }
        }
    }

    private suspend fun checkGameStatus() {
        when {
            gameTopBarModel.value.levelTime == 0 && gameTopBarModel.value.levelProgress <= 0.3f -> {
                setGameStatus(
                    GameStatus.LevelCompleted(LevelProgressState.NOT_COMPLETED)
                )
                _gameStatus.emit(GameStatus.GameOver)
            }

            gameTopBarModel.value.levelTime == 0 && gameTopBarModel.value.levelProgress >= 0.3f && gameTopBarModel.value.levelProgress <= 0.6f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.ONE_STAR)

            )

            gameTopBarModel.value.levelTime == 0 && gameTopBarModel.value.levelProgress >= 0.6f && gameTopBarModel.value.levelProgress <= 0.8f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.TWO_STARS)
            )

            gameTopBarModel.value.levelTime == 0 && gameTopBarModel.value.levelProgress >= 0.9f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.THREE_STARS)
            )

            gameTopBarModel.value.levelTime >= 0 && gameTopBarModel.value.levelProgress >= 1f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.THREE_STARS)
            )

            else -> Unit
        }
    }

    private suspend fun updateGameTimer() {
        resetGame()
        while (gameTopBarModel.value.levelTime >= 0) {
            delay(1000)
            val updatedTime = gameTopBarModel.value.levelTime - 1
            _gameTopBarModel.emit(gameTopBarModel.value.copy(levelTime = updatedTime))
            if (gameTopBarModel.value.levelTime == 0) {
                _gameTopBarModel.emit(gameTopBarModel.value.copy(levelTime = 0))
                setGameStatus(GameStatus.GameOver)
                break
            }
        }
    }

    suspend fun reloadGame() {
        _gameLevel.emit(
            reloadGameItem
        )
        _gameTopBarModel.emit(reloadToolbar)
        setGameStatus(GameStatus.Playing)
        updateGame()
        progressCountDownTimer.resetTimer()
    }

    private suspend fun updateSingleDroppedItem() {
        if (gameLevel.value.singleDroppedItemModel == null) return
        val singleDroppedItemModel = gameLevel.value.singleDroppedItemModel!!
        delay(gameLevel.value.delay)
        if (singleDroppedItemModel.intOffset.y <= screenSize.height) {
            updateItemByLongPress(singleDroppedItemModel)
        } else {
            resetPosition(singleDroppedItemModel)
        }
    }


    private suspend fun resetGame() {
        if (gameLevel.value.singleDroppedItemModel != null) resetPosition(gameLevel.value.singleDroppedItemModel!!)
        if (gameLevel.value.itemList.isNotEmpty()) {
            for (i in gameLevel.value.itemList.indices) {
                resetItemPositionInList(i, gameLevel.value.itemList[i])
            }
        }

    }

    private suspend fun resetPosition(singleDroppedItemModel: SingleDroppedItemModel) {
        val update = singleDroppedItemModel.intOffset.copy(
            x = Random.nextInt(screenSize.width - (singleDroppedItemModel.size.width + 20)),
            y = SCREEN_START_POSITION
        )
        _gameLevel.emit(
            gameLevel.value.copy(
                singleDroppedItemModel = singleDroppedItemModel.copy(
                    alpha = 1f,
                    intOffset = update,
                    size = IntSize(SIZE, SIZE)
                )
            )
        )
    }

    private suspend fun updateItemDroppedInList() {
        if (gameLevel.value.itemList.isEmpty()) return
        val droppedItemModelList = gameLevel.value.itemList
        delay(gameLevel.value.delay)
        for (i in droppedItemModelList.indices) {
            val singleDroppedItemModel = droppedItemModelList[i]
            if (droppedItemModelList[i].intOffset.y <= screenSize.height) {
                checkIsListItemOnPress(i, singleDroppedItemModel)
            } else {
                resetItemPositionInList(
                    index = i, singleDroppedItemModel = singleDroppedItemModel
                )
            }
        }
    }

    private suspend fun checkIsListItemOnPress(
        index: Int,
        singleDroppedItemModel: SingleDroppedItemModel
    ) {
        val itemOffset = singleDroppedItemModel.intOffset
        val itemSize = singleDroppedItemModel.size
        val tapOffset = _tapOffset.value.offset
        if (isTapOnItem(
                itemSize = itemSize,
                itemOffset = itemOffset,
                tapOffset = tapOffset
            )
        ) {
            updateItemInListSizeByPress(index, singleDroppedItemModel)
        } else {
            if (isUltimatePressed.first().isFinished)
                moveIteOnListUpOrDown(
                    index = index,
                    singleDroppedItemModel = singleDroppedItemModel
                )
        }
    }

    private suspend fun updateItemInListSizeByPress(
        i: Int,
        singleDroppedItemModel: SingleDroppedItemModel
    ) {
        if (_tapOffset.value.isOnTap && singleDroppedItemModel.size.width < ITEM_EXPLOSION_SIZE - MINUS) {
            updateItemSize(i, singleDroppedItemModel)
        } else {
            resetItemPositionInList(
                index = i, singleDroppedItemModel = singleDroppedItemModel
            )
        }
    }

    private suspend fun moveIteOnListUpOrDown(
        index: Int,
        singleDroppedItemModel: SingleDroppedItemModel
    ) {
        val maxItemSizeToMoveUp = singleDroppedItemModel.size.width
        if ((!_tapOffset.value.isOnTap)
            && maxItemSizeToMoveUp in MIN_ITEM_SIZE_TO_MOVE_UP..ITEM_EXPLOSION_SIZE - MINUS
        ) {
            moveItemInListUp(index, singleDroppedItemModel)
        } else {
            _gameLevel.emit(
                gameLevel.value.copy(
                    itemList = gameLevel.value.itemList.toMutableList().apply {
                        this[index] = singleDroppedItemModel.copy(
                            intOffset = updateItemByYDown(singleDroppedItemModel).intOffset
                        )
                    }
                )
            )
        }
    }

    private suspend fun updateItemSize(
        index: Int,
        singleDroppedItemModel: SingleDroppedItemModel
    ) {
        _gameLevel.emit(
            gameLevel.value.copy(
                itemList = gameLevel.value.itemList.toMutableList().apply {
                    this[index] = singleDroppedItemModel.copy(
                        size = IntSize(
                            width = singleDroppedItemModel.size.width + SIZE_UPDATE_STEP,
                            height = singleDroppedItemModel.size.height + SIZE_UPDATE_STEP
                        )
                    )
                }
            )
        )
    }

    private suspend fun moveItemInListUp(
        index: Int,
        singleDroppedItemModel: SingleDroppedItemModel
    ) {
        if (singleDroppedItemModel.intOffset.y >= SCREEN_START_POSITION) {
            _gameLevel.emit(
                gameLevel.value.copy(
                    itemList = gameLevel.value.itemList.toMutableList().apply {
                        this[index] = updateItemByYUp(singleDroppedItemModel)
                    }
                )
            )
        } else {
            updateTopBarProgress()
            resetItemPositionInList(
                index = index, singleDroppedItemModel = singleDroppedItemModel
            )
        }
    }

    private suspend fun resetItemPositionInList(
        index: Int,
        singleDroppedItemModel: SingleDroppedItemModel
    ) {
        val update = singleDroppedItemModel.intOffset.copy(
            x = Random.nextInt(screenSize.width - (singleDroppedItemModel.size.width + 20)),
            y = SCREEN_START_POSITION
        )
        _gameLevel.emit(
            gameLevel.value.copy(
                itemList = gameLevel.value.itemList.toMutableList().apply {
                    this[index] = gameLevel.value.itemList[index].copy(
                        alpha = 1f,
                        intOffset = update,
                        size = IntSize(SIZE, SIZE)
                    )
                },
            )
        )
    }

    private suspend fun updateItemByLongPress(singleDroppedItemModel: SingleDroppedItemModel) {
        val itemOffset = singleDroppedItemModel.intOffset
        val itemSize = singleDroppedItemModel.size
        val tapOffset = _tapOffset.value.offset
        if (isTapOnItem(
                itemSize = itemSize,
                itemOffset = itemOffset,
                tapOffset = tapOffset
            )
        ) {
            updateItemSize(singleDroppedItemModel)
        } else {
            moveItemUpOrDown(singleDroppedItemModel)
        }
    }

    private suspend fun moveItemUpOrDown(singleDroppedItemModel: SingleDroppedItemModel) {
        val maxItemSizeToMoveUp = singleDroppedItemModel.size.width
        if ((!_tapOffset.value.isOnTap)
            && maxItemSizeToMoveUp in MIN_ITEM_SIZE_TO_MOVE_UP..ITEM_EXPLOSION_SIZE - MINUS
        ) {
            moveUp(singleDroppedItemModel)
        } else {
            if (isUltimatePressed.first().isFinished)
                _gameLevel.emit(
                    gameLevel.value.copy(
                        singleDroppedItemModel = updateItemByYDown(
                            singleDroppedItemModel
                        )
                    )
                )
        }
    }

    private fun isTapOnItem(itemSize: IntSize, itemOffset: IntOffset, tapOffset: Offset): Boolean {
        return tapOffset.x >= itemOffset.x && tapOffset.x <= itemOffset.x + itemSize.width &&
                tapOffset.y >= itemOffset.y && tapOffset.y <= itemOffset.y + itemSize.height.toFloat() && _tapOffset.value.isOnTap

    }

    private fun updateItemByYUp(singleDroppedItemModel: SingleDroppedItemModel): SingleDroppedItemModel {
        val singleDroppedItem = singleDroppedItemModel.copy(
            alpha = 0.3f,
            intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y - MOVE_UP_STEP)
        )
        return singleDroppedItem
    }

    private fun updateItemByYDown(singleDroppedItemModel: SingleDroppedItemModel): SingleDroppedItemModel {
        val singleDroppedItem = singleDroppedItemModel.copy(
            intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y + DROP_DOWN_UPDATE_STEP)
        )
        return singleDroppedItem
    }

    private suspend fun updateItemSize(singleDroppedItemModel: SingleDroppedItemModel) {
        if (_tapOffset.value.isOnTap && singleDroppedItemModel.size.width < ITEM_EXPLOSION_SIZE - MINUS) {
            val singleDroppedItem = singleDroppedItemModel.copy(
                size = IntSize(
                    width = singleDroppedItemModel.size.width + SIZE_UPDATE_STEP,
                    height = singleDroppedItemModel.size.height + SIZE_UPDATE_STEP
                )
            )
            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
        } else if (_tapOffset.value.isOnTap && singleDroppedItemModel.size.width >= ITEM_EXPLOSION_SIZE - MINUS) {
            resetPosition(singleDroppedItemModel)
        }
    }

    private suspend fun moveUp(singleDroppedItemModel: SingleDroppedItemModel) {
        if (singleDroppedItemModel.intOffset.y >= SCREEN_START_POSITION) {
            _gameLevel.emit(
                gameLevel.value.copy(
                    singleDroppedItemModel = updateItemByYUp(
                        singleDroppedItemModel
                    )
                )
            )
        } else {
            updateTopBarProgress()
            resetPosition(singleDroppedItemModel)
        }
    }

    private fun updateTopBarProgress() {
        _gameTopBarModel.update { gameTopBarModel ->
            gameTopBarModel.copy(levelProgress = gameTopBarModel.levelProgress + TOB_BAR_PROGRESS_STEP)
        }
    }
}

