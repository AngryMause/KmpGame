package org.example.project

import android.app.Application
import org.example.project.data.local.local.ApplicationComponent
import org.example.project.data.local.media.AudioPlayerComponent
import org.example.project.di.initializeKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
initComponents()
    }

    private fun initComponents() {
        initializeKoin()
        ApplicationComponent.init()
        AudioPlayerComponent.init()
    }
}