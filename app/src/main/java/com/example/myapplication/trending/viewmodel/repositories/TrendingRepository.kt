package com.example.myapplication.trending.viewmodel.repositories

import com.example.myapplication.trending.viewmodel.repositories.source.local.LocalSource
import com.example.myapplication.trending.viewmodel.repositories.source.remote.RemoteSource

/**
 * This repository is used for fetching trending github repos from a data source.
 */
class TrendingRepository(
    private val remoteSource: RemoteSource,
    private val localSource: LocalSource
) {

    suspend fun getTrending() {
        //todo
    }
}