package com.example.myapplication.trending.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.trending.ui.adapter.TrendingUI
import com.example.myapplication.trending.viewmodel.repositories.TrendingRepository
import com.example.myapplication.trending.viewmodel.repositories.source.remote.models.TrendingRepositories
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class TrendingViewModel(private val mTrendingRepo: TrendingRepository) : ViewModel() {
    private val mTrendingUIData = MutableLiveData<TrendingUI>()

    fun getTrendingUIData(): LiveData<TrendingUI> = mTrendingUIData

    fun getTrendingRepos(refresh: Boolean = false) {
        viewModelScope.launch {
            mTrendingUIData.postValue(TrendingUI.Loading)
            when (val repos = mTrendingRepo.getTrendingRepos(refresh)) {
                is TrendingRepositories.Repositories -> {
                    mTrendingUIData.postValue(TrendingUI.Success(repos.trending))
                }
                is TrendingRepositories.Error -> {
                    mTrendingUIData.postValue(TrendingUI.Failed(repos.error))
                }
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