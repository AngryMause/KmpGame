package org.example.project.screens.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.main_backgroud
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel : ViewModel() {
    private val _isSoundEnabled = MutableStateFlow(true)
    val isSoundEnabled = _isSoundEnabled.asStateFlow()
    private val _mainBackGround = MutableStateFlow(Res.drawable.main_backgroud)
    val mainBackGround = _mainBackGround.asStateFlow()
    fun toggleSound() {
        viewModelScope.launch {
            _isSoundEnabled.value = !_isSoundEnabled.value
        }
    }


}