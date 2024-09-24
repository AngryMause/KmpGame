package org.example.project.di

import org.koin.core.context.startKoin

fun initializeKoin() {
    startKoin {
        modules(appModule)
    }
}