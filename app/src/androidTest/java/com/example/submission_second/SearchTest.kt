package com.example.submission_second

import android.widget.AutoCompleteTextView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchTest {

    private lateinit var stringEmpty: String
    private lateinit var stringToBeTyped: String

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Before
    fun initValidString() {
        stringEmpty = "ar"
        stringToBeTyped = "arsenal"
    }


    @Test
    fun testRecyclerViewBehaviour() {
        /**
         * skip splash Screen
         */
        Thread.sleep(3000)
        /**
         * check progressBar is showed
         * check rvClub is showed
         */
        onView(withId(R.id.progressBar))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rvClub)).check(matches(isDisplayed()))

        /**
         * access search view page with click searchView
         */
        onView(withId(R.id.searchView)).perform(click())

        /**
         * landing in searchView page
         * perform search_View
         * fill the AutoCompleteTextView with value stringEmpty
         */
        onView(withId(R.id.search_View)).perform(click())
        onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            ViewActions.typeText(stringEmpty)
        )
            .perform(ViewActions.pressImeActionButton())

        /**
         * remove value in AutoCompleteTextView
         */
        onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java)).perform(ViewActions.clearText())
            .perform(ViewActions.pressImeActionButton())

        /**
         * Search Again and Close Keyboard
         */
        onView(ViewMatchers.isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            ViewActions.typeText(stringToBeTyped),
            ViewActions.closeSoftKeyboard()
        )
            .perform(ViewActions.pressImeActionButton())
    }
}