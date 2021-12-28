package com.ds.multileaguefootball.presentaion.leagueTable

import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Standings

data class LeagueTableState(
    val standings: Standings? = null,
    val leagues: List<Competition>? = null,
    val error: Boolean = false,
    val loading: Boolean = false,
    val screenTitle: String = "MultiLeague Football"
)
