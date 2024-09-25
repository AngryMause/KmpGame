package org.example.project.data.local.repository

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.candy2
import firstkmpproject.composeapp.generated.resources.ckake2
import firstkmpproject.composeapp.generated.resources.level1
import firstkmpproject.composeapp.generated.resources.level2
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
import org.example.project.model.ItemListModel
import org.example.project.model.SingleDroppedItemModel
import org.jetbrains.compose.resources.DrawableResource
import org.lighthousegames.logging.logging
import kotlin.random.Random

private const val SIZE = 100
private const val SCREEN_START_POSITION = 400

class GameRepository(
    private val progressCountDownTimer: ProgressCountDownTimer,
    private val onTapEvent: OnTapEvent
) {
    val log = logging("GameRepository")
    private val _gameStatus = MutableStateFlow<GameStatus>(GameStatus.Loading)
    val gameStatus = _gameStatus.asStateFlow()
    private val _gameTopBarModel = MutableStateFlow(GameTopBarModel("", 0f, 0))
    private val _gameLevel = MutableStateFlow(GameLevelItemModel.Zero)
    val gameLevel = _gameLevel.asStateFlow()
    val gameTopBarModel = _gameTopBarModel.asStateFlow()
    private val _tapOffset = onTapEvent.onTapEvent
    private var screenSize = IntSize.Zero
    private var delay = 50L
    private var reloadGameItem: GameLevelItemModel = GameLevelItemModel.Zero
    private var reloadToolbar: GameTopBarModel = GameTopBarModel("", 0f, 0)
    val isUltimatePressed = progressCountDownTimer.timer
    private var isUpdateSend = false
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
        droppedImageList: List<ItemListModel> = emptyList(),
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

    suspend fun udpateGame() {
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
                log.e { "checkGameStatus ${gameTopBarModel.value.levelProgress}" }
                setGameStatus(
                    GameStatus.LevelCompleted(LevelProgressState.NOT_COMPLETED)
                )
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
        udpateGame()
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
        if (isUpdateSend) {
            isUpdateSend = false
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

    private suspend fun updateItemByLongPress(singleDroppedItemModel: SingleDroppedItemModel) {
        val itemOffset = singleDroppedItemModel.intOffset
        val itemSize = singleDroppedItemModel.size
        val tapOffset = _tapOffset.value.offset
        if (tapOffset.x >= itemOffset.x && tapOffset.x <= itemOffset.x + itemSize.width &&
            tapOffset.y >= itemOffset.y && tapOffset.y <= itemOffset.y + itemSize.height.toFloat()
        ) {
            if (_tapOffset.value.isLongPress && singleDroppedItemModel.size.width < 200) {
                val singleDroppedItem = singleDroppedItemModel.copy(
                    size = IntSize(
                        width = singleDroppedItemModel.size.width + 3,
                        height = singleDroppedItemModel.size.height + 3
                    )
                )
                _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
            } else if (_tapOffset.value.isLongPress && singleDroppedItemModel.size.width >= 200) {
                resetPosition(singleDroppedItemModel)
            }
        } else {
            if (!_tapOffset.value.isLongPress && singleDroppedItemModel.size.width < 200) {
                if (!isUpdateSend) {
                    _gameTopBarModel.update { gameTopBarModel ->
                        gameTopBarModel.copy(levelProgress = gameTopBarModel.levelProgress + 0.1f)
                    }
                    _gameLevel.update { gameLevel ->
                        gameLevel.copy(
                            singleDroppedItemModel = gameLevel.singleDroppedItemModel?.copy(
                                alpha = 0.5f
                            )
                        )
                    }
                    isUpdateSend = true
                }
                moveUp(singleDroppedItemModel)
            } else {
                resetPosition(singleDroppedItemModel)
            }
            val singleDroppedItem = singleDroppedItemModel.copy(
                intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y + 50)
            )
            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
        }
    }

    private suspend fun moveUp(singleDroppedItemModel: SingleDroppedItemModel) {
        if (singleDroppedItemModel.intOffset.y >= SCREEN_START_POSITION) {
            val singleDroppedItem = singleDroppedItemModel.copy(
                intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y - 40)
            )
            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
        } else {
            resetPosition(singleDroppedItemModel)
        }
    }

    private suspend fun updateItemDroppedList() {
        if (gameLevel.value.itemList.isEmpty()) return
        val droppedItemModelList = gameLevel.value.itemList
        val test = droppedItemModelList.map { itemListModel ->
            if (itemListModel.intOffset.y <= screenSize.height) {
                val update = itemListModel.intOffset.copy(
                    y = itemListModel.intOffset.y + 10
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


    private suspend fun getGameLevel(level: String) {
        return when (level) {
            GameLevelStatus.LEVEL_0NE.levelName -> {
                setLevelItem(
                    level,
                    13,
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
                        ItemListModel(
                            Res.drawable.ckake2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        ItemListModel(
                            Res.drawable.candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        ItemListModel(
                            Res.drawable.red_candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_THREE.levelName -> {
                setLevelItem(
                    level,
                    13,
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

            GameLevelStatus.LEVEL_FOUR.levelName -> {
                log.e { "getGameLevel ${level}" }
                setLevelItem(
                    level,
                    13,
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

            GameLevelStatus.LEVEL_FIVE.levelName -> {
                setLevelItem(
                    level,
                    13,
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

            GameLevelStatus.LEVEL_SIX.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level2,
                    droppedImageList = listOf(
                        ItemListModel(
                            Res.drawable.ckake2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        ItemListModel(
                            Res.drawable.candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        ItemListModel(
                            Res.drawable.red_candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_SEVEN.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level2,
                    droppedImageList = listOf(
                        ItemListModel(
                            Res.drawable.ckake2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        ItemListModel(
                            Res.drawable.candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        ItemListModel(
                            Res.drawable.red_candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_EIGHT.levelName -> {
                setLevelItem(
                    level,
                    13,
                    Res.drawable.level2,
                    droppedImageList = listOf(
                        ItemListModel(
                            Res.drawable.ckake2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        ItemListModel(
                            Res.drawable.candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        ItemListModel(
                            Res.drawable.red_candy2,
                            IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_NINE.levelName -> {
                log.e { "getGameLevel ${level}" }
                setLevelItem(
                    level,
                    13,
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

            else -> {}
        }
    }
}

