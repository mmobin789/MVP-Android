package com.example.myapplication.trending.viewmodel.repositories.source.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.myapplication.trending.database.BaseDAO
import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending
import kotlinx.coroutines.flow.Flow

@Dao
interface TrendingDAO : BaseDAO<Trending> {
    @Query("SELECT * FROM trending")
    fun getTrendingRepos(): List<Trending>

    @Query("DELETE FROM trending")
    fun delete()

}