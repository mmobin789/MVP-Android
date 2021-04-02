package com.example.myapplication.trending.di

import android.content.Context
import com.example.myapplication.trending.viewmodel.TrendingViewModel
import com.example.myapplication.trending.viewmodel.repositories.TrendingRepository
import com.example.myapplication.trending.viewmodel.repositories.source.local.LocalSource
import com.example.myapplication.trending.viewmodel.repositories.source.remote.RemoteSource
import com.example.myapplication.trending.viewmodel.repositories.source.remote.WebService
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DI {

    /**
     * This initializes Koin DI for the whole application.
     * This must be only called once.
     */
    fun start(context: Context) {

        val networkModule = module {
            single {
                Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.github.com/")
                    .build()
            }

            single { get<Retrofit>().create(WebService::class.java) }

            factory { RemoteSource(get()) }
            factory { LocalSource() }
        }

        val trendingModule = module {
            factory { TrendingRepository(get(), get()) }
            viewModel { TrendingViewModel(get()) }
        }



        startKoin {
            androidLogger(level = Level.DEBUG)
            androidContext(context)
            modules(trendingModule, networkModule)
        }


    }
}