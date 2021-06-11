package com.example.myapplication.trending.viewmodel.repositories.source.remote.models

import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

sealed class TrendingRepoAPI {
    class Error(val error: String) : TrendingRepoAPI()
    class Repositories(val trending: MutableList<Trending>) : TrendingRepoAPI()
}