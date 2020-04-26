package com.emredogan.espresso_demonstration

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import junit.framework.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CreatNewDestinationTest {
    @get:Rule
    val MainActivity = ActivityTestRule(MainActivity::class.java)


    @Test
    fun createNewItem() {

        val location_name_antalya = "Antalya"
        val location_name_kapadokya = "Kapadokya"
        val season_name_summer = "Summer"
        val season_name_spring = "Spring"

        val location_kapadokya = Destination(location_name_kapadokya, season_name_summer, MainActivity.activity.getDrawable(R.drawable.kapadokya))
        val location_antalya = Destination(location_name_antalya, season_name_spring, MainActivity.activity.getDrawable(R.drawable.kaputas))

        onView(withId(R.id.addPlayerFab)).perform(click())
        onView(withId(R.id.location_name_edit_text)).perform(typeText(location_name_kapadokya))
        onView(withId(R.id.season_edit_text)).perform(typeText(season_name_summer))

        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.saveButton)).perform(click())

        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val first_added_location = LocationDatabase.location_list.last()
        assertEquals(first_added_location.name, location_kapadokya.name)
        assertEquals(first_added_location.number, location_kapadokya.number)
        assert(first_added_location.drawableImage!!.equals(location_kapadokya.number))

        onView(withId(R.id.addPlayerFab)).perform(click())
        onView(withId(R.id.add_image_button)).perform(click())
        onView(withId(R.id.location_name_edit_text)).perform(typeText(location_name_antalya))
        onView(withId(R.id.season_edit_text)).perform(typeText(season_name_spring))

        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        onView(withId(R.id.saveButton)).perform(click())

        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        val second_added_location = LocationDatabase.location_list.last()
        assertEquals(second_added_location.name, location_antalya.name)
        assertEquals(second_added_location.number, location_antalya.number)
        assert(second_added_location.drawableImage!!.equals(location_antalya.number))

        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(R.id.playerRecyclerList))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerViewAdapter.PlayerViewHolder>(0, click()))

        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText("Kapadokya")))

        try {
            Thread.sleep(700)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }


    }
}