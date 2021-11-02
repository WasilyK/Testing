package com.geekbrains.tests.automator

import android.widget.TextView
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiScrollable
import androidx.test.uiautomator.UiSelector
import org.junit.Assert.assertTrue
import org.junit.Test

class OpenOtherAppsTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())

    @Test
    fun test_OpenSettings() {
        uiDevice.pressHome()
        uiDevice.swipe(
            uiDevice.displayWidth / 2,
            uiDevice.displayHeight - 50,
            uiDevice.displayWidth / 2,
            0,
            5
        )
        val appViews = UiScrollable(UiSelector().scrollable(true))
        appViews.setAsHorizontalList()
        val settingsApp = appViews
            .getChildByText(
                UiSelector().className(TextView::class.java.name),
                "Settings"
            )

        settingsApp.clickAndWaitForNewWindow()

        val settingsValidation =
            uiDevice.findObject(UiSelector().packageName("com.android.settings"))

        assertTrue(settingsValidation.exists())
    }
}