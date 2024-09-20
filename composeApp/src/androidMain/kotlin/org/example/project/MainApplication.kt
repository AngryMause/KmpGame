package org.example.project

import android.app.Application
import android.util.Log
import org.example.project.data.local.local.ApplicationComponent

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationComponent.init()
    }
}