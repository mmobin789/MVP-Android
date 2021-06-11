package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.trending.database.AppDatabase
import com.example.myapplication.trending.viewmodel.repositories.source.local.LocalSource
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module

object TestDI {

    private var init = false

    /**
     * This initializes Koin DI for the whole test application.
     * This must be only called once.
     */
    fun start(context: Context) {
        if (init)
            return

        val dbModule = module {
            single { Room.inMemoryDatabaseBuilder(get(), AppDatabase::class.java).build() }
            single { get<AppDatabase>().trendingDao() }
            factory { LocalSource(get()) }
        }



        startKoin {
            androidLogger()
            androidContext(context)
            modules(dbModule)
        }
        init = true

    }
}