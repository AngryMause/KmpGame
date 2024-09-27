package org.example.project.di

import org.example.project.data.local.OnTapEvent
import org.example.project.data.local.ProgressCountDownTimer
import org.example.project.data.local.items.ItemLevelCreateManager
import org.example.project.data.local.items.MultipleModelItemServices
import org.example.project.data.local.items.SingleModelItemServices
import org.example.project.data.local.repository.GameRepository
import org.example.project.screens.game.GameViewM0del
import org.example.project.screens.menu.MenuGameViewModel
import org.example.project.screens.navigation.AppViewModel
import org.example.project.screens.setting.SettingViewModel
import org.example.project.screens.splash.SplashViewModel
import org.koin.compose.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    factory { GameRepository(get(), get(), get()) }
    factory { ProgressCountDownTimer() }
    factory { ItemLevelCreateManager() }
    factory { OnTapEvent() }
    factory { SingleModelItemServices() }
    factory { MultipleModelItemServices() }
    viewModel { GameViewM0del(get()) }
    viewModel { SettingViewModel() }
    viewModel { MenuGameViewModel() }
    viewModel { AppViewModel() }
    viewModel { SplashViewModel() }
}

