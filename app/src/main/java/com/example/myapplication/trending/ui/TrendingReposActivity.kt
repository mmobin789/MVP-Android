package com.example.myapplication.trending.ui

import android.os.Bundle
import android.view.Menu
import android.widget.Toast

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

        mViewModel.let {
            it.getTrendingRepos()

            it.getLoadingState().observe(this@TrendingReposActivity) { loadState ->
                Toast.makeText(this@TrendingReposActivity, loadState.name, Toast.LENGTH_SHORT)
                    .show()
            }

            it.getReposData().observe(this@TrendingReposActivity) { trending ->
                Toast.makeText(
                    this@TrendingReposActivity,
                    trending.size.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        menu?.getItem(0)?.setOnMenuItemClickListener {
            mViewModel.getTrendingRepos(refresh = true)
            true
        }
        menu?.getItem(1)?.setOnMenuItemClickListener {
            mViewModel.deleteTrendingRepos()
            true
        }

        return true
    }

}