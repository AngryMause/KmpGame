package org.example.project.data.local

import androidx.compose.ui.geometry.Offset
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.lighthousegames.logging.logging

data class OnTapEventModel(
    val isOnTap: Boolean = false,
    val isLongPress: Boolean = false,
    val offset: Offset = Offset.Zero,
    val size: Int = 0
)

class OnTapEvent {
    val log = logging("OnTapEvent")

    private val _onTapEvent = MutableStateFlow(OnTapEventModel())
    val onTapEvent = _onTapEvent.asStateFlow()

    fun setOnTapEvent(isOnTap: OnTapEventModel) {
        _onTapEvent.value = OnTapEventModel(
            isOnTap = isOnTap.isOnTap,
            isLongPress = isOnTap.isLongPress,
            offset = isOnTap.offset,
            size = isOnTap.size
        )
    }

    suspend fun test() {
        while (onTapEvent.value.isLongPress) {
            delay(500)
            log.e { "test ${_onTapEvent.value.size}" }
            _onTapEvent.emit(_onTapEvent.value.copy(size = _onTapEvent.value.size + 1))
        }
    }
}