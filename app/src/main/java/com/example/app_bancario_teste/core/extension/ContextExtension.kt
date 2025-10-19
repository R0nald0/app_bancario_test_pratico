package com.example.app_bancario_teste.core.extension

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.app_bancario_teste.core.constants.AppConstants

 val Context.dataStore : DataStore<Preferences> by preferencesDataStore(name = AppConstants.DATA_STORE_NAME)
