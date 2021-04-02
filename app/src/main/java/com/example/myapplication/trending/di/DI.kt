package com.example.myapplication.trending.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.trending.database.AppDatabase
import com.example.myapplication.trending.network.NetworkConfig
import com.example.myapplication.trending.viewmodel.TrendingViewModel
import com.example.myapplication.trending.viewmodel.repositories.TrendingRepository
import com.example.myapplication.trending.viewmodel.repositories.source.local.LocalSource
import com.example.myapplication.trending.viewmodel.repositories.source.remote.RemoteSource
import com.example.myapplication.trending.viewmodel.repositories.source.remote.WebService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit

object DI {

    private var init = false

    /**
     * This initializes Koin DI for the whole application.
     * This must be only called once.
     */
    fun start(context: Context) {

        if (init)
            return

        val networkModule = module {
            single {
                NetworkConfig.getRetrofit()
            }
            single { NetworkConfig.getTrendingWebService(get()) }
            factory { RemoteSource(get()) }
            single { Room.databaseBuilder(get(), AppDatabase::class.java, "appDB").build() }
            factory { LocalSource(get<AppDatabase>().trendingDao()) }
        }

        val trendingModule = module {
            factory { TrendingRepository(get(), get()) }
            viewModel { TrendingViewModel(get()) }
        }



        startKoin {
            androidLogger()
            androidContext(context)
            modules(trendingModule, networkModule)
        }

        init = true


    }
}