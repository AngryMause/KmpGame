package org.example.project.model

import org.example.project.data.LevelProgress

data class GameLevelModel(
    val levelName: String,
    val isLevelUnlocked: Boolean,
    val levelProgress: LevelProgress = LevelProgress.NOT_COMPLETED,
)


