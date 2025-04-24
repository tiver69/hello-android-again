package com.example.helloandroidagain.tournament_presentation.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavDirections
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloandroidagain.tournament_domain.model.Tournament
import com.example.helloandroidagain.tournament_presentation.component.recyclerview.ItemLeftSwipeHelper
import com.example.helloandroidagain.tournament_presentation.component.recyclerview.TournamentClickListener
import com.example.helloandroidagain.tournament_presentation.component.recyclerview.TournamentListAdapter
import com.example.helloandroidagain.tournament_presentation.component.recyclerview.TournamentSwipeListener
import com.example.helloandroidagain.tournament_presentation.create.TournamentCreateFragment.Companion.CREATE_TOURNAMENT_FRAGMENT_RESULT
import com.example.helloandroidagain.tournament_presentation.create.TournamentCreateFragment.Companion.CREATE_TOURNAMENT_RESULT_KEY
import com.example.helloandroidagain.tournament_presentation.databinding.FragmentTournamentListBinding
import com.example.helloandroidagain.tournament_presentation.navigation.DirectionsHelper
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TournamentListFragment @Inject constructor() :
    Fragment(),
    TournamentSwipeListener,
    TournamentClickListener {

    @Inject
    lateinit var analytics: FirebaseAnalytics

    @Inject
    lateinit var adapter: TournamentListAdapter

    @Inject
    lateinit var directionsHelper: DirectionsHelper

    private lateinit var binding: FragmentTournamentListBinding

    private val viewModel: TournamentListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTournamentListBinding.inflate(inflater, container, false)
        viewModel.restoreTournaments()
        val layoutManager = LinearLayoutManager(container?.context)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )
        ItemTouchHelper(ItemLeftSwipeHelper(this)).attachToRecyclerView(binding.recyclerView)
        adapter.onClickListener = this
        subscribeToTournamentList()
        binding.addTournamentFab.setOnClickListener {
            val toCreateTournament: NavDirections =
                directionsHelper.toCreateTournamentDirection(
                    (binding.recyclerView.adapter?.itemCount ?: 0) + 1
                )
            findNavController().navigate(toCreateTournament)
        }
        parentFragmentManager.setFragmentResultListener(
            CREATE_TOURNAMENT_FRAGMENT_RESULT,
            viewLifecycleOwner
        ) { _, bundle ->
            tournamentCreated(bundle.getParcelable(CREATE_TOURNAMENT_RESULT_KEY)!!)
        }

        return binding.root
    }

    private fun subscribeToTournamentList() {
        lifecycleScope.launch {
            viewModel.tournamentsFlow.collect {
                adapter.tournaments = it
            }
        }
    }

    override fun onItemClick(tournament: Tournament) {
        val toExportTournament: NavDirections =
            directionsHelper.toExportTournamentDirection(tournament)
        findNavController().navigate(toExportTournament)
    }

    override fun onRemoveSwipe(tournamentPosition: Int) {
        Log.d("TournamentListFragment", "On remove swipe at $tournamentPosition")
        analytics.logEvent("remove_tournament") {
            param(FirebaseAnalytics.Param.ITEM_ID, tournamentPosition.toDouble())
        }
        viewModel.removeTournament(tournamentPosition)
    }

    private fun tournamentCreated(tournament: Tournament) {
        analytics.logEvent("create_tournament") {
            param(FirebaseAnalytics.Param.ITEM_NAME, tournament.name)
        }
        viewModel.createTournament(tournament)
    }
}