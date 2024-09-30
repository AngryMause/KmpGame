package org.example.project.screens.navigation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import candypopuniverse.composeapp.generated.resources.Res
import candypopuniverse.composeapp.generated.resources.main_backgroud
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.example.project.data.local.local.coreComponent
import org.example.project.data.local.media.AudioPlayerComponent
import org.example.project.screens.setting.backList

@Serializable
data class Background(
    val back: Int
)

private const val APP_SOUND = 1


class AppViewModel(
) : ViewModel() {
    private val _mainScreen = MutableStateFlow(Res.drawable.main_backgroud)
    val mainScreen = _mainScreen.asStateFlow()
    private val audioPlayer = AudioPlayerComponent.audioPlayer
    private val localData = coreComponent.appPreferences
    fun playSound() {
        viewModelScope.launch(Dispatchers.IO) {
            localData.isSoundEnabled().collectLatest { isEnabled ->
                if (isEnabled) {
                    audioPlayer.playMainSound(APP_SOUND)
                } else {
                    audioPlayer.pauseMainSound()
                }
            }
        }
    }

    fun pauseSong() {
        viewModelScope.launch(Dispatchers.IO) {
            localData.isSoundEnabled().collectLatest { isEnabled ->
                if (isEnabled) {
                    audioPlayer.pauseMainSound()
                }
            }
        }
    }

    init {

        viewModelScope.launch(Dispatchers.IO) {
            coreComponent.appPreferences.getBackGroundImage.collect { background ->
                if (background.isNotEmpty()) {
                    val back = fromJson(background)
                    _mainScreen.value = backList()[back.back].background!!
                }
            }
        }
    }
}

private fun fromJson(json: String): Background {
    val jsonObject = Json.decodeFromString<Background>(json)
    return jsonObject
}

