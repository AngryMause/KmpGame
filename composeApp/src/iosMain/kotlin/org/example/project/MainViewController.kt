package org.example.project

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.data.local.local.ApplicationComponent

fun MainViewController() = ComposeUIViewController {
    App()
}

fun initialize() {
    ApplicationComponent.init()
}