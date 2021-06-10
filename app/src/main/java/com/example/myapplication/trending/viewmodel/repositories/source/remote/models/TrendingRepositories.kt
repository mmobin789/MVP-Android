package com.example.myapplication.trending.viewmodel.repositories.source.remote.models

import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

sealed class TrendingRepositories {
    class Error(val error: String) : TrendingRepositories()
    class Repositories(val trending: MutableList<Trending>) : TrendingRepositories()
}