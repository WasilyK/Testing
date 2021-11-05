package com.geekbrains.tests

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.annotation.IdRes
import androidx.test.core.app.ApplicationProvider
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until
import com.geekbrains.tests.automator.BehaviorTest

internal const val TEST_NUMBER = 42
internal const val TEST_NUMBER_OF_RESULTS_ZERO = "Number of results: 0"
internal const val TEST_NUMBER_OF_RESULTS_PLUS_1 = "Number of results: 1"
internal const val TEST_NUMBER_OF_RESULTS_MINUS_1 = "Number of results: -1"
internal const val TIMEOUT = 3000L

fun <T: View> UiDevice.findViewById(@IdRes id: Int): UiObject2 {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val packageName = context.packageName
    val resEntryName = context.resources.getResourceEntryName(id)
    return this.findObject(By.res(packageName, resEntryName))
}

fun <T: View> UiDevice.waitForView(@IdRes id: Int, timeout: Long): UiObject2 {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val packageName = context.packageName
    val resEntryName = context.resources.getResourceEntryName(id)
    return this.wait(
        Until.findObject(By.res(packageName, resEntryName)),
        timeout
    )
}

fun UiDevice.startAndWaitForPackageLaunchActivity(timeout: Long) {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val packageName = context.packageName
    val intent = context.packageManager.getLaunchIntentForPackage(packageName)
    intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    context.startActivity(intent)
    this.wait(Until.hasObject(By.pkg(packageName).depth(0)), timeout)
}