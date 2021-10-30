package com.geekbrains.tests.view.search

import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.geekbrains.tests.R
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Test

class MainActivityEspressoTest {

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
    fun activity_AssertNotNull() {
        scenario.onActivity {
            TestCase.assertNotNull(it)
        }
    }

    @Test
    fun activity_IsResumed() {
        TestCase.assertEquals(Lifecycle.State.RESUMED, scenario.state)
    }

    @Test
    fun activityTextView_NotNull() {
        scenario.onActivity {
            val totalCountTextView = it.findViewById<TextView>(R.id.totalCountTextView)
            TestCase.assertNotNull(totalCountTextView)
        }
    }

    @Test
    fun activityProgressbar_NotNull() {
        scenario.onActivity {
            val totalCountTextView = it.findViewById<ProgressBar>(R.id.progressBar)
            TestCase.assertNotNull(totalCountTextView)
        }
    }

    @Test
    fun activityEditTextView_NotNull() {
        scenario.onActivity {
            val searchEditText = it.findViewById<EditText>(R.id.searchEditText)
            TestCase.assertNotNull(searchEditText)
        }
    }

    @Test
    fun activityEditTextView_isCompleteDisplayed() {
        onView(withId(R.id.searchEditText)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityEditTextView_hasHint() {
        onView(withId(R.id.searchEditText))
            .check(matches(withHint("Enter keyword e.g. android")))
    }

    @Test
    fun activityButton_NotNull() {
        scenario.onActivity {
            val button = it.findViewById<Button>(R.id.toDetailsActivityButton)
            TestCase.assertNotNull(button)
        }
    }

    @Test
    fun activityButton_isCompleteDisplayed() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityButton_hasText() {
        onView(withId(R.id.toDetailsActivityButton)).check(matches(withText("TO DETAILS")))
    }
}