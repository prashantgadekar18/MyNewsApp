package com.pg.mynewsapp.ui.details

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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
    fun test_IsTextViewDescriptionVisible(){
        ActivityScenario.launch(NewsDetailsActivity::class.java)
        onView(withId(R.id.imageViewBanner)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewTitle)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewDescription)).check(matches(isDisplayed()))
        onView(withId(R.id.textViewSource)).check(matches(isDisplayed()))
    }


}