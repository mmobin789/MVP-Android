package com.example.myapplication.database

import com.example.myapplication.trending.viewmodel.repositories.source.local.models.Trending

object DBUtils {

    fun createTrending(id: Int) = Trending(
        id = id,
        username = "mmobin$id",
        libraryName = "Library $id",
        language = "Kotlin",
        stars = 70 + id

    )

    fun createTrendingList(count: Int): List<Trending> {
        val list = ArrayList<Trending>(count)
        for (i in 1..count)
            list.add(
                Trending(
                    id = i,
                    username = "mmobin$i",
                    libraryName = "Library $i",
                    language = "Kotlin",
                    stars = 70 + i

                )
            )

        return list
    }
}