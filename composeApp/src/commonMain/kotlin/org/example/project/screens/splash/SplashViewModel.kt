package org.example.project.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import org.example.project.data.local.local.coreComponent
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.LevelProgressState
import org.example.project.model.MenuLevelModel

class SplashViewModel : ViewModel() {

    init {
        cheListData()
    }

    private fun cheListData() {
        viewModelScope.launch(Dispatchers.IO) {
            val list = coreComponent.appPreferences.getLevelList()
            if (list.isEmpty()) {
                saveList()
            }
        }
    }

    private suspend fun saveList() {
        coreComponent.appPreferences.saveLevelList(gemeLevelList)
    }

    private val gemeLevelList = listOf(
        MenuLevelModel(
            id = 1,
            GameLevelStatus.LEVEL_0NE,
            isLevelUnlocked = true,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            id = 2,
            GameLevelStatus.LEVEL_TWO,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            id = 3,
            GameLevelStatus.LEVEL_THREE,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            id = 4,
            GameLevelStatus.LEVEL_FOUR,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            5,
            GameLevelStatus.LEVEL_FIVE,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            6,
            GameLevelStatus.LEVEL_SIX,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            7,
            GameLevelStatus.LEVEL_SEVEN,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            8,
            GameLevelStatus.LEVEL_EIGHT,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
        MenuLevelModel(
            9,
            GameLevelStatus.LEVEL_NINE,
            isLevelUnlocked = false,
            levelProgress = LevelProgressState.NOT_COMPLETED
        ),
    )

}