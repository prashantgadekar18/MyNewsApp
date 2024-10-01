package com.pg.mynewsapp

import com.pg.mynewsapp.ui.MainActivityTest
import com.pg.mynewsapp.ui.details.NewsDetailsActivityTest
import com.pg.mynewsapp.ui.topheadline.TopHeadlineActivityTest
import org.junit.runner.RunWith
import org.junit.runners.Suite

@RunWith(Suite::class)
@Suite.SuiteClasses(
    MainActivityTest::class,
    TopHeadlineActivityTest::class,
    NewsDetailsActivityTest::class
)
class ActivityTestSuite