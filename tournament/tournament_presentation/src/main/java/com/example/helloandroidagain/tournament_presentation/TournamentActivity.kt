package com.example.helloandroidagain.tournament_presentation

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.helloandroidagain.tournament_presentation.databinding.ActivityTournamentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TournamentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTournamentBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTournamentBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
    }

    override fun onResume() {
        super.onResume()
        navController = findNavController(R.id.tournamentNavContainer)
        setupActionBarWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateToolbar(destination.id)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateToolbar(destinationId: Int) {
        when (destinationId) {
            R.id.createTournamentFragment ->
                supportActionBar?.title = getString(R.string.create_tournament_fragment_name)

            else -> supportActionBar?.title = getString(R.string.app_name)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(navController.previousBackStackEntry != null)
    }
}
