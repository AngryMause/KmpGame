package org.example.project.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.level1
import firstkmpproject.composeapp.generated.resources.level2
import firstkmpproject.composeapp.generated.resources.level3
import firstkmpproject.composeapp.generated.resources.level4
import firstkmpproject.composeapp.generated.resources.level5
import firstkmpproject.composeapp.generated.resources.level6
import firstkmpproject.composeapp.generated.resources.level7
import firstkmpproject.composeapp.generated.resources.level8
import firstkmpproject.composeapp.generated.resources.level9
import firstkmpproject.composeapp.generated.resources.main_backgroud
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
import org.lighthousegames.logging.logging

class SettingViewModel
    : ViewModel() {
    val log = logging("SettingViewModel")
    private val _isSoundEnabled = MutableStateFlow(true)
    val isSoundEnabled = _isSoundEnabled.asStateFlow()
    private val _mainBackGround = MutableStateFlow<List<BackgroundsUnlockedModel>>(emptyList())
    val mainBackGround = _mainBackGround.asStateFlow()
    private val localData = coreComponent.appPreferences

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
            log.e { "json: $json" }
            coreComponent.appPreferences.saveNewBackGroundImage(json)
        }
    }

    private fun getBackGroundImage() {
        viewModelScope.launch(Dispatchers.IO) {
            val levelList = localData.getLevelList()
//            val newList = mutableListOf<BackgroundsUnlockedModel>()
//            for (i in backList().indices) {
//                backList()[i].isUnlocked =
//                    if (levelList[i].levelProgress == LevelProgressState.TWO_STARS || levelList[i].levelProgress == LevelProgressState.THREE_STARS) true else false
//            }
            log.e { "newList: $levelList" }
            _mainBackGround.emit(backList())

        }
    }

    init {
        getBackGroundImage()
        toggleSound()
    }
}

fun backList() = listOf(
    BackgroundsUnlockedModel(Res.drawable.main_backgroud, true),
    BackgroundsUnlockedModel(Res.drawable.level1, true),
    BackgroundsUnlockedModel(Res.drawable.level2, true),
    BackgroundsUnlockedModel(Res.drawable.level4, false),
    BackgroundsUnlockedModel(Res.drawable.level3, false),
    BackgroundsUnlockedModel(Res.drawable.level5, false),
    BackgroundsUnlockedModel(Res.drawable.level6, false),
    BackgroundsUnlockedModel(Res.drawable.level7, false),
    BackgroundsUnlockedModel(Res.drawable.level8, false),
    BackgroundsUnlockedModel(Res.drawable.level9, false),
)