package com.example.helloandroidagain

import android.os.Bundle
import android.os.Parcelable
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.helloandroidagain.databinding.ActivityMainBinding
import com.example.helloandroidagain.fragment.FragmentToolbar
import com.example.helloandroidagain.fragment.TournamentCreateFragment
import com.example.helloandroidagain.fragment.TournamentListFragment
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
        supportFragmentManager.setFragmentResultListener("CREATE_TOURNAMENT_RESULT", owner, FragmentResultListener {
                _, bundle -> listener.tournaemntCreated(bundle.getParcelable("CREATE_TOURNAMENT_RESULT_KEY")!!)
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    private fun updateToolbar() {
        val fragment = currentFragment
        if (fragment is FragmentToolbar) {
            binding.toolbar.title = fragment.getFragmentTitle()
            val toolbarAction = binding.toolbar.menu.add(fragment.getFragmentAction().actionHint)
            toolbarAction.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS)
            toolbarAction.icon = DrawableCompat.wrap(
                ContextCompat.getDrawable(
                    this,
                    fragment.getFragmentAction().actionIcRes
                )!!
            )
            toolbarAction.setOnMenuItemClickListener {
                fragment.getFragmentAction().onFragmentAction.run()
                return@setOnMenuItemClickListener true
            }
        } else {
            binding.toolbar.title = "Android Mentoring"
            binding.toolbar.menu.clear()
        }

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            supportActionBar?.setDisplayHomeAsUpEnabled(false)
        }
    }
}