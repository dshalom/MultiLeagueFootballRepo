package com.ds.multileaguefootball.domain.usecases

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import javax.inject.Inject

interface LeagueNavUseCase {
    suspend fun getStoredLeagueId(userAction: Boolean): Flow<Int?>
    suspend fun storeLeagueId(leagueId: Int)
}

class LeagueNavUseCaseImpl @Inject constructor(
    private val dataStoreManager: DataStoreManager
) : LeagueNavUseCase {

    override suspend fun getStoredLeagueId(userAction: Boolean): Flow<Int?> = flow {
        if (userAction) {
            emit(0)
        } else {
            dataStoreManager.getFromDataStore().collect {

                Timber.i("dsds readubg $it")

                emit(it)
            }
        }
    }

    override suspend fun storeLeagueId(leagueId: Int) {

        Timber.i("dsds storing $leagueId")
        dataStoreManager.savetoDataStore(leagueId)
    }
}
