package org.example.project.data.local.items

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import candypopuniverse.composeapp.generated.resources.Res
import candypopuniverse.composeapp.generated.resources.cake1
import candypopuniverse.composeapp.generated.resources.cake4
import candypopuniverse.composeapp.generated.resources.cake5
import candypopuniverse.composeapp.generated.resources.cake6
import candypopuniverse.composeapp.generated.resources.cake7
import candypopuniverse.composeapp.generated.resources.cake8
import candypopuniverse.composeapp.generated.resources.chokolate
import candypopuniverse.composeapp.generated.resources.ckake2
import candypopuniverse.composeapp.generated.resources.ckake3
import candypopuniverse.composeapp.generated.resources.fruit_lemon
import candypopuniverse.composeapp.generated.resources.level1
import candypopuniverse.composeapp.generated.resources.level2
import candypopuniverse.composeapp.generated.resources.level3
import candypopuniverse.composeapp.generated.resources.level4
import candypopuniverse.composeapp.generated.resources.level5
import candypopuniverse.composeapp.generated.resources.level6
import candypopuniverse.composeapp.generated.resources.level7
import candypopuniverse.composeapp.generated.resources.level8
import candypopuniverse.composeapp.generated.resources.level9
import candypopuniverse.composeapp.generated.resources.main_backgroud
import candypopuniverse.composeapp.generated.resources.rainbow_candy
import candypopuniverse.composeapp.generated.resources.strawberry
import candypopuniverse.composeapp.generated.resources.strawberry_chocolate
import org.example.project.data.local.repository.SCREEN_START_POSITION
import org.example.project.data.local.repository.SIZE
import org.example.project.data.local.state.GameLevelStatus
import org.example.project.model.GameLevelItemModel
import org.example.project.model.SingleDroppedItemModel
import kotlin.random.Random

private const val NEGATIVE_VALUE = 120

class ItemLevelCreateManager {
    fun getGameLevel(
        level: String, screenSize: IntSize,
    ): GameLevelItemModel {
        return when (level) {
            GameLevelStatus.LEVEL_0NE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level1,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.cake6,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_TWO.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level2,
                    delay = 44,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.chokolate,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.cake7,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_THREE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level3,
                    delay = 38,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.cake5,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_FOUR.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level4,
                    delay = 38,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.cake1,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_FIVE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level5,
                    delay = 36,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.rainbow_candy,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }

            GameLevelStatus.LEVEL_SIX.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level6,
                    delay = 34,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.ckake2,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.ckake3,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.cake7,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_SEVEN.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level7,
                    delay = 32,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.fruit_lemon,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION + 50
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.cake1,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.strawberry,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION + 70
                            )
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_EIGHT.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level8,
                    delay = 36,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.cake8,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION + 60
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.strawberry_chocolate,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION + 70
                            )
                        ),

                        )
                )
            }

            GameLevelStatus.LEVEL_NINE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level9,
                    delay = 30,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.cake4,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
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
                        Res.drawable.strawberry,
                        intOffset = IntOffset(
                            Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                            SCREEN_START_POSITION
                        ),
                        size = IntSize(SIZE, SIZE),
                    ),
                )
            }
        }
    }
}