package org.example.project

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.multiplatform.lifecycle.LifecycleTracker
import com.multiplatform.lifecycle.LocalLifecycleTracker
import com.multiplatform.lifecyle.LifecycleComposeUIVCDelegate
import org.example.project.data.local.local.ApplicationComponent
import org.example.project.data.local.media.AudioPlayerComponent
import platform.UIKit.UIViewController

fun MainViewController():UIViewController {
    val lifecycleTracker = LifecycleTracker()
    return ComposeUIViewController({
        delegate = LifecycleComposeUIVCDelegate(lifecycleTracker)
    }) {
        CompositionLocalProvider(LocalLifecycleTracker provides lifecycleTracker) {
            App()
        }
    }
// return   ComposeUIViewController {
//
//        App()
//    }
}

fun initialize() {
    ApplicationComponent.init()
    AudioPlayerComponent.init()
}