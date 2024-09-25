package org.example.project.model

import kotlinx.serialization.Serializable
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.data.local.state.LevelProgressState
import org.koin.core.scope.ScopeID

@Serializable
data class MenuLevelModel(
    val id: Int,
    val levelName: GameLevelStatus,
    var isLevelUnlocked: Boolean,
    var levelProgress: LevelProgressState = LevelProgressState.NOT_COMPLETED,
)


