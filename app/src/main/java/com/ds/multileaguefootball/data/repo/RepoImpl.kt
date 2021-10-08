package com.ds.multileaguefootball.data.repo

import com.ds.multileaguefootball.data.httpclient.ApiService
import com.ds.multileaguefootball.domain.model.Competition
import com.ds.multileaguefootball.domain.repo.Repo
import javax.inject.Inject

class RepoImpl @Inject constructor(private val apiService: ApiService) : Repo {
    override suspend fun fetchLeagues(): List<Competition> {
        return apiService.getLeagues().toDomain()
    }
}
