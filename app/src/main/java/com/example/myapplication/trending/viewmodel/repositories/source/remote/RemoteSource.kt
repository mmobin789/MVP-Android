package com.example.myapplication.trending.viewmodel.repositories.source.remote

class RemoteSource(private val webService: WebService) {
    suspend fun getTrendingRepos() = webService.getTrendingRepos()
}