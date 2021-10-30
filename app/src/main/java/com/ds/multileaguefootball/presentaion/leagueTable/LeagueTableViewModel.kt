package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchStandingsUseCase
import com.ds.multileaguefootball.domain.usecases.LeagueNavUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTableViewModel @Inject constructor(
    private val fetchStandingsUseCase: FetchStandingsUseCase,
    private val leagueNavUseCase: LeagueNavUseCase
) :
    ViewModel() {

    private val _viewState: MutableStateFlow<LeagueTableState> =
        MutableStateFlow(LeagueTableState(null, false))
    val viewState: StateFlow<LeagueTableState> = _viewState

    fun onStart() {
        viewModelScope.launch {

            leagueNavUseCase.getStoredLeagueId()
                .collect {
                    it?.let { leagueId ->
                        val result = fetchStandingsUseCase(leagueId = leagueId)
                        result.collect { resource ->
                            _viewState.value = when (resource) {
                                is Resource.Error -> LeagueTableState(
                                    error = true,
                                    loading = false,
                                    data = null
                                )
                                is Resource.Loading -> LeagueTableState(
                                    loading = true,
                                    error = false,
                                    data = null
                                )
                                is Resource.Success -> LeagueTableState(
                                    data = resource.data,
                                    error = false,
                                    loading = false
                                )
                            }
                        }
                    } ?: kotlin.run {
                        _viewState.value = LeagueTableState(
                            error = true,
                            loading = false,
                            data = null
                        )
                    }
                }
        }
    }
}
