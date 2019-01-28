package com.dicoding.millatip.footballapps.presentation.ui.main

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.pressBack
import android.support.test.espresso.IdlingRegistry
import android.support.test.espresso.action.ViewActions.*
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.dicoding.millatip.footballapps.R.id.*
import com.dicoding.millatip.footballapps.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class InstrumentationTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setUp(){
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun testAppBehaviour(){

        onView(withId(rvPrevMatch)).check(matches(isDisplayed()))
        onView(withId(action_search)).check(matches(isDisplayed()))
        onView(withId(action_search)).perform(click())
        onView(withText("isOpened")).check(matches(isDisplayed()))
        pressBack()
        onView(withId(rvPrevMatch)).check(matches(isDisplayed()))
        onView(withId(rvPrevMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(6))
        onView(withId(rvPrevMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(6, click()))

        onView(withId(add_to_fav)).check(matches(isDisplayed()))
        onView(withId(add_to_fav)).perform(click())
        onView(withText("Added to favorite")).check(matches(isDisplayed()))
        pressBack()

        onView(withId(bottomNavigationView)).check(matches(isDisplayed()))
        onView(withId(action_fav)).check(matches(isDisplayed()))
        onView(withId(action_fav)).perform(click())

        onView(withId(rvFavoriteMatch)).check(matches(isDisplayed()))
        onView(withId(rvFavoriteMatch)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
        onView(withId(rvFavoriteMatch)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(add_to_fav)).check(matches(isDisplayed()))
        onView(withId(add_to_fav)).perform(click())
        onView(withText("Removed from favorite")).check(matches(isDisplayed()))
        pressBack()

        onView(withId(bottomNavigationView)).check(matches(isDisplayed()))
        onView(withId(action_team)).check(matches(isDisplayed()))
        onView(withId(action_team)).perform(click())
        onView(withId(action_search)).check(matches(isDisplayed()))
        onView(withId(action_search)).perform(click()).check(matches(isDisplayed()))
        onView(withId(action_search)).perform(typeText("Arsenal"))
        onView(withId(action_search)).perform(closeSoftKeyboard())
    }

    @After
    fun tearDown(){
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }
}
