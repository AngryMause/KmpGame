package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.multiplatform.lifecycle.LifecycleTracker
import com.multiplatform.lifecyle.AndroidLifecycleEventObserver

class MainActivity : ComponentActivity() {
    private val lifecycleTracker = LifecycleTracker()
    private val observer = AndroidLifecycleEventObserver(lifecycleTracker)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(observer)
        setContent {
            App(
            )
        }
    }
}
