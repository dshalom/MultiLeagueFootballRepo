package com.ds.multileaguefootball.data

import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.model.Standings
import javax.inject.Inject

class InMemoryCache @Inject constructor() {
    var competitions: List<Competition>? = null
    var standings: MutableMap<Int, Standings> = mutableMapOf()
}
