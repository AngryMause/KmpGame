package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import com.multiplatform.lifecycle.LifecycleEvent
import com.multiplatform.lifecycle.LifecycleObserver
import com.multiplatform.lifecycle.LocalLifecycleTracker
import org.example.project.screens.navigation.AppNavigation

@Composable
fun App(
) {
    MaterialTheme {
        AppNavigation()
    }
}

