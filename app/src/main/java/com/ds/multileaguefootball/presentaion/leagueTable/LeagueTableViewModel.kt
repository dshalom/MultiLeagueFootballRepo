package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchCompetitionsUseCase
import com.ds.multileaguefootball.domain.usecases.FetchStandingsUseCase
import com.ds.multileaguefootball.domain.usecases.StoredLeagueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTableViewModel @Inject constructor(
    private val fetchStandingsUseCase: FetchStandingsUseCase,
    private val storedLeagueUseCase: StoredLeagueUseCase,
    private val fetchCompetitionsUseCase: FetchCompetitionsUseCase
) : ViewModel() {

    private val _viewState: MutableStateFlow<LeagueTableState> =
        MutableStateFlow(
            LeagueTableState(
                standings = null,
                leagues = null,
                error = false,
                loading = false
            )
        )
    val viewState: StateFlow<LeagueTableState> = _viewState

    init {
        fetchCompetitions()
        fetchLeague()
        fetchScreenTitle()
    }

    private fun fetchScreenTitle() {
        viewModelScope.launch {
            storedLeagueUseCase.getLeagueName().collect {
                _viewState.value = _viewState.value.copy(
                    screenTitle = it ?: "Multileague Football"
                )
            }
        }
    }

    fun onMenuItemClicked(leagueId: Int, leagueName: String) {
        resetSelectedItem()
        viewModelScope.launch {
            storedLeagueUseCase.storeLeagueId(leagueId = leagueId)
            storedLeagueUseCase.storeLeagueName(leagueName = leagueName)
        }
    }

    private fun resetSelectedItem() {
        _viewState.value.leagues?.find { it.selected }?.selected = false
    }

    private fun fetchCompetitions() {
        viewModelScope.launch {
            fetchCompetitionsUseCase(Unit).collect { leaguesData ->
                when (leaguesData) {
                    is Resource.Error -> _viewState.update {
                        it.copy(
                            error = true,
                            loading = false,
                            standings = null
                        )
                    }
                    is Resource.Loading -> _viewState.update {
                        it.copy(
                            error = false,
                            loading = true,
                            standings = null
                        )
                    }
                    is Resource.Success -> _viewState.update {
                        it.copy(
                            error = false,
                            loading = false,
                            leagues = leaguesData.data
                        )
                    }
                }

                if (leaguesData is Resource.Success) {
                    storedLeagueUseCase.getStoredLeagueId().collect { storedId ->
                        storedId?.let {
                            leaguesData.data?.find { it.id == storedId }?.selected = true
                        } ?: kotlin.run {
                            storedLeagueUseCase.storeLeagueId(leaguesData.data?.get(0)?.id ?: 0)
                            storedLeagueUseCase.storeLeagueName(
                                leaguesData.data?.get(0)?.name ?: ""
                            )
                        }
                    }
                }
            }
        }
    }

    private fun fetchLeague() {
        viewModelScope.launch {
            storedLeagueUseCase.getStoredLeagueId()
                .collect { leagueId ->
                    leagueId?.let {
                        val result = fetchStandingsUseCase(leagueId = leagueId)
                        result.collect { resource ->
                            when (resource) {
                                is Resource.Error -> _viewState.update {
                                    it.copy(
                                        error = true,
                                        loading = false,
                                        standings = null
                                    )
                                }
                                is Resource.Loading -> _viewState.update {
                                    it.copy(
                                        loading = true,
                                        error = false,
                                        standings = null
                                    )
                                }
                                is Resource.Success -> _viewState.update {
                                    it.copy(
                                        standings = resource.data,
                                        error = false,
                                        loading = false
                                    )
                                }
                            }
                        }
                    }
                }
        }
    }
}
