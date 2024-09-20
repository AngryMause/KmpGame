package org.example.project

import android.app.Application
import org.example.project.data.local.local.ApplicationComponent

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationComponent.init()
    }
}