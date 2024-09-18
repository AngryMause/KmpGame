package org.example.project.di

import org.example.project.data.local.ProgressCountDownTimer
import org.example.project.repository.GameRepository
import org.example.project.screens.game.GameViewM0del
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.mp.KoinPlatform.startKoin

val appModule = module {
    factory { GameRepository(get()) }
    factory { ProgressCountDownTimer() }
    viewModel { GameViewM0del(get()) }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}