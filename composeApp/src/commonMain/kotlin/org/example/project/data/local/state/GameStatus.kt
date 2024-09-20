package org.example.project.data.local.state

sealed class GameStatus {
    data object Loading : GameStatus()
    data object Playing : GameStatus()
    data object GameOver : GameStatus()
    data class LevelCompleted(val level: LevelProgressState) : GameStatus()
}

