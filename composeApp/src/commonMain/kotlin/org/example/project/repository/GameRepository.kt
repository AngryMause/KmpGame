package org.example.project.repository

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.candy
import firstkmpproject.composeapp.generated.resources.candy2
import firstkmpproject.composeapp.generated.resources.ckake
import firstkmpproject.composeapp.generated.resources.ckake2
import firstkmpproject.composeapp.generated.resources.level1
import firstkmpproject.composeapp.generated.resources.level2
import firstkmpproject.composeapp.generated.resources.red_candy2
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.GameStatus
import org.example.project.model.GameLevelItemModel
import org.example.project.model.GameTopBarModel
import org.example.project.model.ItemListModel
import org.example.project.model.SingleDroppedItemModel
import org.jetbrains.compose.resources.DrawableResource
import org.lighthousegames.logging.logging


class GameRepository {
    val log = logging("GameRepository")

    private val _gameStatus = MutableStateFlow(GameStatus.LOADING)
    val gameStatus = _gameStatus.asStateFlow()
    private val _gameTopBarModel = MutableStateFlow(GameTopBarModel("", 0f, 0))
    private val _gameLevel = MutableStateFlow(GameLevelItemModel.Zero)
    val gameLevel = _gameLevel.asStateFlow()
    val gameTopBarModel = _gameTopBarModel.asStateFlow()
    private val _tapOffset = MutableStateFlow(Offset.Zero)
    private var screenSize = IntSize.Zero

    suspend fun initGame(screenSize: IntSize, gameLevel: String) {
        if (gameStatus.value == GameStatus.LOADING) {
            this.screenSize = screenSize
            getGameLevel(gameLevel)
        }
    }

    suspend fun setTapOffset(offset: Offset) {
        log.e { "setTapOffset $offset" }
        _tapOffset.emit(offset)
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
        _gameTopBarModel.emit(GameTopBarModel(level, levelTime = time))
        _gameLevel.emit(
            GameLevelItemModel(
                background = levelBackground,
                itemList = droppedImageList,
                singleDroppedItemModel = singleDroppedItemModel
            )
        )
    }

    private var isUltimatePressed = false

    suspend fun udpateGame() {
        log.e { "udpateGame ${gameStatus.value}" }
        while (gameTopBarModel.value.levelTime >= 0 && !isUltimatePressed) {
            delay(300)
            updateItemDroppedList()
            updateSingleDroppedItem()
        }
    }

    private suspend fun updateSingleDroppedItem() {
        if (gameLevel.value.singleDroppedItemModel == null) return
        val singleDroppedItemModel = gameLevel.value.singleDroppedItemModel!!
        if (singleDroppedItemModel.intOffset.y <= screenSize.height) {
            val singleDroppedItem = singleDroppedItemModel.copy(
                intOffset = singleDroppedItemModel.intOffset.copy(y = singleDroppedItemModel.intOffset.y + 10)
            )
            _gameLevel.emit(gameLevel.value.copy(singleDroppedItemModel = singleDroppedItem))
        } else {
            val update = singleDroppedItemModel.intOffset.copy(
                x = 300,
                y = 100
            )
            _gameLevel.emit(
                gameLevel.value.copy(
                    singleDroppedItemModel = singleDroppedItemModel.copy(
                        intOffset = update
                    )
                )
            )
        }
    }


    private suspend fun updateItemDroppedList() {
        if (gameLevel.value.itemList.isEmpty()) return
        val droppedItemModelList = gameLevel.value.itemList
        droppedItemModelList.forEach { item ->
            if (item.intOffset.y <= screenSize.height) {
                val update = item.intOffset.copy(
                    y = item.intOffset.y + 10
                )
                _gameLevel.emit(
                    gameLevel.value.copy(
                        itemList = droppedItemModelList.map {
                            if (it == item) {
                                return@map item.copy(intOffset = update)
                            } else {
                                return@map it
                            }
                        }
                    )
                )

            } else {
                val update = item.intOffset.copy(
                    x = 300,
                    y = 100
                )
                _gameLevel.emit(
                    gameLevel.value.copy(
                        itemList = droppedItemModelList.map {
                            if (it == item) {
                                return@map item.copy(intOffset = update)
                            } else {
                                return@map it
                            }
                        }
                    )
                )
            }
        }
    }

    private suspend fun getGameLevel(level: String) {
        return when (level) {
            GameLevelStatus.LEVEL_0NE.levelName -> {
                setLevelItem(
                    level,
                    30,
                    Res.drawable.level1,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(100, 100),
                        size = IntSize(100, 100),
                    ),
                )
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_TWO.levelName -> {
                setLevelItem(
                    level,
                    40,
                    Res.drawable.level2,
                    droppedImageList = listOf(
                        ItemListModel(Res.drawable.ckake2, IntOffset(100, 100)),
                        ItemListModel(Res.drawable.candy2, IntOffset(200, 200)),
                        ItemListModel(Res.drawable.red_candy2, IntOffset(300, 300)),
                    )
                )
            }

            GameLevelStatus.LEVEL_THREE.levelName -> {
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_FOUR.levelName -> {
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_FIVE.levelName -> {
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_SIX.levelName -> {
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_SEVEN.levelName -> {
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_EIGHT.levelName -> {
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_NINE.levelName -> {
                log.e { "getGameLevel ${level}" }
            }

            else -> {}
        }
    }
}

