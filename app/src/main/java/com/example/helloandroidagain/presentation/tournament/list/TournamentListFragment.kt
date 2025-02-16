package com.example.helloandroidagain.presentation.tournament.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.databinding.FragmentTournamentListBinding
import com.example.helloandroidagain.presentation.component.recyclerview.ItemLeftSwipeHelper
import com.example.helloandroidagain.presentation.component.recyclerview.TournamentListAdapter
import com.example.helloandroidagain.presentation.component.recyclerview.TournamentSwipeListener
import com.example.helloandroidagain.presentation.navigation.CreateTournamentResultListener
import com.example.helloandroidagain.presentation.navigation.router
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class TournamentListFragment @Inject constructor() : Fragment(), TournamentSwipeListener,
    CreateTournamentResultListener {

    private lateinit var binding: FragmentTournamentListBinding

    @Inject
    lateinit var adapter: TournamentListAdapter

    private val viewModel: TournamentListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTournamentListBinding.inflate(inflater, container, false)
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
        subscribeToTournamentList()
        binding.addTournamentFab.setOnClickListener {
            router().navToCreateTournament((binding.recyclerView.adapter?.itemCount ?: 0) + 1)
        }
        router().listenToCreateResult(viewLifecycleOwner, this)

        return binding.root
    }

    private fun subscribeToTournamentList() {
        lifecycleScope.launch {
            viewModel.tournamentsFlow.collect {
                adapter.tournaments = it
            }
        }
    }

    override fun onStop() {
        viewModel.saveTournaments()
        super.onStop()
    }

    override fun onRemoveSwipe(tournamentPosition: Int) {
        Log.d("TournamentListFragment", "On click pressed on $tournamentPosition")
        viewModel.removeTournament(tournamentPosition)
    }

    override fun tournamentCreated(tournament: Tournament) {
        viewModel.createTournament(tournament)
    }
}