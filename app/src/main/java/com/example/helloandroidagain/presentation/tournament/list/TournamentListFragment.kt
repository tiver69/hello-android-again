package com.example.helloandroidagain.presentation.tournament.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloandroidagain.App
import com.example.helloandroidagain.presentation.component.recyclerview.ItemLeftSwipeHelper
import com.example.helloandroidagain.presentation.component.recyclerview.TournamentSwipeListener
import com.example.helloandroidagain.presentation.component.recyclerview.TournamentListAdapter
import com.example.helloandroidagain.databinding.FragmentTournamentListBinding
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.presentation.navigation.CreateTournamentResultListener
import com.example.helloandroidagain.presentation.navigation.router
import javax.inject.Inject

class TournamentListFragment @Inject constructor() : Fragment(), TournamentSwipeListener, CreateTournamentResultListener,
    TournamentListContract.View {

    private lateinit var binding: FragmentTournamentListBinding
    @Inject
    lateinit var adapter: TournamentListAdapter
    @Inject
    lateinit var presenter: TournamentListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity().application as App).appComponent.injectTournamentListFragment(this)
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
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                layoutManager.orientation
            )
        )
        ItemTouchHelper(ItemLeftSwipeHelper(this)).attachToRecyclerView(binding.recyclerView)
        presenter.attachView(this)
        binding.addTournamentFab.setOnClickListener {
            router().navToCreateTournament((binding.recyclerView.adapter?.itemCount ?: 0) + 1)
        }
        router().listenToCreateResult(viewLifecycleOwner, this)

        return binding.root
    }

    override fun updateTournamentList(tournaments: List<Tournament>) {
        binding.recyclerView.adapter = adapter //This fixes incorrect displaying old/current tournaments on start but causes removing from scrollable part of list jump to start
        adapter.tournaments = tournaments
    }

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }

    override fun onStop() {
        presenter.saveTournaments()
        super.onStop()
    }

    override fun onRemoveSwipe(tournamentPosition: Int) {
        Log.d("TournamentListFragment", "On click pressed on $tournamentPosition")
        presenter.removeTournament(tournamentPosition)
    }

    override fun tournamentCreated(tournament: Tournament) {
        presenter.createTournament(tournament)
    }
}