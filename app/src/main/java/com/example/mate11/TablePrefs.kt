package com.example.mate11

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "mate11_prefs")

object TablePrefs {
    private val LAST_TABLE_KEY = intPreferencesKey("last_table")

    fun getLastTable(context: Context): Flow<Int?> =
        context.dataStore.data.map { prefs: Preferences ->
            prefs[LAST_TABLE_KEY]
        }

    suspend fun setLastTable(context: Context, table: Int) {
        context.dataStore.edit { prefs ->
            prefs[LAST_TABLE_KEY] = table
        }
    }
}
