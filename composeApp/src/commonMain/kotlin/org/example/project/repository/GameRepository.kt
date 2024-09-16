package org.example.project.repository

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.candy
import firstkmpproject.composeapp.generated.resources.level1
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.model.GameLevelItemModel
import org.example.project.model.GameTopBarModel
import org.lighthousegames.logging.logging

class GameRepository {
    val log = logging("GameRepository")
    private val _gameStatus = MutableStateFlow(GameStatus.LOADING)
    val gameStatus = _gameStatus.asStateFlow()
    private val _gameTopBarModel = MutableStateFlow(GameTopBarModel("", 0, 0))
    private val _gameLevel = MutableStateFlow<GameLevelItemModel>(GameLevelItemModel.Zero)
    val gameLevel = _gameLevel.asStateFlow()
    val gameTopBarModel = _gameTopBarModel.asStateFlow()
    private var screenSize = IntSize.Zero

    suspend fun initGame(screenSize: IntSize, gameLevel: String) {
        if (gameStatus.value == GameStatus.LOADING) {
            this.screenSize = screenSize
            getGameLevel(gameLevel)
        }
    }

    suspend fun setGameStatus(status: GameStatus) {
        _gameStatus.emit(status)
    }

    private suspend fun getGameLevel(level: String) {
        return when (level) {
            GameLevelStatus.LEVEL_0NE.levelName -> {
                _gameTopBarModel.emit(GameTopBarModel(level, 0, 30))
                _gameLevel.emit(
                    GameLevelItemModel(
                        Res.drawable.level1,
                        Res.drawable.candy,
                        IntOffset.Zero,
                        IntSize(100, 100)
                    )
                )
                log.e { "getGameLevel ${level}" }
            }

            GameLevelStatus.LEVEL_TWO.levelName -> {
                log.e { "getGameLevel ${level}" }
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

object GameConstant {
    const val CATCH_ELEMENT_SIZE = 100
    const val BULL_WEIGHT = 200
    const val BULL_HEIGHT = 110
}

enum class GameStatus {
    LOADING,
    PLAYING,
    PAUSED,
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