package com.example.helloandroidagain.presentation.tournament.list

import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.helloandroidagain.R
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.di.AppModule
import com.example.helloandroidagain.di.RepositoryModule
import com.example.helloandroidagain.launchFragmentInHiltContainer
import com.example.helloandroidagain.presentation.component.navigation.DirectionsHelper
import com.example.helloandroidagain.presentation.component.recyclerview.TournamentListAdapter
import com.example.helloandroidagain.presentation.tournament.create.TournamentCreateFragment.Companion.CREATE_TOURNAMENT_FRAGMENT_RESULT
import com.example.helloandroidagain.presentation.tournament.create.TournamentCreateFragment.Companion.CREATE_TOURNAMENT_RESULT_KEY
import com.example.helloandroidagain.core.util.generateRandomDate
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import fakes.FakeTournamentRepositoryImpl.Companion.NAV_TOURNAMENT
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.random.Random

@HiltAndroidTest
@UninstallModules(AppModule::class, RepositoryModule::class)
@OptIn(ExperimentalCoroutinesApi::class)
class TournamentListFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    val testDirectionsHelper: DirectionsHelper = mockk()

    @Module
    @InstallIn(SingletonComponent::class)
    inner class TestDirectionsModule {

        @Provides
        fun provideTestDirectionsHelper(): DirectionsHelper = testDirectionsHelper
    }

    @Test
    fun shouldLaunchTournamentListFragmentWithAllItemsScrollableToLast() {
        launchFragmentInHiltContainer<TournamentListFragment>()

        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<TournamentListAdapter.TournamentViewHolder>(19)
            )
        onView(withText("FakeTournament19")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldRemoveTournamentItemOnLeftSwipe() {
        launchFragmentInHiltContainer<TournamentListFragment>()

        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<TournamentListAdapter.TournamentViewHolder>(
                    10,
                    swipeLeft()
                )
            )
        Thread.sleep(1000)
        onView(withText("FakeTournament10")).check(doesNotExist())
    }

    @Test
    fun shouldDisplayTournamentItemFromResultsAtTheBottomOfTheList() {
        val tournament = Tournament(
            100,
            "FakeNewTournament100",
            Random.nextInt(2, 10),
            generateRandomDate(),
            TournamentLogo.default()
        )
        launchFragmentInHiltContainer<TournamentListFragment> {
            parentFragmentManager.setFragmentResult(
                CREATE_TOURNAMENT_FRAGMENT_RESULT,
                bundleOf(CREATE_TOURNAMENT_RESULT_KEY to tournament)
            )
        }

        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .scrollToPosition<TournamentListAdapter.TournamentViewHolder>(21)
            )
        onView(withText("FakeNewTournament100")).check(matches(isDisplayed()))
    }

    @Test
    fun shouldNavigateToExportDirectionOnTournamentItemClick() {
        val navController = mockk<NavController>(relaxed = true)
        launchFragmentInHiltContainer<TournamentListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        val direction: NavDirections = mockk(relaxed = true)
        every { testDirectionsHelper.toExportTournamentDirection(NAV_TOURNAMENT) } returns direction

        onView(withId(R.id.recyclerView))
            .perform(
                RecyclerViewActions
                    .actionOnItemAtPosition<TournamentListAdapter.TournamentViewHolder>(17, click())
            )
        verify { navController.navigate(direction) }
    }

    @Test
    fun shouldNavigateToCreateDirectionOnFabClick() {
        val navController = mockk<NavController>(relaxed = true)
        launchFragmentInHiltContainer<TournamentListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
        val direction: NavDirections = mockk(relaxed = true)
        every { testDirectionsHelper.toCreateTournamentDirection(22) } returns direction

        onView(withId(R.id.add_tournament_fab)).perform(click())

        verify { navController.navigate(direction) }
    }
}