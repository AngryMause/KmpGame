package org.example.project.model

import org.example.project.data.local.LevelProgressState
import org.example.project.repository.GameLevelStatus

data class MenuLevelModel(
    val levelName: GameLevelStatus,
    val isLevelUnlocked: Boolean,
    val levelProgress: LevelProgressState = LevelProgressState.NOT_COMPLETED,
)


