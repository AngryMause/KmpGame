package org.example.project.screens.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.example.project.data.local.local.coreComponent
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.LevelProgressState
import org.example.project.model.MenuLevelModel
import org.lighthousegames.logging.logging

class MenuGameViewModel : ViewModel() {

    private val _gameLevelList = MutableStateFlow(emptyList<MenuLevelModel>())
    val gameLevelList = _gameLevelList.asStateFlow()
    val log = logging("MenuGameViewModel")

    init {
        viewModelScope.launch(Dispatchers.IO) {
//            val list = coreComponent.appPreferences.getLevelList()
            _gameLevelList.value = checkIsLevelUnlocked()
        }
    }


    private suspend fun checkIsLevelUnlocked(): List<MenuLevelModel> {
        val list = coreComponent.appPreferences.getLevelList()
        val newList = mutableListOf<MenuLevelModel>()
        for (i in list.indices) {
            if (i == 0) {
                list[i].isLevelUnlocked = true
                newList.add(list[i])
            } else if (list[i].levelProgress == LevelProgressState.NOT_COMPLETED) {
                list[i].isLevelUnlocked = false
                newList.add(list[i])
            }

        }

        return newList
    }
}



