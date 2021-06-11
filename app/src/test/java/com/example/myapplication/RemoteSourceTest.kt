package com.example.myapplication

import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending
import com.example.myapplication.trending.viewmodel.repositories.source.remote.RemoteSource
import com.example.myapplication.trending.viewmodel.repositories.source.remote.models.Item
import com.example.myapplication.trending.viewmodel.repositories.source.remote.models.TrendingResponse
import com.example.myapplication.trending.viewmodel.repositories.source.remote.models.toLocalList
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Test

class RemoteSourceTest {

    @Test
    fun verifyTrendingAPIResponseSuccess() {
        val remoteSource = mockk<RemoteSource>()
        val trendingResponse = mockk<TrendingResponse>()
        coEvery { remoteSource.getTrendingRepos() } returns trendingResponse
        val trendingList = mockk<MutableList<Trending>>()
        val items = mockk<List<Item>>()
        every { trendingResponse.items } returns items
        every { items.toLocalList() } returns trendingList
        every { trendingList.isEmpty() } returns false
        val result = trendingList.isNotEmpty()
        Assert.assertTrue(result)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun verifyTrendingAPIResponseFail() {
        val remoteSource = mockk<RemoteSource>()
        coEvery { remoteSource.getTrendingRepos() } returns null
        runBlockingTest {
            val result = remoteSource.getTrendingRepos()
            Assert.assertTrue(result == null)
        }
    }
}