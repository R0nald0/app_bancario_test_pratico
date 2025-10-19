package com.example.app_bancario_teste.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_bancario_teste.core.constants.AppConstants
import com.example.app_bancario_teste.core.extension.dataStore
import com.example.app_bancario_teste.data.local.dao.AppDatabase
import com.example.app_bancario_teste.data.local.service.PreferenceService
import com.example.app_bancario_teste.data.local.service.impl.PreferencesServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun providePreferenceService(dataStore: DataStore<Preferences>) : PreferencesServiceImpl {
        return PreferencesServiceImpl(dataStore = dataStore)
    }
    @Singleton
    @Provides
    fun provideDataStore( @ApplicationContext context : Context) : DataStore<Preferences> {
        return  context.dataStore
    }


    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase{
          return Room.databaseBuilder(
              context = context,
              name = AppConstants.DATABASE_NAME,
              klass = AppDatabase::class.java
          ).build()
    }

    @Provides
    @Singleton
    fun providerRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    }

}