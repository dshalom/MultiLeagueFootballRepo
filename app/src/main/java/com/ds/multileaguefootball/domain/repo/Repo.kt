package com.ds.multileaguefootball.domain.repo

import com.ds.multileaguefootball.domain.model.Competition

interface Repo {
    suspend fun fetchLeagues(): List<Competition>
}
