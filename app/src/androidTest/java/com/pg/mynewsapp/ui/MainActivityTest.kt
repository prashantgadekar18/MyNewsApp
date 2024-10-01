package com.pg.mynewsapp.ui

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.pg.mynewsapp.R
import com.pg.mynewsapp.ui.search.SearchActivity
import com.pg.mynewsapp.ui.sources.SourcesActivity
import com.pg.mynewsapp.ui.topheadline.TopHeadlineActivity
import com.pg.mynewsapp.ui.topheadline.TopHeadlineActivityByList
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{

    @Before
    fun setup() {
        // Initialize Espresso Intents before each test
        Intents.init()
    }

    @Test
    fun testIsActivityInView() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.activity_main_view)).check(matches(isDisplayed()))
    }

    @Test
    fun checkForActivity() {
        ActivityScenario.launch(MainActivity::class.java)
        val currentActivity = getTopActivity()
        assertTrue(currentActivity?.javaClass == MainActivity::class.java)
    }

    private fun getTopActivity(): Activity? {
        var activity : Activity ?= null
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            val resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(
                Stage.RESUMED)
            if (resumedActivities.iterator().hasNext()) {
                resumedActivities.iterator().next()?.let {
                     activity = it
                }
            }
        }
        return activity
    }

    @Test
    fun testButtonClicksOpenTopHeadLineActivity(){
         ActivityScenario.launch(MainActivity::class.java)
        // Simulate button click
        onView(withId(R.id.topHeadlineButton)).perform(click())

        // Check that the intent to open SecondActivity was triggered
        Intents.intended(hasComponent(TopHeadlineActivity::class.java.name))
    }

    @Test
    fun testButtonClicksOpenTopHeadlineActivityByList(){
        ActivityScenario.launch(MainActivity::class.java)
        // Simulate button click
        onView(withId(R.id.topHeadlineListButton)).perform(click())

        // Check that the intent to open SecondActivity was triggered
        Intents.intended(hasComponent(TopHeadlineActivityByList::class.java.name))
    }

    @Test
    fun testButtonClicksOpenSourcesActivity(){
        ActivityScenario.launch(MainActivity::class.java)
        // Simulate button click
        onView(withId(R.id.newsSourceButton)).perform(click())

        // Check that the intent to open SecondActivity was triggered
        Intents.intended(hasComponent(SourcesActivity::class.java.name))
    }
    @Test
    fun testButtonClicksOpenSearchActivity(){
        ActivityScenario.launch(MainActivity::class.java)
        // Simulate button click
        onView(withId(R.id.searchButton)).perform(click())

        // Check that the intent to open SecondActivity was triggered
        Intents.intended(hasComponent(SearchActivity::class.java.name))
    }

    @After
    fun tearDown() {
        // Release Espresso Intents after each test
        Intents.release()
    }


}