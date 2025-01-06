package com.example.helloandroidagain.fragment.tournament.list

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.helloandroidagain.component.recyclerview.ItemLeftSwipeHelper
import com.example.helloandroidagain.component.recyclerview.TournamentSwipeListener
import com.example.helloandroidagain.component.recyclerview.TournamentListAdapter
import com.example.helloandroidagain.databinding.FragmentTournamentListBinding
import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.service.TournamentRepository
import com.example.helloandroidagain.navigation.CreateTournamentResultListener
import com.example.helloandroidagain.navigation.router

class TournamentListFragment : Fragment(), TournamentSwipeListener, CreateTournamentResultListener,
    TournamentListContract.View {

    private lateinit var binding: FragmentTournamentListBinding
    private lateinit var adapter: TournamentListAdapter
    private lateinit var presenter: TournamentListContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        adapter = TournamentListAdapter()
        presenter = TournamentListPresenter(
            TournamentRepository(requireContext())
        )
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
        binding.recyclerView.adapter = adapter
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