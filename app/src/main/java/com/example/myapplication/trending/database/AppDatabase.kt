package com.example.myapplication.trending.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.trending.viewmodel.repositories.source.dao.TrendingDAO
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

@Database(entities = [Trending::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun trendingDao(): TrendingDAO
}