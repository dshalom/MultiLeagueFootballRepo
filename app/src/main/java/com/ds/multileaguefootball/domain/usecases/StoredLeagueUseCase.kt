package com.ds.multileaguefootball.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface StoredLeagueUseCase {
    suspend fun getStoredLeagueId(): Flow<Int?>
    suspend fun storeLeagueId(leagueId: Int)
    suspend fun getLeagueName(): Flow<String?>
    suspend fun storeLeagueName(leagueName: String)
}

class StoredLeagueUseCaseImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : StoredLeagueUseCase {

    override suspend fun getStoredLeagueId(): Flow<Int?> = flow {
        dataStoreManager.getIdFromDataStore().collect {
            emit(it)
        }
    }

    override suspend fun storeLeagueId(leagueId: Int) {
        dataStoreManager.saveIdToDataStore(leagueId)
    }

    override suspend fun getLeagueName(): Flow<String?> = flow {
        dataStoreManager.getNameFromDataStore().collect {
            emit(it)
        }
    }

    override suspend fun storeLeagueName(leagueName: String) {
        dataStoreManager.saveNameToDataStore(leagueName)
    }
}
