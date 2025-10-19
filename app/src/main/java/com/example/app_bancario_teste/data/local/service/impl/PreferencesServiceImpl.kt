package com.example.app_bancario_teste.data.local.service.impl

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import com.example.app_bancario_teste.data.local.service.PreferenceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PreferencesServiceImpl @Inject constructor(
    private val dataStore : DataStore<Preferences>
): PreferenceService{
    override suspend fun <T> saveOnPreferenceStore(value : T,key: Preferences.Key<T>) {
       try {
           dataStore.edit { preferences ->
               preferences[key]= value
           }
       }catch (ioException : IOException){
           throw  ioException
       }
    }

    override suspend fun <T> readFromPreference(
        key: Preferences.Key<T>
    ) : Flow<T?>{
       return dataStore.data.map {preferences ->
            preferences[key]
        }.catch { ioException->
            throw ioException
       }
    }

}