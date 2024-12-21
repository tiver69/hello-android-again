package com.example.helloandroidagain.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import com.example.helloandroidagain.R
import com.example.helloandroidagain.databinding.FragmentTournamentCreateBinding
import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.navigation.router
import com.example.helloandroidagain.util.convertToLocalDate
import com.example.helloandroidagain.util.convertToLocalDateAsEpochMilli
import com.example.helloandroidagain.util.convertToLongAsEpochMilli
import com.example.helloandroidagain.util.convertToString
import com.google.android.material.datepicker.MaterialDatePicker

class TournamentCreateFragment : Fragment(), FragmentToolbar {

    private lateinit var binding: FragmentTournamentCreateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().addMenuProvider(tournamentCreateMenuProvider, viewLifecycleOwner)
        val tournamentName = savedInstanceState?.getString(TOURNAMENT_NAME_BUNDLE) ?: "Tournament${
            arguments?.getInt(NEXT_TOURNAMENT_COUNT)
        }"
        val tournamentParticipantCount =
            savedInstanceState?.getString(TOURNAMENT_PARTICIPANT_COUNT_BUNDLE) ?: "2"
        val tournamentDate = savedInstanceState?.getString(TOURNAMENT_DATE_BUNDLE) ?: "17.03.2025"

        binding = FragmentTournamentCreateBinding.inflate(inflater, container, false)
        binding.tournamentCreateName.editText?.setText(tournamentName)
        binding.tournamentCreateParticipantCount.editText?.setText(tournamentParticipantCount)
        binding.tournamentCreateDate.editText?.setText(tournamentDate)
        binding.tournamentCreateDate.editText?.setOnClickListener {
            createDatePicker().show(parentFragmentManager, "CREATE_TOURNAMENT_DATE")
        }
        binding.tournamentCreateSaveButton.setOnClickListener {
            router().createResult(createTournamentResult())
            router().navBack()
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(
            TOURNAMENT_NAME_BUNDLE,
            binding.tournamentCreateName.editText?.text.toString()
        )
        outState.putString(
            TOURNAMENT_PARTICIPANT_COUNT_BUNDLE,
            binding.tournamentCreateParticipantCount.editText?.text.toString()
        )
        outState.putString(
            TOURNAMENT_DATE_BUNDLE,
            binding.tournamentCreateDate.editText?.text.toString()
        )
        outState.putString(
            TOURNAMENT_NAME_BUNDLE,
            binding.tournamentCreateName.editText?.text.toString()
        )
        super.onSaveInstanceState(outState)
    }

    private fun createTournamentResult(): Tournament = Tournament(
        id = 0L,
        name = binding.tournamentCreateName.editText?.text.toString(),
        participantCount = Integer.valueOf(binding.tournamentCreateParticipantCount.editText?.text.toString()),
        date = binding.tournamentCreateDate.editText?.text.toString().convertToLocalDate()
    )

    private fun createDatePicker(): MaterialDatePicker<Long> {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(requireContext().getString(R.string.create_tournament_fragment_date_picker))
            .setSelection(
                binding.tournamentCreateDate.editText?.text.toString().convertToLocalDate()
                    .convertToLongAsEpochMilli()
            )
            .build()

        datePicker.addOnPositiveButtonClickListener { pickedEpochMilli ->
            binding.tournamentCreateDate.editText?.setText(
                pickedEpochMilli.convertToLocalDateAsEpochMilli().convertToString()
            )
        }
        return datePicker
    }

    override fun getFragmentTitle(): Int = R.string.create_tournament_fragment_name

    private val tournamentCreateMenuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menu.clear()
            menuInflater.inflate(
                R.menu.create_tournament_toolbar_menu,
                menu
            )
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return when (menuItem.itemId) {
                R.id.tournament_create_toolbar_confirm_action -> {
                    router().createResult(createTournamentResult())
                    router().navBack()
                    return true
                }

                else -> false
            }
        }
    }

    companion object {

        private const val TOURNAMENT_NAME_BUNDLE = "TOURNAMENT_NAME_BUNDLE"
        private const val TOURNAMENT_DATE_BUNDLE = "TOURNAMENT_DATE_BUNDLE"
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
}