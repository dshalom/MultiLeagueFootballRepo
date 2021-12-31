package com.ds.multileaguefootball.data

import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Standings
import com.ds.multileaguefootball.domain.model.Team
import javax.inject.Inject

class InMemoryCache @Inject constructor() {
    var competitions: List<Competition>? = null
    var standings: MutableMap<Int, Standings> = mutableMapOf()
    var teams: MutableMap<Int, Team> = mutableMapOf()
}
