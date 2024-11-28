package com.example.helloandroidagain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.helloandroidagain.R
import com.example.helloandroidagain.databinding.FragmentTournamentCreateBinding
import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.navigation.router
import java.time.LocalDate

class TournamentCreateFragment : Fragment(), FragmentToolbar {

    private lateinit var binding: FragmentTournamentCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTournamentCreateBinding.inflate(inflater, container, false)
        binding.tournamentCreateName.setText("Tournament${arguments?.getInt(NEXT_TOURNAMENT_COUNT)}")
        binding.tournamentCreateParticipantCount.setText("2")
        binding.tournamentCreateDate.updateDate(2025, 2, 17)
        binding.tournamentItemSaveButton.setOnClickListener {
            router().createResult(createTournamentResult())
            router().navBack()
        }
        return binding.root
    }

    private fun createTournamentResult(): Tournament = Tournament(
        id = 0L,
        name = binding.tournamentCreateName.text.toString(),
        participantCount = Integer.valueOf(binding.tournamentCreateParticipantCount.text.toString()),
        date = with(binding.tournamentCreateDate) {
            LocalDate.of(year, month + 1, dayOfMonth)
        }
    )

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
            "Save"
        ) {
            router().createResult(createTournamentResult())
            router().navBack()
        }
    }
}