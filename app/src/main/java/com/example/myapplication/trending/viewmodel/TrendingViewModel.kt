package com.example.myapplication.trending.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.trending.network.LoadState
import com.example.myapplication.trending.ui.adapter.TrendingUI
import com.example.myapplication.trending.viewmodel.repositories.TrendingRepository
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendingViewModel(private val mTrendingRepo: TrendingRepository) : ViewModel() {
    private val mTrendingUIData = MutableLiveData<TrendingUI>()

    fun getTrendingUIData(): LiveData<TrendingUI> = mTrendingUIData

    fun getTrendingRepos(refresh: Boolean = false) {
        viewModelScope.launch {
            mTrendingRepo.getTrendingRepos(refresh).onStart {
                mTrendingUIData.postValue(TrendingUI.Loading)
            }.catch { e ->
                e.printStackTrace()
                mTrendingUIData.postValue(TrendingUI.Failed(e.toString()))
            }.collect {
                mTrendingUIData.postValue(TrendingUI.Success(it))
            }

        }
    }

    fun onInternet() {
        mTrendingUIData.postValue(TrendingUI.InternetRestore)
    }

    fun onInternetLost() {
        mTrendingUIData.postValue(TrendingUI.InternetFailure)
    }


    fun deleteTrendingRepos() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                mTrendingRepo.deleteLocalTrendingRepos()
            }
            mTrendingUIData.postValue(TrendingUI.Clear)
        }
    }

}