package org.example.project.data.local

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

private const val TIMER_COUNT=3
private const val PROGRESS_TIME=100
data class ProgressCountDownTimerModel(
    val timer: Int = TIMER_COUNT,
    val isFinished: Boolean = true,
    val progress: Float = 1f
)

class ProgressCountDownTimer {
    private val _timer =
        MutableStateFlow(ProgressCountDownTimerModel())
    val timer = _timer.asStateFlow()
    suspend fun startTimer() {
        _timer.emit(timer.value.copy(isFinished = false))
        while (_timer.value.timer >= 0) {
            _timer.value = _timer.value.copy(timer = _timer.value.timer - 1)
            delay(1000)
        }
        if (_timer.value.timer <= 0) {
            _timer.emit(timer.value.copy(isFinished = true))
        }
        loadProgress()
    }

    private suspend fun loadProgress() {
        for (i in 1..PROGRESS_TIME) {
            _timer.emit(_timer.value.copy(progress = i / 100f))
            delay(100)
        }
        _timer.emit(timer.value.copy(timer = TIMER_COUNT))
    }
}