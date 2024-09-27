package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.CompositionLocalProvider
import com.multiplatform.lifecycle.LifecycleTracker
import com.multiplatform.lifecycle.LocalLifecycleTracker
import com.multiplatform.lifecyle.AndroidLifecycleEventObserver

class MainActivity : ComponentActivity() {
    val lifecycleTracker = LifecycleTracker()
    val observer: AndroidLifecycleEventObserver =
        AndroidLifecycleEventObserver(lifecycleTracker)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycle.addObserver(observer)
        setContent {
            CompositionLocalProvider(LocalLifecycleTracker provides lifecycleTracker) {

                App(
                )
            }
        }
    }
}
