package com.example.helloandroidagain.presentation.tournament.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.helloandroidagain.R
import com.example.helloandroidagain.di.AppModule
import com.example.helloandroidagain.di.RepositoryModule
import com.example.helloandroidagain.launchFragmentInHiltContainer
import com.example.helloandroidagain.presentation.component.recyclerview.TournamentListAdapter
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class, RepositoryModule::class)
class TournamentListFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    @OptIn(ExperimentalCoroutinesApi::class)
    fun shouldLaunchTournamentListFragmentWithAllItemsScrollableToLast() {
        launchFragmentInHiltContainer<TournamentListFragment>()

        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<TournamentListAdapter.TournamentViewHolder>(19)
            )
        onView(withText("Tournament19")).check(matches(isDisplayed()))
    }
}