package com.example.app_bancario_teste.core.constants

import androidx.datastore.preferences.core.stringPreferencesKey

object AppConstants {
    const val BASE_URL = "https://60bd336db8ab3700175a03b3.mockapi.io"
    const val DATA_STORE_NAME ="local_preferences"
    const val DATABASE_NAME ="app_payment.db"
    val CUSTOMER_PREFERENCE_KEY = stringPreferencesKey("customer_´pref_key")

}