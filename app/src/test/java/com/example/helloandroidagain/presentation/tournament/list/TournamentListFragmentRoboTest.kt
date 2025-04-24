package com.example.helloandroidagain.presentation.tournament.list

import com.example.helloandroidagain.di.AppModule
import com.example.helloandroidagain.util.launchFragmentInHiltContainer
import com.example.helloandroidagain.tournament_data.di.TournamentDataModule
import com.example.helloandroidagain.tournament_presentation.component.recyclerview.TournamentType
import com.example.helloandroidagain.tournament_presentation.list.TournamentListFragment
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(AppModule::class, TournamentDataModule::class)
class TournamentListFragmentRoboTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun `test tournament list assigns correct view type for items`() {
        launchFragmentInHiltContainer<TournamentListFragment> {
            assertEquals(TournamentType.ACTIVE.viewType, adapter.getItemViewType(0))
            assertEquals(TournamentType.OUTDATED.viewType, adapter.getItemViewType(1))
        }
    }

    @Test
    fun `test tournament list retains after activity recreation`() {
        val scenario = launchFragmentInHiltContainer<TournamentListFragment> {
            assertEquals(2, adapter.itemCount)
        }
        scenario.recreate()
        scenario.onActivity { activity ->
            val currentFragment =
                activity.supportFragmentManager.fragments[0] as TournamentListFragment
            assertEquals(2, currentFragment.adapter.itemCount)
        }
    }
}