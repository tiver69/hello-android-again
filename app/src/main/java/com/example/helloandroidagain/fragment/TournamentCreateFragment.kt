package com.example.helloandroidagain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.helloandroidagain.R
import com.example.helloandroidagain.databinding.FragmentTournamentCreateBinding
import com.example.helloandroidagain.navigation.router

class TournamentCreateFragment : Fragment(), FragmentToolbar {

    private lateinit var binding: FragmentTournamentCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTournamentCreateBinding.inflate(inflater, container, false)
        binding.tournamentCreateName.setText("Tournament${arguments?.getInt(NEXT_TOURNAMENT_COUNT)}")
        binding.tournamentItemSaveButton.setOnClickListener { router().navToTournamentList() }
        return binding.root
    }

    companion object {

        private const val NEXT_TOURNAMENT_COUNT = "NEXT_TOURNAMENT_COUNT"

        fun newInstance(nextTournamentCount: Int): TournamentCreateFragment {
            val fragment = TournamentCreateFragment()
            fragment.arguments = Bundle().apply {
                putInt(NEXT_TOURNAMENT_COUNT, nextTournamentCount)
            }
            return fragment
        }
    }

    override fun getFragmentTitle(): String = "Create Tournament"

    override fun getFragmentAction(): FragmentAction {
        return FragmentAction(
            R.drawable.ic_delete,
            "Save",
            Runnable { router().navToTournamentList() })
    }
}