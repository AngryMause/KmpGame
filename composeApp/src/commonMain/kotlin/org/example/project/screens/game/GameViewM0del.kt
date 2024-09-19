package org.example.project.screens.game

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.example.project.data.local.OnTapEventModel
import org.example.project.data.local.state.GameStatus
import org.example.project.data.local.repository.GameRepository
import org.lighthousegames.logging.logging

class GameViewM0del(
    private val gameRepository: GameRepository,
) : ViewModel() {
    val log = logging("GameViewM0del")
    val gameTopBarModel = gameRepository.gameTopBarModel
    val gameLevel = gameRepository.gameLevel
    val gameStatus = gameRepository.gameStatus
    val isUltimatePressed = gameRepository.isUltimatePressed
    var gameJob: Job? = null
    fun initGame(screenSize: IntSize, gameStatus: String) {
        viewModelScope.launch {
            gameRepository.initGame(screenSize, gameStatus)
        }
    }


    init {
        log.e { "GameViewM0del init" }
    }
    fun setTapOffset(offset: OnTapEventModel) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            gameRepository.setTapOffset(offset)
        }
    }


    override fun onCleared() {
        super.onCleared()
        gameJob?.cancel()
        log.e { "GameViewM0del onCleared" }
    }

    fun setGameStatus(status: GameStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.setGameStatus(status)
        }
    }

    fun startGame() {
        gameJob = viewModelScope.launch {
            gameRepository.udpateGame()
        }
    }


    fun restartGame() {
        viewModelScope.launch {
            gameRepository.reloadGame()
        }
    }

    fun pauseDropped() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.setUltimatePressed()
        }
    }
}