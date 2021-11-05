package com.geekbrains.tests.automator

import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import com.geekbrains.tests.*
import com.geekbrains.tests.TEST_NUMBER
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class BehaviorTest {
    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    @Before
    fun setup() {
        uiDevice.pressHome()
        uiDevice.startAndWaitForPackageLaunchActivity(TIMEOUT)
    }

    @Test
    fun test_MainActivityStarted() {
        val editText = uiDevice.findViewById<EditText>(R.id.searchEditText)
        assertNotNull(editText)
    }

    @Test
    fun test_SearchIsPositive() {
        val editText = uiDevice.findViewById<EditText>(R.id.searchEditText)
        editText.text = "UiAutomator"
        uiDevice.findViewById<ImageButton>(R.id.search_button)
            .click()
        val changeText = uiDevice.waitForView<TextView>(R.id.totalCountTextView, TIMEOUT)

        assertEquals(changeText.text.toString(), TEST_NUMBER)
    }

    @Test
    fun test_OpenDetailsScreen() {
        val toDetails = uiDevice.findViewById<Button>(R.id.toDetailsActivityButton)
        toDetails.click()
        val changedText = uiDevice.waitForView<TextView>(R.id.totalCountTextView, TIMEOUT)

        assertEquals(changedText.text, TEST_NUMBER_OF_RESULTS_ZERO)
    }

    @Test
    fun test_IsDetailsScreenShowValidTotalCountAfterRequest() {
        uiDevice.findViewById<EditText>(R.id.searchEditText)
            .text = "UiAutomator"
        uiDevice.findViewById<ImageButton>(R.id.search_button)
            .click()
        val changedTextInMainScreen = uiDevice
            .waitForView<TextView>(R.id.totalCountTextView, TIMEOUT)
            .text
        uiDevice.findViewById<Button>(R.id.toDetailsActivityButton)
            .click()
        val changedTextInDetailsScreen = uiDevice
            .waitForView<TextView>(R.id.totalCountTextView, TIMEOUT)
            .text

        assertEquals(
            changedTextInMainScreen,
            changedTextInDetailsScreen
        )
    }
}