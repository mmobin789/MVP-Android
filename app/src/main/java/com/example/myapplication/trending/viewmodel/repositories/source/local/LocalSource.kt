package com.example.myapplication.trending.viewmodel.repositories.source.local

import com.example.myapplication.trending.viewmodel.repositories.source.dao.TrendingDAO
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

class LocalSource(private val trendingDAO: TrendingDAO) {
    fun getTrendingRepos() = trendingDAO.getTrendingRepos()
    fun clear() = trendingDAO.delete()
    fun insertTrendingRepos(repos: List<Trending>) = trendingDAO.insertAll(repos)
}