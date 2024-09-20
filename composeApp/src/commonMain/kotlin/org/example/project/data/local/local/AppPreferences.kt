package org.example.project.data.local.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.lighthousegames.logging.logging

interface AppPreferences {
    suspend fun isSoundEnabled(): Boolean
    suspend fun changeSoundEnabled(isEnabled: Boolean): Preferences
    suspend fun saveNewBackGroundImage(imageUrl: String)
    val getBackGroundImage: Flow<String>
}

internal class AppPreferencesImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferences {
    val log = logging("AppPreferencesImpl")

    private companion object {
        private const val PREFS_TAG_KEY = "AppPreferences"
        private const val IS_DARK_MODE_ENABLED = "prefsBoolean"
        private const val BACK_GROUND_IMAGE = "backGroundImage"
    }

    private val darkModeKey = booleanPreferencesKey("$PREFS_TAG_KEY$IS_DARK_MODE_ENABLED")
    private val backGroundImageKey = stringPreferencesKey("$PREFS_TAG_KEY$BACK_GROUND_IMAGE")

    override suspend fun isSoundEnabled() = dataStore.data.map { preferences ->
        preferences[darkModeKey] ?: false
    }.first()

    override suspend fun changeSoundEnabled(isEnabled: Boolean) = dataStore.edit { preferences ->
        preferences[darkModeKey] = isEnabled
    }

    override suspend fun saveNewBackGroundImage(imageUrl: String) {
        dataStore.edit { preferences ->
            preferences[backGroundImageKey] = imageUrl
        }
    }

    override val getBackGroundImage: Flow<String> =
        dataStore.data.map { preferences ->
            preferences[backGroundImageKey] ?: ""
        }
}