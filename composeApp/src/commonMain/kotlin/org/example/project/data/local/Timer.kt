package org.example.project.data.local

import kotlinx.coroutines.delay
import org.lighthousegames.logging.logging

class Timer() {
    private var startTime: Long = 0

    fun setTime(long: Long) {
        startTime = long
    }
    val log = logging("Timer")

    suspend fun getTime(time: (Long) -> Unit) {
        while (startTime >= 0) {
            delay(1000)
            log.e { "timer $startTime" }
            time(startTime)
            startTime -= 1
        }
    }


}