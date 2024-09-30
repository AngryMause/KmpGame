package org.example.project.screens.game

import androidx.compose.ui.unit.IntSize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.example.project.data.local.OnTapEventModel
import org.example.project.data.local.local.coreComponent
import org.example.project.data.local.media.AudioPlayerComponent
import org.example.project.data.local.repository.GameRepository
import org.example.project.data.local.state.GameStatus
import org.example.project.data.local.state.LevelProgressState

private const val END_GAME_SOUND = 0


class GameViewM0del(
    private val gameRepository: GameRepository,
) : ViewModel() {
    private val audioPlayer = AudioPlayerComponent.audioPlayer
    private val localData = coreComponent.appPreferences
    val gameTopBarModel = gameRepository.gameTopBarModel
    val gameLevel = gameRepository.gameLevel
    val gameStatus = gameRepository.gameStatus
    val isUltimatePressed = gameRepository.isUltimatePressed
    private var gameJob: Job? = null


    fun initGame(screenSize: IntSize, gameStatus: String) {
        viewModelScope.launch {
            gameRepository.initGame(screenSize, gameStatus)
        }
    }

    fun playSound() {
        viewModelScope.launch {
            if (localData.isSoundEnabled().first()) {
                audioPlayer.playEndSound(END_GAME_SOUND)
            }
        }
    }

    fun saveLevelRecords(index: String, levelProgressState: LevelProgressState) {
        viewModelScope.launch(Dispatchers.IO) {
            if (levelProgressState != LevelProgressState.NOT_COMPLETED) {
                val list = localData.getLevelList()
                list[index.toInt() - 1].levelProgress = levelProgressState
                list[index.toInt()].isLevelUnlocked = true
                localData.saveLevelList(list)
            }
        }
    }

    fun setTapOffset(offset: OnTapEventModel) {
        viewModelScope.launch(Dispatchers.Unconfined) {
            gameRepository.setTapOffset(offset)
        }
    }

    override fun onCleared() {
        super.onCleared()
        gameJob?.cancel()
    }

    fun setGameStatus(status: GameStatus) {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.setGameStatus(status)
        }
    }

    fun startGame() {
        gameJob = viewModelScope.launch {
            gameRepository.updateGame()
        }
    }

    fun stopGame() {
        if (gameJob?.isActive == true) {
            gameJob?.cancel()
        }
    }

    fun restartGame() {
        viewModelScope.launch {
            gameJob?.start()
            gameRepository.reloadGame()
        }
    }

    fun pauseDropped() {
        viewModelScope.launch(Dispatchers.IO) {
            gameRepository.setUltimatePressed()
        }
    }
}