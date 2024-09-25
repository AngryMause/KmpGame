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
import org.example.project.data.local.state.GameStatus
import org.example.project.data.local.repository.GameRepository
import org.example.project.data.local.state.LevelProgressState
import org.lighthousegames.logging.logging

private const val END_GAME_SOUND = 0


class GameViewM0del(
    private val gameRepository: GameRepository,
) : ViewModel() {
    val log = logging("GameViewM0del")
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

    fun playSound(id: Int = 0) {
        viewModelScope.launch {
            if (localData.isSoundEnabled().first()) {
                audioPlayer.playEndSound(END_GAME_SOUND)
            }
        }
    }

    fun saveLevelRecords(index: String, levelProgressState: LevelProgressState) {
        viewModelScope.launch(Dispatchers.IO) {
            val list = localData.getLevelList()
            log.e { "saveLevelRecords list $list" }
            list[index.toInt() - 1].levelProgress = levelProgressState
            list[index.toInt()].isLevelUnlocked = true

//            val newList = list.map {
//                if (it == index.toInt() - 1) {
//                    it.levelName.levelName.toInt() + 1
//                    it.copy(isLevelUnlocked = true, levelProgress = levelProgressState)
//                } else {
//                    it
//                }
//            }
//            log.e { "newList $newList" }
            log.e { "saveLevelRecords  after resetData list ${list[index.toInt() + 1]}" }

            localData.saveLevelList(list)
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