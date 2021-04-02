package com.example.myapplication.trending.viewmodel.repositories.source.remote

import com.example.myapplication.trending.viewmodel.repositories.source.remote.models.TrendingResponse
import retrofit2.http.GET

interface WebService  {
    @GET("search/repositories?q=language=+sort:stars")
    suspend fun getTrendingRepos(): TrendingResponse?
}