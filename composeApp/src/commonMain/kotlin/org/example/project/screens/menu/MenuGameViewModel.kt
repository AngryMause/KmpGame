package org.example.project.screens.menu

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.LevelProgressState
import org.example.project.model.MenuLevelModel

class MenuGameViewModel : ViewModel() {

    private val _gameLevelList = MutableStateFlow(emptyList<MenuLevelModel>())
    val gameLevelList = _gameLevelList.asStateFlow()
    init {
        _gameLevelList.value = gemeLevelList
    }

}

private val gemeLevelList = listOf(
    MenuLevelModel(
        GameLevelStatus.LEVEL_0NE,
        isLevelUnlocked = true,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_TWO,
        isLevelUnlocked = true,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_THREE,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_FOUR,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_FIVE,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_SIX,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_SEVEN,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_EIGHT,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
    MenuLevelModel(
        GameLevelStatus.LEVEL_NINE,
        isLevelUnlocked = false,
        levelProgress = LevelProgressState.NOT_COMPLETED
    ),
)


