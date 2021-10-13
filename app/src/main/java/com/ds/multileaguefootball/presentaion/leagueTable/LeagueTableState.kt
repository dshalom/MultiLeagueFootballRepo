package com.ds.multileaguefootball.presentaion.leagueTable

import com.ds.multileaguefootball.domain.model.Standings

data class LeagueTableState(
    val data: Standings? = null,
    val error: Boolean = false,
    val loading: Boolean = false
)
