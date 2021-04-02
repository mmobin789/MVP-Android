package com.example.myapplication.trending.viewmodel.repositories.source.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Trending(
    @PrimaryKey val id: Int? = null,
    val username: String? = null,
    val libraryName: String? = null,
    val language: String? = null,
    val description: String? = null,
    val imageUrl: String? = null,
    val stars: Int? = null
)