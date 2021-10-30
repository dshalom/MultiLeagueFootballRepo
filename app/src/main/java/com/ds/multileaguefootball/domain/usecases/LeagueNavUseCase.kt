package com.ds.multileaguefootball.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface LeagueNavUseCase {
    suspend fun getStoredLeagueId(): Flow<Int?>
    suspend fun storeLeagueId(leagueId: Int)
}

class LeagueNavUseCaseImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : LeagueNavUseCase {

    override suspend fun getStoredLeagueId(): Flow<Int?> = flow {
        dataStoreManager.getFromDataStore().collect {
            emit(it)
        }
    }

    override suspend fun storeLeagueId(leagueId: Int) {
        dataStoreManager.savetoDataStore(leagueId)
    }
}
