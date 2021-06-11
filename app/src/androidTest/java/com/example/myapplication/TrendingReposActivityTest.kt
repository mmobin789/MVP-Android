package com.example.myapplication


import android.content.Context
import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.myapplication.trending.ui.TrendingReposActivity
import com.example.myapplication.trending.ui.adapter.TrendingAdapter
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * This is a strict non-integration UI test meant to test views and activity only.
 * Integration testing would be too much for an interview process so its skipped on purpose.
 */
@RunWith(AndroidJUnit4::class)
class TrendingReposActivityTest {

    @get:Rule
    val mActivityScenarioRule = activityScenarioRule<TrendingReposActivity>()

    private lateinit var mAppContext: Context

    @Before
    fun setup() {
        mAppContext = InstrumentationRegistry.getInstrumentation().targetContext
    }

    @Test
    fun testReposLoadingSuccessfulDbAvailableUI() {
        mActivityScenarioRule.scenario.onActivity {
            hideShimmerShowList(it)
        }
        onView(withId(R.id.llShimmer)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rv)).check(matches(isDisplayed()))
        onView(withId(R.id.rv)).perform(RecyclerViewActions.scrollToPosition<TrendingAdapter.VH>(10))
    }

    @Test
    fun testReposLoadingUI() {
        mActivityScenarioRule.scenario.onActivity {
            showShimmerHideList(it)
        }

        onView(withId(R.id.llShimmer)).check(matches(isDisplayed()))
        onView(withId(R.id.rv)).check(matches(not(isDisplayed())))

    }

    @Test
    fun testReposLoadingFailUI() {
        val errorText = "Explicit UI test Loading Fail"
        mActivityScenarioRule.scenario.onActivity {
            it.mErrorView.showMessage(errorText)
            hideShimmerShowList(it)
        }
        onView(withId(R.id.llShimmer)).check(matches(not(isDisplayed())))
        onView(withId(R.id.uiError)).check(matches(isDisplayed()))
        onView(withId(R.id.tvError)).check(matches(withText(errorText)))

    }

    @Test
    fun testReposDBClearedUI() {
        val errorText = mAppContext.getString(R.string.db_cleared)
        mActivityScenarioRule.scenario.onActivity {
            it.mErrorView.showMessage(errorText)
        }
        onView(withId(R.id.uiError)).check(matches(isDisplayed()))
        onView(withId(R.id.tvError)).check(matches(withText(errorText)))

    }

    @Test
    fun testInternetUI() {
        val errorText = mAppContext.getString(R.string.internet_available)
        mActivityScenarioRule.scenario.onActivity {
            it.onInternetAvailable()
        }
        onView(withId(R.id.uiError)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvError)).check(matches(withText(errorText)))
    }

    @Test
    fun testRetryButtonDbEmptyUI() {
        val errorText = mAppContext.getString(R.string.db_cleared)
        val scenario = mActivityScenarioRule.scenario
        scenario.onActivity {
            it.mErrorView.showMessage(errorText)
            hideShimmerShowList(it)

        }
        onView(withId(R.id.llShimmer)).check(matches(not(isDisplayed())))
        onView(withId(R.id.rv)).check(matches(isDisplayed()))
        onView(withId(R.id.uiError)).check(matches(isDisplayed()))
        onView(withId(R.id.tvError)).check(matches(withText(errorText)))
        onView(withId(R.id.btnRetry)).perform(ViewActions.click())


    }

    @Test
    fun testRetryButtonInternetRestoreWithDBEmptyUI() {
        val errorText = mAppContext.getString(R.string.internet_available)
        val scenario = mActivityScenarioRule.scenario
        scenario.onActivity {
            hideShimmerShowList(it)
            it.mErrorView.showMessage(it.getString(R.string.db_cleared))
            it.onInternetAvailable()
        }
        onView(withId(R.id.llShimmer)).check(matches(not(isDisplayed())))
        onView(withId(R.id.uiError)).check(matches(isDisplayed()))
        onView(withId(R.id.tvError)).check(matches(withText(errorText)))
        onView(withId(R.id.btnRetry)).perform(ViewActions.click())


    }

    @Test
    fun testInternetLostUI() {
        val errorText = mAppContext.getString(R.string.no_internet)
        mActivityScenarioRule.scenario.onActivity {
            it.onInternetUnavailable()
        }
        onView(withId(R.id.uiError)).check(matches(not(isDisplayed())))
        onView(withId(R.id.tvError)).check(matches(withText(errorText)))

    }

    private fun showShimmerHideList(trendingReposActivity: TrendingReposActivity) {
        val binding = trendingReposActivity.mBinding
        binding.llShimmer.visibility = View.VISIBLE
        binding.rv.visibility = View.GONE
    }

    private fun hideShimmerShowList(trendingReposActivity: TrendingReposActivity) {
        val binding = trendingReposActivity.mBinding
        binding.llShimmer.visibility = View.GONE
        binding.rv.visibility = View.VISIBLE
    }

}