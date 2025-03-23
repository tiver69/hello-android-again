package com.example.helloandroidagain

import androidx.navigation.findNavController
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.example.helloandroidagain.di.AppModule
import com.example.helloandroidagain.di.RepositoryModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.LooperMode

@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
//@LooperMode(LooperMode.Mode.PAUSED)
@UninstallModules(AppModule::class, RepositoryModule::class)
class TournamentActivityRoboTest {

    @get:Rule(order = 0)
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var activityScenarioRule: ActivityScenarioRule<TournamentActivity> =
        ActivityScenarioRule(TournamentActivity::class.java)

    @Before
    fun setUp() {
        hiltAndroidRule.inject()
    }

    @Test
    fun `tournament activity should be launched with Tournament List Fragment by default`() {
        activityScenarioRule.scenario.onActivity { activity ->
            val navController =
                (activity as TournamentActivity).findNavController(R.id.tournamentNavContainer)
            assertNotNull(navController)
            assertEquals(2, navController.currentBackStack.value.size)
            assertEquals(
                R.id.tournamentListFragment,
                navController.currentBackStack.value[1].destination.id
            )
        }
    }
}