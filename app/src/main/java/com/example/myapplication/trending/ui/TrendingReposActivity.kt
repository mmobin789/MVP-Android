package com.example.myapplication.trending.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.trending.di.DI
import com.example.myapplication.trending.viewmodel.TrendingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingReposActivity : AppCompatActivity() {

    private val mViewModel: TrendingViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_trending_repos)
        DI.start(this)
        mViewModel.getTrendingRepos()

    }

}