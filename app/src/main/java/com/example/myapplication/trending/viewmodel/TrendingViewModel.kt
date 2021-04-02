package com.example.myapplication.trending.viewmodel

import androidx.lifecycle.ViewModel
import com.example.myapplication.trending.viewmodel.repositories.TrendingRepository
import org.koin.java.KoinJavaComponent.inject

class TrendingViewModel(private val mTrendingRepo: TrendingRepository) : ViewModel() {

    fun getTrendingRepos() {
        // todo
       // mTrendingRepo.getTrending()
    }
}