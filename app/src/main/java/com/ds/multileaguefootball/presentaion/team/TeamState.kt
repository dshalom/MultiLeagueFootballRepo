package com.ds.multileaguefootball.presentaion.team

import com.ds.multileaguefootball.domain.model.Matches
import com.ds.multileaguefootball.domain.model.Team

data class TeamState(
    val teamData: Team? = null,
    val matchesData: Matches? = null,
    val error: Boolean = false,
    val loading: Boolean = false
)
