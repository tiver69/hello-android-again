package com.example.helloandroidagain.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloandroidagain.recyclerview.ItemLeftSwipeHelper
import com.example.helloandroidagain.recyclerview.TournamentSwipeListener
import com.example.helloandroidagain.recyclerview.TournamentListAdapter
import com.example.helloandroidagain.databinding.FragmentTournamentListBinding
import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.model.TournamentListListener
import com.example.helloandroidagain.model.TournamentService
import com.example.helloandroidagain.navigation.CreateTournamentResultListener
import com.example.helloandroidagain.navigation.router

class TournamentListFragment : Fragment(), TournamentSwipeListener, TournamentListListener,
    CreateTournamentResultListener {

    private lateinit var binding: FragmentTournamentListBinding
    private lateinit var adapter: TournamentListAdapter
    private lateinit var tournamentService: TournamentService

    override fun onCreate(savedInstanceState: Bundle?) {
        adapter = TournamentListAdapter()
        tournamentService = TournamentService(this, requireContext())
        super.onCreate(savedInstanceState)
    }

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
        adapter.tournaments = tournamentService.getTournaments()
        binding.addTournamentFab.setOnClickListener {
            router().navToCreateTournament(
                tournamentService.getTournaments().size + 1
            )
        }
        router().listenToCreateResult(viewLifecycleOwner, this)

        return binding.root
    }

    override fun onStop() {
        tournamentService.saveTournaments()
        super.onStop()
    }

    override fun onRemoveSwipe(tournamentPosition: Int) {
        tournamentService.removeTournament(tournamentPosition)
        Log.d("TournamentListFragment", "On click pressed on $tournamentPosition")
    }

    override fun tournamentListUpdated(tournamentList: List<Tournament>) {
        adapter.tournaments = tournamentList
    }

    override fun tournaemntCreated(tournament: Tournament) {
        tournamentService.addTournament(tournament)
    }
}