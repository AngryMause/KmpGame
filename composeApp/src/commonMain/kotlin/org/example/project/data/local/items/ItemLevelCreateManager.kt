package org.example.project.data.local.items

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.candy2
import firstkmpproject.composeapp.generated.resources.ckake2
import firstkmpproject.composeapp.generated.resources.level1
import firstkmpproject.composeapp.generated.resources.level2
import firstkmpproject.composeapp.generated.resources.level3
import firstkmpproject.composeapp.generated.resources.level4
import firstkmpproject.composeapp.generated.resources.level5
import firstkmpproject.composeapp.generated.resources.level6
import firstkmpproject.composeapp.generated.resources.level7
import firstkmpproject.composeapp.generated.resources.level8
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.red_candy2
import org.example.project.data.local.repository.SCREEN_START_POSITION
import org.example.project.data.local.repository.SIZE
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.model.GameLevelItemModel
import org.example.project.model.SingleDroppedItemModel
import kotlin.random.Random

class ItemLevelCreateManager {
    fun getGameLevel(
        level: String, screenSize: IntSize,
    ): GameLevelItemModel {
        return when (level) {
            GameLevelStatus.LEVEL_0NE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level1,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_TWO.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level2,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_THREE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level3,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_FOUR.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level4,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_FIVE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level5,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_SIX.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level6,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_SEVEN.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level7,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_EIGHT.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level8,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 100)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 200)
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.red_candy2,
                            intOffset = IntOffset(Random.nextInt(screenSize.width - (120)), 300)
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_NINE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level8,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            else -> {
                GameLevelItemModel(
                    Res.drawable.main_backgroud,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (120)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }
        }
    }
}