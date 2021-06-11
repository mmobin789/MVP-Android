package com.example.myapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.myapplication.trending.ui.adapter.TrendingUI
import com.example.myapplication.trending.viewmodel.TrendingViewModel
import com.example.myapplication.trending.viewmodel.repositories.TrendingRepository
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending
import com.example.myapplication.trending.viewmodel.repositories.source.remote.models.TrendingRepoAPI
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class TrendingViewModelTest {

    private val mTrendingRepository = mockk<TrendingRepository>()

    private val mTrendingViewModel = TrendingViewModel(mTrendingRepository)


    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()


    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @Test
    fun getTrendingRepos() {
        val trendingList = mockk<MutableList<Trending>>()
        coEvery { mTrendingRepository.getTrendingRepos() } returns TrendingRepoAPI.Repositories(
            trendingList
        )
        every { trendingList.isEmpty() } returns false
        Assert.assertTrue(trendingList.isNotEmpty())
    }

    @Test
    fun testTrendingUILoading() {
        coEvery { mTrendingRepository.getTrendingRepos() } returns mockk()
        val trendingLiveData = mTrendingViewModel.getTrendingUIData()
        var trendingUI: TrendingUI? = null
        val observer = Observer<TrendingUI> {
            if (trendingUI == null)
                trendingUI = it
        }
        trendingLiveData.observeForever(observer)
        mTrendingViewModel.getTrendingRepos()
        trendingLiveData.removeObserver(observer)
        Assert.assertTrue(trendingUI == TrendingUI.Loading)
    }

    @Test
    fun testTrendingUISuccess() {
        coEvery { mTrendingRepository.getTrendingRepos() } returns TrendingRepoAPI.Repositories(
            mockk()
        )
        val trendingLiveData = mTrendingViewModel.getTrendingUIData()
        val observer = Observer<TrendingUI> {}
        trendingLiveData.observeForever(observer)
        mTrendingViewModel.getTrendingRepos()
        trendingLiveData.removeObserver(observer)
        Assert.assertTrue(trendingLiveData.value is TrendingUI.Success)
    }

    @Test
    fun testTrendingUIFailedByTrendingAPI() {
        coEvery { mTrendingRepository.getTrendingRepos() } returns TrendingRepoAPI.Error(
            "Generic API Failure"
        )
        val trendingLiveData = mTrendingViewModel.getTrendingUIData()
        val observer = Observer<TrendingUI> {}
        trendingLiveData.observeForever(observer)
        mTrendingViewModel.getTrendingRepos()
        trendingLiveData.removeObserver(observer)
        Assert.assertTrue(trendingLiveData.value is TrendingUI.Failed)
    }

    @Test
    fun testTrendingUIFailedByInternetFailInApiCall() {
        coEvery { mTrendingRepository.getTrendingRepos() } returns TrendingRepoAPI.Error(
            UnknownHostException("Base URL not found").toString()
        )
        val trendingLiveData = mTrendingViewModel.getTrendingUIData()
        val observer = Observer<TrendingUI> {}
        trendingLiveData.observeForever(observer)
        mTrendingViewModel.getTrendingRepos()
        trendingLiveData.removeObserver(observer)
        Assert.assertTrue(trendingLiveData.value is TrendingUI.Failed)
    }

    @Test
    fun testTrendingUIInternetFailure() {
        val trendingLiveData = mTrendingViewModel.getTrendingUIData()
        val observer = Observer<TrendingUI> {}
        trendingLiveData.observeForever(observer)
        mTrendingViewModel.onInternetLost()
        trendingLiveData.removeObserver(observer)
        Assert.assertTrue(trendingLiveData.value == TrendingUI.InternetFailure)
    }

    @Test
    fun testTrendingUIInternetRestore() {
        val trendingLiveData = mTrendingViewModel.getTrendingUIData()
        val observer = Observer<TrendingUI> {}
        trendingLiveData.observeForever(observer)
        mTrendingViewModel.onInternet()
        trendingLiveData.removeObserver(observer)
        Assert.assertTrue(trendingLiveData.value == TrendingUI.InternetRestore)
    }
}