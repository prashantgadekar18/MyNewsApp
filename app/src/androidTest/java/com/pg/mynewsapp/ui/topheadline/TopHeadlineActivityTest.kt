package com.pg.mynewsapp.ui.topheadline

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.pg.mynewsapp.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class TopHeadlineActivityTest{

//    @get:Rule
//    val activityRule = ActivityScenarioRule(TopHeadlineActivity::class.java)
    @Test
    fun testRecyclerViewIsDisplay() {
        ActivityScenario.launch(TopHeadlineActivity::class.java)
        onView(withId(R.id.recyclerView))
            .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }


    @Test
    fun testProgressBarIsDisplay() {
        ActivityScenario.launch(TopHeadlineActivity::class.java)
        onView(withId(R.id.progressBar)).check(matches(isDisplayed()))
    }

}