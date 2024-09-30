package com.pg.mynewsapp.ui

import android.app.Activity
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry
import androidx.test.runner.lifecycle.Stage
import com.pg.mynewsapp.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class MainActivityTest{
    @Test
    fun testIsActivityInView() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.activity_main_view)).check(matches(isDisplayed()))
    }

    @Test
    fun checkForActivity() {
        val activityScenario = ActivityScenario.launch(MainActivity::class.java)
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
}