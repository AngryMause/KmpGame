package org.example.project.data.local.repository

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.candy2
import firstkmpproject.composeapp.generated.resources.ckake2
import firstkmpproject.composeapp.generated.resources.level1
import firstkmpproject.composeapp.generated.resources.level2
import firstkmpproject.composeapp.generated.resources.level3
import firstkmpproject.composeapp.generated.resources.level4
import firstkmpproject.composeapp.generated.resources.level5
import firstkmpproject.composeapp.generated.resources.level6
import firstkmpproject.composeapp.generated.resources.level7
import firstkmpproject.composeapp.generated.resources.level8
import firstkmpproject.composeapp.generated.resources.red_candy2
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
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.GameStatus
import org.example.project.data.local.state.LevelProgressState
import org.example.project.model.GameLevelItemModel
import org.example.project.model.GameTopBarModel
import org.example.project.model.SingleDroppedItemModel
import org.jetbrains.compose.resources.DrawableResource
import org.lighthousegames.logging.logging
import kotlin.random.Random

private const val SIZE = 100
private const val SCREEN_START_POSITION = 400
private const val TOB_BAR_PROGRESS_STEP = 0.1f
private const val DROP_DOWN_UPDATE_STEP = 30
private const val SIZE_UPDATE_STEP = 4
private const val MOVE_UP_STEP = 30
private const val ITEM_EXPLOSION_SIZE = 200
private const val ITEM_MOVE_UP_SIZE = 60

class GameRepository(
    private val progressCountDownTimer: ProgressCountDownTimer,
    private val onTapEvent: OnTapEvent
) {
    val log = logging("GameRepository")
    private val _gameStatus = MutableStateFlow<GameStatus>(GameStatus.Loading)
    val gameStatus = _gameStatus.asStateFlow()
    private val _gameLevel = MutableStateFlow(GameLevelItemModel.Zero)
    val gameLevel = _gameLevel.asStateFlow()
    private val _gameTopBarModel = MutableStateFlow(GameTopBarModel("", 0f, 0))
    val gameTopBarModel = _gameTopBarModel.asStateFlow()
    private val _tapOffset = onTapEvent.onTapEvent
    private var screenSize = IntSize.Zero
    private var delay = 50L
    private var reloadGameItem: GameLevelItemModel = GameLevelItemModel.Zero
    private var reloadToolbar: GameTopBarModel = GameTopBarModel("", 0f, 0)
    val isUltimatePressed = progressCountDownTimer.timer
    private var isUpdateSend = true
    suspend fun initGame(screenSize: IntSize, gameLevel: String) {
        if (gameStatus.value == GameStatus.Loading) {
            this.screenSize = screenSize
            getGameLevel(gameLevel)
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
        time: Int,
        levelBackground: DrawableResource? = null,
        droppedImageList: List<SingleDroppedItemModel> = emptyList(),
        singleDroppedItemModel: SingleDroppedItemModel? = null,
    ) {
        reloadToolbar = GameTopBarModel(level, levelTime = time)

        reloadGameItem = GameLevelItemModel(
            background = levelBackground,
            itemList = droppedImageList,
            singleDroppedItemModel = singleDroppedItemModel
        )
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
                    delay(delay)
                    if (isUltimatePressed.first().isFinished) {
                        updateItemDroppedList()
                        updateSingleDroppedItem()
                    }
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

            gameTopBarModel.value.levelTime == 0 && gameTopBarModel.value.levelProgress >= 0.3f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.ONE_STAR)

            )

            gameTopBarModel.value.levelTime == 0 && gameTopBarModel.value.levelProgress >= 0.66f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.TWO_STARS)
            )

            gameTopBarModel.value.levelTime == 0 && gameTopBarModel.value.levelProgress >= 0.98f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.THREE_STARS)
            )

            gameTopBarModel.value.levelTime >= 0 && gameTopBarModel.value.levelProgress >= 1f -> setGameStatus(
                GameStatus.LevelCompleted(LevelProgressState.THREE_STARS)
            )

            else -> Unit
        }
    }

    private suspend fun updateGameTimer() {
        while (gameTopBarModel.value.levelTime >= 0) {
            delay(1000)
            checkGameStatus()
            val update = gameTopBarModel.value.levelTime - 1
            _gameTopBarModel.emit(gameTopBarModel.value.copy(levelTime = update))
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
        if (singleDroppedItemModel.intOffset.y <= screenSize.height) {
            updateItemByLongPress(singleDroppedItemModel)
        } else {
            resetPosition(singleDroppedItemModel)
        }
    }

    private suspend fun resetPosition(singleDroppedItemModel: SingleDroppedItemModel) {
        if (!isUpdateSend) {
            isUpdateSend = true
        }
        val update = singleDroppedItemModel.intOffset.copy(
            x = Random.nextInt(screenSize.width - (singleDroppedItemModel.size.width + 20)),
            y = SCREEN_START_POSITION
        )
        _gameLevel.emit(
            gameLevel.value.copy(
                singleDroppedItemModel = singleDroppedItemModel.copy(
                    intOffset = update,
                    size = IntSize(SIZE, SIZE)
                )
            )
        )
    }

    private suspend fun updateItemDroppedList() {
        if (gameLevel.value.itemList.isEmpty()) return
        val droppedItemModelList = gameLevel.value.itemList
        val test = droppedItemModelList.map { itemListModel ->
            if (itemListModel.intOffset.y <= screenSize.height) {
                val update = itemListModel.intOffset.copy(
                    y = itemListModel.intOffset.y + DROP_DOWN_UPDATE_STEP
                )
                return@map itemListModel.copy(intOffset = update)
            } else {
                val update = itemListModel.intOffset.copy(
                    x = Random.nextInt(screenSize.width - (itemListModel.size.width + 20)),
                    y = 300
                )
                return@map itemListModel.copy(intOffset = update)
            }
        }
        _gameLevel.emit(_gameLevel.value.copy(itemList = test))
    }

    private suspend fun updateItemByLongPress(singleDroppedItemModel: SingleDroppedItemModel) {
        val itemOffset = singleDroppedItemModel.intOffset
        val itemSize = singleDroppedItemModel.size
        val tapOffset = _tapOffset.value.offset
        if (isTapOnItem(itemSize = itemSize, itemOffset = itemOffset, tapOffset = tapOffset)) {
            updateItemSize(singleDroppedItemModel)
        } else {
            if (!_tapOffset.value.isLongPress && singleDroppedItemModel.size.width + ITEM_MOVE_UP_SIZE != 160) {
                moveUp(singleDroppedItemModel)
            } else {
                updateItemY(singleDroppedItemModel)
            }
        }
    }

    private fun isTapOnItem(itemSize: IntSize, itemOffset: IntOffset, tapOffset: Offset) =
        tapOffset.x >= itemOffset.x && tapOffset.x <= itemOffset.x + itemSize.width &&
                tapOffset.y >= itemOffset.y && tapOffset.y <= itemOffset.y + itemSize.height.toFloat()


    private suspend fun updateItemY(singleDroppedItemModel: SingleDroppedItemModel) {
        val singleDroppedItem = singleDroppedItemModel.copy(
            intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y + DROP_DOWN_UPDATE_STEP)
        )
        _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
    }

    private suspend fun updateItemSize(singleDroppedItemModel: SingleDroppedItemModel) {
        if (_tapOffset.value.isLongPress && singleDroppedItemModel.size.width < ITEM_EXPLOSION_SIZE) {
            val singleDroppedItem = singleDroppedItemModel.copy(
                size = IntSize(
                    width = singleDroppedItemModel.size.width + SIZE_UPDATE_STEP,
                    height = singleDroppedItemModel.size.height + SIZE_UPDATE_STEP
                )
            )
            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
        } else if (_tapOffset.value.isLongPress && singleDroppedItemModel.size.width >= ITEM_EXPLOSION_SIZE) {
            resetPosition(singleDroppedItemModel)
        }
    }

    private fun updateTopBarProgress() {
        _gameTopBarModel.update { gameTopBarModel ->
            gameTopBarModel.copy(levelProgress = gameTopBarModel.levelProgress + TOB_BAR_PROGRESS_STEP)
        }
    }

    private suspend fun moveUp(singleDroppedItemModel: SingleDroppedItemModel) {
        if (singleDroppedItemModel.intOffset.y >= SCREEN_START_POSITION) {
            if (isUpdateSend) {
                _gameLevel.update { gameLevel ->
                    gameLevel.copy(
                        singleDroppedItemModel = gameLevel.singleDroppedItemModel?.copy(
                            alpha = 0.3f
                        )
                    )
                }
                isUpdateSend = false
            }
            val singleDroppedItem = singleDroppedItemModel.copy(
                intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y - MOVE_UP_STEP)
            )
            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
        } else {
            updateTopBarProgress()
            resetPosition(singleDroppedItemModel)
        }
    }


    private suspend fun getGameLevel(level: String) {
        return when (level) {
            GameLevelStatus.LEVEL_0NE.levelName -> {
                setLevelItem(
                    level,
                    23,
                    Res.drawable.level1,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_TWO.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level2,
                    droppedImageList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_THREE.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level3,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_FOUR.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level4,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_FIVE.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level5,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_SIX.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level6,
                    droppedImageList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_SEVEN.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level7,
                    droppedImageList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_EIGHT.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level8,
                    droppedImageList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_NINE.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level8,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            else -> {}
        }
    }
}

