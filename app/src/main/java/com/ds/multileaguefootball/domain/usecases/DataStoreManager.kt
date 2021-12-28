package com.ds.multileaguefootball.domain.usecases

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DataStoreManager {
    suspend fun saveIdToDataStore(leagueId: Int)
    suspend fun getIdFromDataStore(): Flow<Int?>
    suspend fun saveNameToDataStore(leagueName: String)
    suspend fun getNameFromDataStore(): Flow<String?>
}

class DataStoreManagerImpl @Inject constructor(private val context: Context) : DataStoreManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        val LEAGUE_ID = intPreferencesKey("leagueId")
        val LEAGUE_NAME = stringPreferencesKey("leagueName")
    }

    override suspend fun saveIdToDataStore(leagueId: Int) {
        context.dataStore.edit {
            it[LEAGUE_ID] = leagueId
        }
    }

    override suspend fun getIdFromDataStore() = context.dataStore.data.map {
        it[LEAGUE_ID]
    }

    override suspend fun saveNameToDataStore(leagueName: String) {
        context.dataStore.edit {
            it[LEAGUE_NAME] = leagueName
        }
    }

    override suspend fun getNameFromDataStore() = context.dataStore.data.map {
        it[LEAGUE_NAME]
    }
}
