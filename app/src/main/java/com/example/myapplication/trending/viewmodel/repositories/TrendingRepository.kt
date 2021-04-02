package com.example.myapplication.trending.viewmodel.repositories

import com.example.myapplication.trending.viewmodel.repositories.source.local.LocalSource
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending
import com.example.myapplication.trending.viewmodel.repositories.source.remote.RemoteSource
import com.example.myapplication.trending.viewmodel.repositories.source.remote.models.toLocalList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

/**
 * This repository is used for fetching trending github repos from a data source.
 */
class TrendingRepository(
    private val mRemoteSource: RemoteSource,
    private val mLocalSource: LocalSource
) {
    /**
     * This webservice doesn't support pagination.
     * Assuming minimum api calls, a single call will update the DB and return locally afterwards.
     * Even if pagination is applied locally, the data isn't enough for multiple pages.
     * Usually 10 - 50 items per page are considered normal.
     * But APIs total number of items appear to be always 30 so it makes sense to fetch all items in a single call.
     * What is the right time or state to call the API once its data is locally stored ?
     * Many such things are missing from the problem statement.
     * The function will attempt to load data from API in case of refresh or in 1st invocation and from local DB in subsequent invocations.
     * There would be a refresh option in the menu to trigger an explicit call.
     * An explicit override is therefore set.
     * @param refresh pass true to update the local DB.
     */
    fun getTrendingRepos(refresh: Boolean): Flow<List<Trending>> {
        return flow {
            var localList = mLocalSource.getTrendingRepos()
            if (refresh || localList.isEmpty()) {
                mRemoteSource.getTrendingRepos()?.items?.run {
                    localList = toLocalList()
                    mLocalSource.insertTrendingRepos(localList)
                }
            }
            emit(localList)
        }.flowOn(Dispatchers.IO)

    }


    fun deleteLocalTrendingRepos(): Flow<Unit> {
        return flow {
            emit(mLocalSource.clear())
        }.flowOn(Dispatchers.IO)
    }
}