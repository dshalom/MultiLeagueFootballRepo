package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchLeaguesUseCase
import com.ds.multileaguefootball.domain.usecases.FetchStandingsUseCase
import com.ds.multileaguefootball.domain.usecases.StoredLeagueUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTableViewModel @Inject constructor(
    private val fetchStandingsUseCase: FetchStandingsUseCase,
    private val storedLeagueUseCase: StoredLeagueUseCase,
    private val fetchLeaguesUseCase: FetchLeaguesUseCase
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
        fetchLeagues()
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

    private fun fetchLeagues() {
        viewModelScope.launch {
            fetchLeaguesUseCase(Unit).collect { leaguesData ->

                _viewState.value = when (leaguesData) {
                    is Resource.Error -> viewState.value.copy(error = true)
                    is Resource.Loading -> viewState.value.copy(loading = true)
                    is Resource.Success -> {
                        viewState.value.copy(
                            leagues = leaguesData.data,
                            error = false,
                            loading = false
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
                            _viewState.value = when (resource) {
                                is Resource.Error -> viewState.value.copy(
                                    error = true,
                                    loading = false,
                                    standings = null
                                )
                                is Resource.Loading -> viewState.value.copy(
                                    loading = true,
                                    error = false,
                                    standings = null
                                )
                                is Resource.Success -> _viewState.value.copy(
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
