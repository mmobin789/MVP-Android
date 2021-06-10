package com.example.myapplication.trending.ui

import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R
import com.example.myapplication.databinding.ActivityTrendingReposBinding
import com.example.myapplication.trending.di.DI
import com.example.myapplication.trending.network.InternetMonitor
import com.example.myapplication.trending.ui.adapter.TrendingAdapter
import com.example.myapplication.trending.ui.adapter.TrendingUI
import com.example.myapplication.trending.viewmodel.TrendingViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class TrendingReposActivity : AppCompatActivity(), InternetMonitor.OnInternetConnectivityListener {

    private val mViewModel: TrendingViewModel by viewModel()

    private var mMenu: Menu? = null


    private val mTrendingAdapter by lazy {
        TrendingAdapter(mutableListOf())
    }


    private val mBinding by lazy {
        ActivityTrendingReposBinding.inflate(layoutInflater)
    }

    private val mErrorView by lazy {
        ErrorView(mBinding.uiError)
    }

    private val mInternetMonitor = InternetMonitor(this)

    override fun onInternetAvailable() {
        mViewModel.onInternet()

    }

    override fun onInternetUnavailable() {
        mViewModel.onInternetLost()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        DI.start(this)

        mBinding.rv.adapter = mTrendingAdapter

        mViewModel.let {
            it.getTrendingRepos()

            it.getTrendingUIData().observe(this) { uiData ->
                when (uiData) {
                    TrendingUI.Loading -> {
                        mBinding.llShimmer.visibility = View.VISIBLE
                        mBinding.rv.visibility = View.GONE
                        mErrorView.hide()
                    }
                    is TrendingUI.Success -> {
                        mBinding.llShimmer.visibility = View.GONE
                        mBinding.rv.visibility = View.VISIBLE
                        val trending = uiData.trending

                        if (trending.isEmpty()) {
                            mErrorView.showMessage()
                        }
                        mTrendingAdapter.update(trending)
                    }
                    is TrendingUI.Failed -> {
                        mBinding.llShimmer.visibility = View.GONE
                        mErrorView.showMessage(uiData.error)
                    }
                    TrendingUI.InternetRestore -> {
                        mMenu?.getItem(0)?.isVisible = true   // can refresh
                        mErrorView.hideNoInternetView()

                    }
                    TrendingUI.InternetFailure -> {
                        mMenu?.getItem(0)?.isVisible = false  // cannot refresh
                        mErrorView.showNoInternetView()
                    }
                    TrendingUI.Clear -> {
                        mErrorView.showMessage(getString(R.string.db_cleared))
                    }
                }


            }

        }

        mErrorView.onRetryClick {
            mViewModel.getTrendingRepos()
        }


    }

    override fun onPause() {
        super.onPause()
        mInternetMonitor.unregister(this)
    }

    override fun onResume() {
        super.onResume()
        mInternetMonitor.register(this)
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

        mMenu = menu

        return true
    }

}