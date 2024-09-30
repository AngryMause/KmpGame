package org.example.project.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import candypopuniverse.composeapp.generated.resources.Res
import candypopuniverse.composeapp.generated.resources.level1
import candypopuniverse.composeapp.generated.resources.level2
import candypopuniverse.composeapp.generated.resources.level3
import candypopuniverse.composeapp.generated.resources.level4
import candypopuniverse.composeapp.generated.resources.level5
import candypopuniverse.composeapp.generated.resources.level6
import candypopuniverse.composeapp.generated.resources.level7
import candypopuniverse.composeapp.generated.resources.level8
import candypopuniverse.composeapp.generated.resources.level9
import candypopuniverse.composeapp.generated.resources.main_backgroud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.example.project.data.local.local.coreComponent
import org.example.project.data.local.state.LevelProgressState
import org.example.project.screens.navigation.Background

class SettingViewModel
    : ViewModel() {
    private val _isSoundEnabled = MutableStateFlow(true)
    val isSoundEnabled = _isSoundEnabled.asStateFlow()
    private val _mainBackGround = MutableStateFlow<List<BackgroundsUnlockedModel>>(emptyList())
    val mainBackGround = _mainBackGround.asStateFlow()
    private val localData = coreComponent.appPreferences

    init {
        getBackGroundImage()
        toggleSound()
    }

    private fun toggleSound() {
        viewModelScope.launch {
            val isSoundEnabled = localData.isSoundEnabled()
            _isSoundEnabled.value = isSoundEnabled.first()
        }
    }

    fun saveSoundEnabled(isEnabled: Boolean) {
        viewModelScope.launch {
            _isSoundEnabled.emit(isEnabled)
            coreComponent.appPreferences.changeSoundEnabled(isEnabled)
        }
    }

    fun saveNewBackGroundImage(imageUrl: Int) {
        viewModelScope.launch {
            val data = Background(imageUrl)
            val json = Json.encodeToString(data)
            coreComponent.appPreferences.saveNewBackGroundImage(json)
        }
    }

    private fun getBackGroundImage() {
        viewModelScope.launch(Dispatchers.IO) {
            _mainBackGround.emit(convertListWithUnlocked())
        }
    }

    private suspend fun convertListWithUnlocked(): List<BackgroundsUnlockedModel> {
        val levelList = localData.getLevelList()
        val newList = mutableListOf<BackgroundsUnlockedModel>()
        backList().mapIndexed() { index, background ->
            newList.add(
                background.copy(
                    isUnlocked = if (index == 0) true else levelList[index - 1].levelProgress == LevelProgressState.TWO_STARS || levelList[index - 1].levelProgress == LevelProgressState.THREE_STARS
                )
            )
        }
        return newList
    }
}

fun backList() = listOf(
    BackgroundsUnlockedModel(Res.drawable.main_backgroud, true),
    BackgroundsUnlockedModel(Res.drawable.level1, false),
    BackgroundsUnlockedModel(Res.drawable.level2, true),
    BackgroundsUnlockedModel(Res.drawable.level4, false),
    BackgroundsUnlockedModel(Res.drawable.level3, false),
    BackgroundsUnlockedModel(Res.drawable.level5, false),
    BackgroundsUnlockedModel(Res.drawable.level6, false),
    BackgroundsUnlockedModel(Res.drawable.level7, false),
    BackgroundsUnlockedModel(Res.drawable.level8, false),
    BackgroundsUnlockedModel(Res.drawable.level9, false),
)