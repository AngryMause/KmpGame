package org.example.project.data.local

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OnTapEventModel(
    val isOnTap: Boolean = false,
    val offset: Offset = Offset.Zero,
    val size: Int = 0
)

class OnTapEvent {
    private val _onTapEvent = MutableStateFlow(OnTapEventModel())
    val onTapEvent = _onTapEvent.asStateFlow()

    fun setOnTapEvent(isOnTap: OnTapEventModel) {
        _onTapEvent.value = OnTapEventModel(
            isOnTap = isOnTap.isOnTap,
            offset = isOnTap.offset,
            size = isOnTap.size
        )
    }

}