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
        val tournamentName = savedInstanceState?.getString(TOURNAMENT_NAME_BUNDLE) ?: "Tournament${arguments?.getInt(NEXT_TOURNAMENT_COUNT)}"
        val tournamentParticipantCount = savedInstanceState?.getString(TOURNAMENT_PARTICIPANT_COUNT_BUNDLE) ?: "2"
        val tournamentDateDay = savedInstanceState?.getInt(TOURNAMENT_DATE_DAY_BUNDLE) ?: 17
        val tournamentDateMonth = savedInstanceState?.getInt(TOURNAMENT_DATE_MONTH_BUNDLE) ?: 2
        val tournamentDateYear = savedInstanceState?.getInt(TOURNAMENT_DATE_YEAR_BUNDLE) ?: 2024

        binding = FragmentTournamentCreateBinding.inflate(inflater, container, false)
        binding.tournamentCreateName.setText(tournamentName)
        binding.tournamentCreateParticipantCount.setText(tournamentParticipantCount)
        binding.tournamentCreateDate.updateDate(tournamentDateYear, tournamentDateMonth, tournamentDateDay)
        binding.tournamentItemSaveButton.setOnClickListener {
            router().createResult(createTournamentResult())
            router().navBack()
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(TOURNAMENT_NAME_BUNDLE, binding.tournamentCreateName.text.toString())
        outState.putString(
            TOURNAMENT_PARTICIPANT_COUNT_BUNDLE,
            binding.tournamentCreateParticipantCount.text.toString()
        )
        outState.putInt(TOURNAMENT_DATE_DAY_BUNDLE, binding.tournamentCreateDate.dayOfMonth)
        outState.putInt(TOURNAMENT_DATE_MONTH_BUNDLE, binding.tournamentCreateDate.month)
        outState.putInt(TOURNAMENT_DATE_YEAR_BUNDLE, binding.tournamentCreateDate.year)
        outState.putString(TOURNAMENT_NAME_BUNDLE, binding.tournamentCreateName.text.toString())
        super.onSaveInstanceState(outState)
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

        private const val TOURNAMENT_NAME_BUNDLE = "TOURNAMENT_NAME_BUNDLE"
        private const val TOURNAMENT_DATE_DAY_BUNDLE = "TOURNAMENT_DATE_DAY_BUNDLE"
        private const val TOURNAMENT_DATE_MONTH_BUNDLE = "TOURNAMENT_DATE_MONTH_BUNDLE"
        private const val TOURNAMENT_DATE_YEAR_BUNDLE = "TOURNAMENT_DATE_YEAR_BUNDLE"
        private const val TOURNAMENT_PARTICIPANT_COUNT_BUNDLE =
            "TOURNAMENT_PARTICIPANT_COUNT_BUNDLE"
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