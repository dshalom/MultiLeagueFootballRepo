package com.ds.multileaguefootball.domain.usecases

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface DataStoreManager {
    suspend fun savetoDataStore(leagueId: Int)
    suspend fun getFromDataStore(): Flow<Int?>
}

class DataStoreManagerImpl @Inject constructor(private val context: Context) : DataStoreManager {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    companion object {
        val LEAGUEID = intPreferencesKey("leagueId")
    }

    override suspend fun savetoDataStore(leagueId: Int) {
        context.dataStore.edit {
            it[LEAGUEID] = leagueId
        }
    }

    override suspend fun getFromDataStore() = context.dataStore.data.map {
        it[LEAGUEID]
    }
}
