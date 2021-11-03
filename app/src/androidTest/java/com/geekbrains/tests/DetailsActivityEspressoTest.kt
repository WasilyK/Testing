package com.geekbrains.tests

import android.content.Context
import android.content.Intent
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import com.geekbrains.tests.automator.BehaviorTest
import com.geekbrains.tests.view.details.DetailsActivity
import junit.framework.TestCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class DetailsActivityEspressoTest {
    private lateinit var scenario: ActivityScenario<DetailsActivity>

    @Before
    fun setup() {
        scenario = ActivityScenario.launch(DetailsActivity::class.java)
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
    fun activityTextView_HasText() {
        val assertion = matches(withText("Number of results: 0"))
        onView(withId(R.id.totalCountTextView)).check(assertion)
    }

    @Test
    fun activityTextView_IsDisplayed() {
        onView(withId(R.id.totalCountTextView)).check(matches(isDisplayed()))
    }

    @Test
    fun activityTextView_IsCompletelyDisplayed() {
        onView(withId(R.id.totalCountTextView)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    fun activityButtons_AreEffectiveVisible() {
        onView(withId(R.id.incrementButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.decrementButton)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
    }

    @Test
    fun activityButtonIncrement_IsWorking() {
        onView(withId(R.id.incrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: 1")))
    }

    @Test
    fun activityButtonDecrement_IsWorking() {
        onView(withId(R.id.decrementButton)).perform(click())
        onView(withId(R.id.totalCountTextView)).check(matches(withText("Number of results: -1")))
    }

    @Test
    fun activityButtonIncrement_IsWorking_AutomatorTest() {
        val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
        val context = ApplicationProvider.getApplicationContext<Context>()
        val packageName = context.packageName

        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), BehaviorTest.TIMEOUT)

        uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
            .click()
        uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            BehaviorTest.TIMEOUT
        )

        uiDevice.findObject(By.res(packageName, "incrementButton"))
            .click()

        val changedText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
            .text

        assertEquals("Number of results: 1", changedText)
    }

    @Test
    fun activityButtonDecrement_IsWorking_AutomatorTest() {
        val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
        val context = ApplicationProvider.getApplicationContext<Context>()
        val packageName = context.packageName

        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), BehaviorTest.TIMEOUT)

        uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
            .click()
        uiDevice.wait(
            Until.findObject(By.res(packageName, "totalCountTextView")),
            BehaviorTest.TIMEOUT
        )

        uiDevice.findObject(By.res(packageName, "decrementButton"))
            .click()

        val changedText = uiDevice.findObject(By.res(packageName, "totalCountTextView"))
            .text

        assertEquals("Number of results: -1", changedText)
    }

    @After
    fun close() {
        scenario.close()
    }
}