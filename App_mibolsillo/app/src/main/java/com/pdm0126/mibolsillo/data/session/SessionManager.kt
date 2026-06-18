package com.pdm0126.mibolsillo.data.session

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first

private val Context.dataStore by preferencesDataStore(
    name = "session"
)

class SessionManager(
    private val context: Context
) {

    companion object {

        private val TOKEN_KEY =
            stringPreferencesKey("token")
    }

    suspend fun saveToken(
        token: String
    ) {

        context.dataStore.edit { preferences ->

            preferences[TOKEN_KEY] = token
        }
    }

    suspend fun getToken(): String? {

        val preferences =
            context.dataStore.data.first()
        val token = preferences[TOKEN_KEY]

        println("TOKEN: $token")

        return token
        //return preferences[TOKEN_KEY]
    }

    suspend fun clearToken() {

        context.dataStore.edit { preferences ->

            preferences.remove(TOKEN_KEY)
        }
    }
}