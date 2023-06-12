package com.dicoding.bukuku

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreference private constructor(private val dataStore: DataStore<Preferences>) {

    fun getUser(): Flow<UserModel> {
        return dataStore.data.map { preferences ->
            UserModel(
                preferences[NAME_KEY] ?: "",
                preferences[STATE_KEY] ?: false
            )
        }
    }

    suspend fun saveUser(name: String, isLogin: Boolean) {
        dataStore.edit { preferences ->
            preferences[NAME_KEY] = name
            preferences[STATE_KEY] = isLogin
            preferences[LAST_LOGIN_TIME_KEY] = System.currentTimeMillis()
        }
    }

    suspend fun login() {
        dataStore.edit { preferences ->
            preferences[STATE_KEY] = true
            preferences[LAST_LOGIN_TIME_KEY] = System.currentTimeMillis()
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    suspend fun isSessionExpired(): Boolean {
        val lastLoginTime = dataStore.data.first()[LAST_LOGIN_TIME_KEY] ?: 0L
        val currentTime = System.currentTimeMillis()
        return (currentTime - lastLoginTime) > ONE_HOUR_IN_MILLIS
    }


    companion object {
        private const val ONE_HOUR_IN_MILLIS = 3_600_000 // 1 hour in milliseconds
        private val NAME_KEY = stringPreferencesKey("name")
        private val STATE_KEY = booleanPreferencesKey("state")
        private val LAST_LOGIN_TIME_KEY = longPreferencesKey("last_login_time")

        @Volatile
        private var INSTANCE: UserPreference? = null

        fun getInstance(dataStore: DataStore<Preferences>): UserPreference {
            return INSTANCE ?: synchronized(this) {
                val instance = UserPreference(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}
