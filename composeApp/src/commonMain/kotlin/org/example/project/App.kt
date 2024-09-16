package org.example.project

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import org.example.project.screens.navigation.AppNavigation

@Composable
fun App(
    dataStore: DataStore<Preferences>
) {

    MaterialTheme {
        AppNavigation()
    }
}
