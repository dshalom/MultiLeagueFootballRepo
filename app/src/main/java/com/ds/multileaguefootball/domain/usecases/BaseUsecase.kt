package com.ds.multileaguefootball.domain.usecases

import com.ds.multileaguefootball.domain.common.Resource
import kotlinx.coroutines.flow.Flow

interface BaseUseCase<T, K> {
    suspend operator fun invoke(other: T): Flow<Resource<K>>
}
