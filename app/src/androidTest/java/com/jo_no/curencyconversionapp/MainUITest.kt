package com.jo_no.curencyconversionapp

import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.jo_no.curencyconversionapp.models.CurrencyRate
import com.jo_no.curencyconversionapp.ui.main.CurrencyAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainUITest {

    private val mockList = arrayListOf(
        CurrencyRate("GBP", 1.4),
        CurrencyRate("USD", 1.9),
        CurrencyRate("SEK", 5.2),
        CurrencyRate("HUF", 3.2)
    )

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false)

    @Before
    fun setUp() {
        activityRule.launchActivity(Intent())
    }

    @Test
    fun allElementsDisplayed() {
        onView(withId(R.id.header)).check(matches(isDisplayed()))
    }

    @Test
    fun recyclerViewPopulated() {
        val recyclerView =
            activityRule.activity.supportFragmentManager.fragments.first().view?.findViewById<RecyclerView>(
                R.id.currency_list_recycler
            )
        val adapter = recyclerView?.adapter
        (adapter as CurrencyAdapter).listItems = mockList
        onView(withId(R.id.currency_list_recycler)).check(matches(isDisplayed()))
    }
}