package com.pg.mynewsapp.ui.details

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.pg.mynewsapp.R
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class NewsDetailsActivityTest{

    @Test
    fun test_NewsDetailsActivityView(){
        ActivityScenario.launch(NewsDetailsActivity::class.java)
        onView(withId(R.id.imageViewBanner)).check(matches(isDisplayed()))
    }

    @Test
    fun testTextViewDisplaysCorrectText() {
        ActivityScenario.launch(NewsDetailsActivity::class.java)
        // Verify that the TextView has the expected text
        onView(withId(R.id.textViewTitle)).check(matches(withText("")))
    }

}