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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {
    private val _isSoundEnabled = MutableStateFlow(true)
    val isSoundEnabled = _isSoundEnabled.asStateFlow()
    private val _mainBackGround = MutableStateFlow<List<BackgroundsUnlockedModel>>(emptyList())
    val mainBackGround = _mainBackGround.asStateFlow()
    fun toggleSound() {
        viewModelScope.launch {
            _isSoundEnabled.value = !_isSoundEnabled.value
        }
    }

    init {
        _mainBackGround.value = backList()
    }

    private fun backList() = listOf(
        BackgroundsUnlockedModel(Res.drawable.main_backgroud, true),
        BackgroundsUnlockedModel(Res.drawable.level1, false),
        BackgroundsUnlockedModel(Res.drawable.level2, false),
        BackgroundsUnlockedModel(Res.drawable.level4, false),
        BackgroundsUnlockedModel(Res.drawable.level3, false),
        BackgroundsUnlockedModel(Res.drawable.level5, false),
        BackgroundsUnlockedModel(Res.drawable.level6, false),
        BackgroundsUnlockedModel(Res.drawable.level7, false),
        BackgroundsUnlockedModel(Res.drawable.level8, false),
        BackgroundsUnlockedModel(Res.drawable.level9, false),
    )

}