package com.geekbrains.tests.view.search

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.geekbrains.tests.R
import org.hamcrest.Matcher
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

        Espresso.onView(ViewMatchers.isRoot()).perform(delay())
        Espresso.onView(ViewMatchers.withId(R.id.totalCountTextView))
            .check(ViewAssertions.matches(ViewMatchers.withText("Number of results: 2650")))
    }

    private fun delay(): ViewAction? {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()
            override fun getDescription(): String = "wait for $2 seconds"
            override fun perform(uiController: UiController, v: View?) {
                uiController.loopMainThreadForAtLeast(2000)
            }
        }
    }
}