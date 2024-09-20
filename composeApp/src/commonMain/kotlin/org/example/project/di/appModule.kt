package org.example.project.di

import org.example.project.data.local.OnTapEvent
import org.example.project.data.local.ProgressCountDownTimer
import org.example.project.data.local.repository.GameRepository
import org.example.project.screens.game.GameViewM0del
import org.example.project.screens.menu.MenuGameViewModel
import org.example.project.screens.navigation.AppViewModel
import org.example.project.screens.setting.SettingViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

val appModule = module {
    factory { GameRepository(get(),get()) }
    factory { ProgressCountDownTimer() }
    factory { OnTapEvent() }
    viewModel { GameViewM0del(get()) }
    viewModel { SettingViewModel() }
    viewModel { MenuGameViewModel() }
    viewModel { AppViewModel() }
}

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}