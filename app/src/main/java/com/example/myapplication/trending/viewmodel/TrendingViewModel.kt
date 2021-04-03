package com.example.myapplication.trending.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.trending.network.LoadState
import com.example.myapplication.trending.viewmodel.repositories.TrendingRepository
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendingViewModel(private val mTrendingRepo: TrendingRepository) : ViewModel() {

    private val mReposData = MutableLiveData<MutableList<Trending>>()
    private val mLoadingData = MutableLiveData<LoadState>()

    fun getReposData(): LiveData<MutableList<Trending>> = mReposData

    fun getLoadingState(): LiveData<LoadState> = mLoadingData

    fun getTrendingRepos(refresh: Boolean = false) {
        viewModelScope.launch {
            mTrendingRepo.getTrendingRepos(refresh).onStart {
                mLoadingData.postValue(LoadState.LOADING)
            }.catch { e ->
                e.printStackTrace()
                mLoadingData.postValue(LoadState.FAILED)
            }.collect {
                mLoadingData.postValue(LoadState.SUCCESS)
                mReposData.postValue(it)
            }

        }
    }

    fun onInternet() {
        mLoadingData.postValue(LoadState.NETWORK_AVAILABLE)
    }

    fun onInternetLost() {
        mLoadingData.postValue(LoadState.NETWORK_UNAVAILABLE)
    }


    fun deleteTrendingRepos() {
        viewModelScope.launch {
            mTrendingRepo.deleteLocalTrendingRepos().collect()
            mReposData.postValue(mutableListOf())
        }
    }

}