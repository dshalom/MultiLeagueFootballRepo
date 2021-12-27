package com.ds.multileaguefootball.presentaion.team

import com.ds.multileaguefootball.domain.model.Team

data class TeamState(
    val data: Team? = null,
    val error: Boolean = false,
    val loading: Boolean = false
)
