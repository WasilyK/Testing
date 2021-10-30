package com.geekbrains.tests.view.search

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.geekbrains.tests.R
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityTest {
    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @After
    fun close() {
        scenario.close()
    }

    @Test
    fun activitySearch_IsWorking() {
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.replaceText("algol"), ViewActions.closeSoftKeyboard())
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())

        Espresso.onView(ViewMatchers.withId(R.id.totalCountTextView))
                .check(ViewAssertions.matches(ViewMatchers.withText("Number of results: 42")))

    }
}