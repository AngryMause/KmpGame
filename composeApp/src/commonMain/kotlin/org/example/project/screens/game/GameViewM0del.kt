package org.example.project.screens.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.example.project.repository.GameLevelStatus
import org.example.project.repository.GameRepository
import org.example.project.repository.GameStatus
import org.example.project.data.local.Timer
import org.lighthousegames.logging.logging

class GameViewM0del(
    private val gameRepository: GameRepository
) : ViewModel() {
    val log = logging("GameViewM0del")

    val timer = Timer()
    val gameTopBarModel = gameRepository.gameTopBarModel
    val gameLevel = gameRepository.gameLevel
    val gameStatus = gameRepository.gameStatus
    fun initGame(screenSize: IntSize, gameStatus: String) {
        viewModelScope.launch {
            gameRepository.initGame(screenSize, gameStatus)
        }
    }


    init {
        log.e { "GameViewM0del init" }
    }

    fun setTapOffset(offset: Offset) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            gameRepository.setTapOffset(offset)
        }
    }

    override fun onCleared() {
        super.onCleared()
        log.e { "GameViewM0del onCleared" }
    }

    fun setGameStatus(status: GameStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.setGameStatus(status)
        }
    }

    fun restartGame() {
        viewModelScope.launch {
        }
    }

    fun pauseDropped() {
        viewModelScope.launch {
        }
    }

    init {
        timer.setTime(20)
    }
}