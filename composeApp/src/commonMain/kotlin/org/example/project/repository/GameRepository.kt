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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.model.GameLevelItemModel
import org.example.project.model.GameTopBarModel
import org.example.project.model.ItemListModel
import org.jetbrains.compose.resources.DrawableResource
import org.lighthousegames.logging.logging


class GameRepository {
    val log = logging("GameRepository")
    private val _gameStatus = MutableStateFlow<GameStatus>(GameStatus.LOADING)
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
        droppedImage: DrawableResource? = null,
        droppedImageList: List<ItemListModel> = emptyList()
    ) {
        _gameTopBarModel.emit(GameTopBarModel(level, levelTime = time))
        _gameLevel.emit(
            GameLevelItemModel(
                background = levelBackground,
                droppedImage = droppedImage,
                itemList = droppedImageList,
                intOffset = IntOffset.Zero,
            )
        )
    }

    suspend fun udpateGame() {
        when (gameStatus.value) {
            GameStatus.PLAYING -> {
                log.e { "udpateGame ${gameStatus.value}" }
            }


            GameStatus.GAME_OVER -> {
                log.e { "udpateGame ${gameStatus.value}" }
            }

            GameStatus.LEVEL_COMPLETE -> {
                log.e { "udpateGame ${gameStatus.value}" }
            }

            else -> {}
        }
    }

    private suspend fun getGameLevel(level: String) {
        return when (level) {
            GameLevelStatus.LEVEL_0NE.levelName -> {
                setLevelItem(
                    level,
                    30,
                    Res.drawable.level1,
                    Res.drawable.candy2,
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

enum class GameStatus {
    LOADING,
    PLAYING,
    GAME_OVER,
    LEVEL_COMPLETE
}

enum class GameLevelStatus(val levelName: String) {
    LEVEL_0NE("1"),
    LEVEL_TWO("2"),
    LEVEL_THREE("3"),
    LEVEL_FOUR("4"),
    LEVEL_FIVE("5"),
    LEVEL_SIX("6"),
    LEVEL_SEVEN("7"),
    LEVEL_EIGHT("8"),
    LEVEL_NINE("9")
}