package org.example.project.data.local.items

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import firstkmpproject.composeapp.generated.resources.Res
import firstkmpproject.composeapp.generated.resources.blue_candy1
import firstkmpproject.composeapp.generated.resources.blue_white_candy
import firstkmpproject.composeapp.generated.resources.candy2
import firstkmpproject.composeapp.generated.resources.chocolate_candy1
import firstkmpproject.composeapp.generated.resources.chocolate_candy2
import firstkmpproject.composeapp.generated.resources.chokolate
import firstkmpproject.composeapp.generated.resources.ckake2
import firstkmpproject.composeapp.generated.resources.ckake3
import firstkmpproject.composeapp.generated.resources.fruit1
import firstkmpproject.composeapp.generated.resources.fruit2
import firstkmpproject.composeapp.generated.resources.fruit_cacke
import firstkmpproject.composeapp.generated.resources.green_candy
import firstkmpproject.composeapp.generated.resources.ice_cream
import firstkmpproject.composeapp.generated.resources.level1
import firstkmpproject.composeapp.generated.resources.level2
import firstkmpproject.composeapp.generated.resources.level3
import firstkmpproject.composeapp.generated.resources.level4
import firstkmpproject.composeapp.generated.resources.level5
import firstkmpproject.composeapp.generated.resources.level6
import firstkmpproject.composeapp.generated.resources.level7
import firstkmpproject.composeapp.generated.resources.level8
import firstkmpproject.composeapp.generated.resources.level9
import firstkmpproject.composeapp.generated.resources.main_backgroud
import firstkmpproject.composeapp.generated.resources.pink_rainbow_candy
import firstkmpproject.composeapp.generated.resources.red_candy1
import firstkmpproject.composeapp.generated.resources.red_candy2
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
                        Res.drawable.candy2,
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
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.chokolate,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.chocolate_candy1,
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
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.chokolate,
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
                    Res.drawable.blue_white_candy,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
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
                    Res.drawable.pink_rainbow_candy,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
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
                            Res.drawable.fruit_cacke,
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
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.fruit1,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.fruit2,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.ice_cream,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION
                            )
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_EIGHT.levelName -> {
                GameLevelItemModel(
                    Res.drawable.level8,
                    itemList = listOf(
                        SingleDroppedItemModel(
                            Res.drawable.red_candy1,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION + 60
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.green_candy,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION + 70
                            )
                        ),
                        SingleDroppedItemModel(
                            Res.drawable.pink_rainbow_candy,
                            intOffset = IntOffset(
                                Random.nextInt(screenSize.width - (NEGATIVE_VALUE)),
                                SCREEN_START_POSITION + 100
                            )
                        ),
                    )
                )
            }

            GameLevelStatus.LEVEL_NINE.levelName -> {
                GameLevelItemModel(
                    Res.drawable.blue_candy1,
                    singleDroppedItemModel = SingleDroppedItemModel(
                        Res.drawable.candy2,
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
                        Res.drawable.candy2,
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