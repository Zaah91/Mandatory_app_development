package com.example.mandatory

import android.support.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.mandatory.ui.login.LoginFragment

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun LoginFragment() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.mandatory", appContext.packageName)

        Espresso.onView(ViewMatchers.withText("Login")) // TODO find ud af dette
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.email_input))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("zaah91@gmail.com"))
        Espresso.onView(withId(R.id.password))
            .perform(ViewActions.clearText())
            .perform(ViewActions.typeText("123456"))
            .perform(ViewActions.closeSoftKeyboard())
        Espresso.onView(withId(R.id.button_login)).perform(ViewActions.click())
        pause(2000) // to wait for Firebase response to arrive
    }

    private fun pause(millis: Long) {
        try {
            Thread.sleep(millis)
            // https://www.repeato.app/android-espresso-why-to-avoid-thread-sleep/
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}