package com.ds.multileaguefootball.presentaion.leagueTable

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ds.multileaguefootball.domain.common.Resource
import com.ds.multileaguefootball.domain.usecases.FetchStandingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueTableViewModel @Inject constructor(private val fetchStandingsUseCase: FetchStandingsUseCase) :
    ViewModel() {

    private val _viewState: MutableStateFlow<LeagueTableState> =
        MutableStateFlow(LeagueTableState(null, false))
    val viewState: StateFlow<LeagueTableState> = _viewState

    fun fetchStandings(leagueId: Int) {
        viewModelScope.launch {
            val result = fetchStandingsUseCase(leagueId = leagueId)
            result.collect {
                _viewState.value = when (it) {
                    is Resource.Error -> LeagueTableState(error = true)
                    is Resource.Loading -> LeagueTableState(loading = true)
                    is Resource.Success -> {
                        LeagueTableState(
                            data = it.data,
                            error = false
                        )
                    }
                }
            }
        }
    }
}
