package com.example.myapplication.database

import android.util.Log
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.di.TestDI
import com.example.myapplication.trending.viewmodel.repositories.source.dao.TrendingDAO
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.java.KoinJavaComponent.inject

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TrendingDaoTest {
    private val mTrendingDAO by inject<TrendingDAO>(TrendingDAO::class.java)

    @Before
    fun init() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        TestDI.start(appContext)
    }

    @Test
    fun insertTrendingRepos() {
        val idsList = runBlocking { mTrendingDAO.insertAll(DBUtils.createTrendingList(10)) }
        assertTrue(idsList.also { Log.d("IdsList", it.joinToString()) }.isNotEmpty())
    }

    @Test
    fun getTrendingRepos() {
        val reposFound = runBlocking {
            mTrendingDAO.insertAll(DBUtils.createTrendingList(10))
            mTrendingDAO.getTrendingRepos().also { Log.d("IdsList get", it.joinToString()) }
                .isNotEmpty()
        }

        assertTrue(reposFound)
    }

    @Test
    fun deleteTrendingRepos() {
        val deleted = runBlocking {
            val dummyList = DBUtils.createTrendingList(10)
            if (mTrendingDAO.insertAll(dummyList).isNotEmpty()) {
                mTrendingDAO.deleteAll(dummyList)
                mTrendingDAO.getTrendingRepos().isEmpty()
            } else false
        }

        assertTrue(deleted.also { Log.d("IdsList", "Deleted.") })
    }
}