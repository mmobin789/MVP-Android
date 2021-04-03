package com.example.myapplication.trending.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTrendingReposBinding
import com.example.myapplication.trending.di.DI
import com.example.myapplication.trending.network.LoadState
import com.example.myapplication.trending.ui.adapter.TrendingAdapter
import com.example.myapplication.trending.viewmodel.TrendingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TrendingReposActivity : AppCompatActivity() {

    private val mViewModel: TrendingViewModel by viewModel()

    private val mTrendingAdapter by lazy {
        TrendingAdapter(mutableListOf())
    }


    private val mBinding by lazy {
        ActivityTrendingReposBinding.inflate(layoutInflater)
    }

    private val mErrorView by lazy {
        ErrorView(mBinding.uiError)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        DI.start(this)

        mBinding.rv.adapter = mTrendingAdapter

        mViewModel.let {
            it.getTrendingRepos()

            it.getLoadingState().observe(this@TrendingReposActivity) { loadState ->
                when (loadState) {
                    LoadState.LOADING -> {
                        mBinding.llShimmer.visibility = View.VISIBLE
                        mBinding.rv.visibility = View.GONE
                        mErrorView.hide()
                    }
                    LoadState.SUCCESS -> {
                        mBinding.llShimmer.visibility = View.GONE
                        mBinding.rv.visibility = View.VISIBLE
                    }

                    else -> {
                        mBinding.llShimmer.visibility = View.GONE
                        mErrorView.showMessage()
                    }
                }


            }

            it.getReposData().observe(this@TrendingReposActivity) { trending ->
                if (trending.isEmpty()) {
                    mErrorView.showMessage(getString(R.string.no_data_available))
                }
                mTrendingAdapter.update(trending)
            }

        }

        mErrorView.onRetryClick {
            mViewModel.getTrendingRepos()
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