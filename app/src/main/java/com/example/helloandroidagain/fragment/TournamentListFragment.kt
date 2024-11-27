package com.example.helloandroidagain.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloandroidagain.TournamentActionListener
import com.example.helloandroidagain.TournamentListAdapter
import com.example.helloandroidagain.databinding.FragmentTournamentListBinding
import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.model.TournamentService
import com.example.helloandroidagain.navigation.router

class TournamentListFragment : Fragment(), TournamentActionListener {

    private lateinit var binding: FragmentTournamentListBinding
    private lateinit var adapter: TournamentListAdapter
    private lateinit var tournamentService: TournamentService

    override fun onCreate(savedInstanceState: Bundle?) {
        adapter = TournamentListAdapter(this)
        tournamentService = TournamentService()
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
        adapter.tournaments = tournamentService.getTournaments()
        binding.addTournamentFab.setOnClickListener {
            router().navToCreateTournament(
                tournamentService.getTournaments().size + 1
            )
        }

        return binding.root
    }

    override fun onTournamentRemove(tournament: Tournament) {
        Log.d("TournamentListFragment", "On click pressed by ${tournament.id}")
    }
}