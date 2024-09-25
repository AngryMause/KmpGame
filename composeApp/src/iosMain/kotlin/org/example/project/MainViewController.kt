package org.example.project

import androidx.compose.ui.window.ComposeUIViewController
import org.example.project.data.local.local.ApplicationComponent
import org.example.project.data.local.media.AudioPlayerComponent

fun MainViewController() = ComposeUIViewController {
    App()
}

fun initialize() {
    ApplicationComponent.init()
    AudioPlayerComponent.init()
}