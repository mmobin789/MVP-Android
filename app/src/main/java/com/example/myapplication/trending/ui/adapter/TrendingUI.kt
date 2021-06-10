package com.example.myapplication.trending.ui.adapter

import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

/**
 * This class represents UI and it's state for trending repositories data.
 */
sealed class TrendingUI {
    object Loading : TrendingUI()
    class Success(val trending: MutableList<Trending>) : TrendingUI()
    object Clear : TrendingUI()
    object InternetFailure : TrendingUI()
    object InternetRestore : TrendingUI()
    class Failed(val error: String) : TrendingUI()
}