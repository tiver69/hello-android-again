package com.example.helloandroidagain

import android.os.Bundle
import android.os.Parcelable
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import com.example.helloandroidagain.databinding.ActivityMainBinding
import com.example.helloandroidagain.fragment.tournament.create.FragmentToolbar
import com.example.helloandroidagain.fragment.tournament.create.TournamentCreateFragment
import com.example.helloandroidagain.fragment.tournament.list.TournamentListFragment
import com.example.helloandroidagain.navigation.CreateTournamentResultListener
import com.example.helloandroidagain.navigation.Router

class MainActivity : AppCompatActivity(), Router {

    private lateinit var binding: ActivityMainBinding

    private val currentFragment: Fragment?
        get() = supportFragmentManager.findFragmentById(R.id.fragmentContainer)

    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(
            fm: FragmentManager,
            f: Fragment,
            v: View,
            savedInstanceState: Bundle?
        ) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            updateToolbar()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, TournamentListFragment())
                .commit()
        }
        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        supportFragmentManager.unregisterFragmentLifecycleCallbacks(fragmentListener)
    }

    override fun navToCreateTournament(nextTournamentCount: Int) {
        supportFragmentManager
            .beginTransaction()
            .addToBackStack(null)
            .replace(
                R.id.fragmentContainer,
                TournamentCreateFragment.newInstance(nextTournamentCount)
            )
            .commit()
    }

    override fun navToTournamentList() {
        supportFragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun navBack() {
        onBackPressedDispatcher.onBackPressed()
    }

    override fun createResult(tournament: Parcelable) {
        supportFragmentManager.setFragmentResult(
            "CREATE_TOURNAMENT_RESULT",
            bundleOf("CREATE_TOURNAMENT_RESULT_KEY" to tournament)
        )
    }

    override fun listenToCreateResult(
        owner: LifecycleOwner,
        listener: CreateTournamentResultListener
    ) {
        supportFragmentManager.setFragmentResultListener(
            "CREATE_TOURNAMENT_RESULT",
            owner
        ) { _, bundle ->
            listener.tournamentCreated(bundle.getParcelable("CREATE_TOURNAMENT_RESULT_KEY")!!)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateToolbar() {
        val fragment = currentFragment
        if (fragment is FragmentToolbar) {
            binding.toolbar.title = getString(fragment.getFragmentTitle())
        } else {
            binding.toolbar.title = getString(R.string.app_name)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(supportFragmentManager.backStackEntryCount > 0)
    }
}