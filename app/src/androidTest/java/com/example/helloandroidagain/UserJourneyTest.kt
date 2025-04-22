package com.example.helloandroidagain

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBackUnconditionally
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressBack
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.helloandroidagain.auth_presentation.AuthActivity
import com.example.helloandroidagain.di.TestAppModule
import com.example.helloandroidagain.di.TestRepositoryModule
import com.example.helloandroidagain.presentation.component.glide.CustomActionIdleRequestListener
import com.example.helloandroidagain.presentation.component.recyclerview.TournamentListAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.After
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.concurrent.atomic.AtomicReference

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@UninstallModules(TestAppModule::class, TestRepositoryModule::class)
class UserJourneyTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityRule = ActivityScenarioRule(AuthActivity::class.java)

    @Before
    fun setup() {
        hiltRule.inject()
        IdlingRegistry.getInstance().register(CustomActionIdleRequestListener)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(CustomActionIdleRequestListener)
    }

    @Test
    fun userJourney_navigatesFromStartToEnd() {
        // Auth Activity Navigation
        checkIfViewDisplayed(com.example.helloandroidagain.auth_presentation.R.id.authActivity)
        performClickOnView(
            com.example.helloandroidagain.auth_presentation.R.id.auth_landing_login_button
        )
        performClickOnView(
            com.example.helloandroidagain.auth_presentation.R.id.auth_profile_manage_tournament_button
        )

        // Tournament Activity Navigation
        // Tournament List Fragment
        checkIfViewDisplayed(R.id.tournamentActivity)
        performClickOnView(R.id.add_tournament_fab)

        // Tournament Create Fragment
        val initialImage = captureImageDrawable(R.id.tournament_create_logo)
        performClickOnView(R.id.tournament_create_regenerate_image_button)
        val regeneratedImage = captureImageDrawable(R.id.tournament_create_logo)
        assertNotEquals(initialImage, regeneratedImage)
        performClickOnView(R.id.tournament_create_save_button)

        // Tournament List Fragment
        val lastRecyclerViewPosition = getLastRecyclerViewItemPosition()
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    lastRecyclerViewPosition,
                    click()
                )
            )

        // Tournament Export Fragment
        checkIfViewDisplayed(R.id.tournament_export_save_button)
        onView(isRoot()).perform(pressBack())

        // Tournament List Fragment
        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<TournamentListAdapter.TournamentViewHolder>(
                    lastRecyclerViewPosition,
                    swipeLeft()
                )
            )
        onView(withId(R.id.recyclerView)).check(
            matches(
                hasMinimumChildCount(
                    lastRecyclerViewPosition - 1
                )
            )
        )
        onView(isRoot()).perform(pressBack())

        // Auth Activity Navigation
        checkIfViewDisplayed(com.example.helloandroidagain.auth_presentation.R.id.authActivity)
        performClickOnView(
            com.example.helloandroidagain.auth_presentation.R.id.auth_profile_logout_button
        )
        checkIfViewDisplayed(
            com.example.helloandroidagain.auth_presentation.R.id.auth_landing_login_button
        )

        pressBackUnconditionally()
    }

    private fun checkIfViewDisplayed(id: Int) = onView(withId(id)).check(matches(isDisplayed()))

    private fun performClickOnView(id: Int) {
        checkIfViewDisplayed(id)
        onView(withId(id)).perform(click())
    }

    private fun getLastRecyclerViewItemPosition(): Int {
        var itemCount = 0
        onView(withId(R.id.recyclerView)).check { view, _ ->
            val recyclerView = view as RecyclerView
            itemCount = recyclerView.adapter?.itemCount ?: 0
        }
        return if (itemCount > 0) itemCount - 1 else 0
    }

    private fun captureImageDrawable(viewId: Int): Drawable {
        val drawableHolder = AtomicReference<Drawable>()
        onView(withId(viewId)).check { view, _ ->
            drawableHolder.set((view as ImageView).drawable)
        }
        return drawableHolder.get()
    }
}