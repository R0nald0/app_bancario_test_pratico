package com.example.app_bancario_teste.data.local.service

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferenceService {
    suspend fun  <T> saveOnPreferenceStore(value : T,key: Preferences.Key<T>)
    suspend fun <T>readFromPreference(key : Preferences.Key<T>) :Flow<T?>
}