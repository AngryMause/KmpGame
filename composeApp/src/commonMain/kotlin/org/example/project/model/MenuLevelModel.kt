package org.example.project.model

import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.LevelProgressState

data class MenuLevelModel(
    val levelName: GameLevelStatus,
    val isLevelUnlocked: Boolean,
    val levelProgress: LevelProgressState = LevelProgressState.NOT_COMPLETED,
)


