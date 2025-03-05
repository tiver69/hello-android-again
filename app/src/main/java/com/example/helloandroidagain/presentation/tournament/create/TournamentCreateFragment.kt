package com.example.helloandroidagain.presentation.tournament.create

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.helloandroidagain.R
import com.example.helloandroidagain.presentation.component.glide.CustomCacheLoader.SQLiteCacheFetcher.Companion.SKIP_CUSTOM_CACHE
import com.example.helloandroidagain.databinding.FragmentTournamentCreateBinding
import com.example.helloandroidagain.data.model.Tournament
import com.example.helloandroidagain.data.model.TournamentLogo
import com.example.helloandroidagain.util.convertToLocalDate
import com.example.helloandroidagain.util.convertToLocalDateAsEpochMilli
import com.example.helloandroidagain.util.convertToLongAsEpochMilli
import com.example.helloandroidagain.util.convertToString
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.logEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class TournamentCreateFragment : Fragment() {

    @Inject
    lateinit var analytics: FirebaseAnalytics

    private lateinit var binding: FragmentTournamentCreateBinding

    private val args: TournamentCreateFragmentArgs by navArgs()

    private val viewModel: TournamentCreateViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().addMenuProvider(tournamentCreateMenuProvider, viewLifecycleOwner)
        val state: TournamentCreateFragmentState? =
            savedInstanceState?.getParcelable(TOURNAMENT_CRATE_STATE_BUNDLE)
        binding = FragmentTournamentCreateBinding.inflate(inflater, container, false)

        val tournamentName =
            state?.tournamentName ?: "Tournament${args.nextTournamentCount}"
        binding.tournamentCreateName.editText?.setText(tournamentName)
        binding.tournamentCreateParticipantCount.editText?.setText(
            state?.tournamentParticipantCount ?: "2"
        )
        binding.tournamentCreateDate.editText?.setText(state?.tournamentDate ?: "17.03.2025")
        binding.tournamentCreateDate.editText?.setOnClickListener {
            createDatePicker().show(parentFragmentManager, "CREATE_TOURNAMENT_DATE")
        }
        lifecycleScope.launch {
            viewModel.currentLogo.collect { selectedLogo ->
                selectedLogo?.let {
                    loadLogo(it.regularUrl)
                } ?: run {
                    loadPlaceholderImage()
                    showLogoErrorToast()
                }
            }
        }
        if (savedInstanceState == null) viewModel.fetchTournamentLogoPage()
        binding.tournamentCreateRegenerateImageButton.setOnClickListener {
            viewModel.regenerateTournamentLogo()
        }
        binding.tournamentCreateSaveButton.setOnClickListener {
            onTournamentCreated()
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val state = TournamentCreateFragmentState(
            tournamentName = binding.tournamentCreateName.editText?.text.toString(),
            tournamentParticipantCount = binding.tournamentCreateParticipantCount.editText?.text.toString(),
            tournamentDate = binding.tournamentCreateDate.editText?.text.toString(),
        )
        outState.putParcelable(TOURNAMENT_CRATE_STATE_BUNDLE, state)
        super.onSaveInstanceState(outState)
    }

    private fun createTournamentResult(): Tournament = Tournament(
        id = 0L,
        name = binding.tournamentCreateName.editText?.text.toString(),
        participantCount = Integer.valueOf(
            binding.tournamentCreateParticipantCount.editText?.text.toString()
        ),
        date = binding.tournamentCreateDate.editText?.text.toString().convertToLocalDate(),
        logo = viewModel.currentLogo.value ?: TournamentLogo.default()
    )

    private fun createDatePicker(): MaterialDatePicker<Long> {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(
                requireContext().getString(R.string.create_tournament_fragment_date_picker)
            )
            .setSelection(
                binding.tournamentCreateDate.editText?.text.toString().convertToLocalDate()
                    .convertToLongAsEpochMilli()
            ).build()

        datePicker.addOnPositiveButtonClickListener { pickedEpochMilli ->
            binding.tournamentCreateDate.editText?.setText(
                pickedEpochMilli.convertToLocalDateAsEpochMilli().convertToString()
            )
        }
        return datePicker
    }

    private fun loadLogo(logoUrl: String) {
        Glide.with(requireContext().applicationContext)
            .load(logoUrl)
            .apply(RequestOptions().set(SKIP_CUSTOM_CACHE, true))
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any?,
                    target: Target<Drawable>?,
                    isFirstResource: Boolean
                ): Boolean {
                    showLogoErrorToast()
                    analytics.logEvent("unsplash_remote") {
                        param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
                        param(FirebaseAnalytics.Param.ITEM_ID, logoUrl)
                        param(FirebaseAnalytics.Param.SUCCESS, "FALSE")
                    }
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any?,
                    target: Target<Drawable>?,
                    dataSource: DataSource?,
                    isFirstResource: Boolean
                ): Boolean = false
            })
            .placeholder(R.drawable.ic_image_placeholder)
            .into(binding.tournamentCreateLogo)
    }

    private fun loadPlaceholderImage() {
        Glide.with(requireContext())
            .load(R.drawable.ic_image_placeholder)
            .into(binding.tournamentCreateLogo)
    }

    private fun showLogoErrorToast() {
        Toast.makeText(
            requireContext(),
            R.string.create_tournament_load_error,
            Toast.LENGTH_SHORT
        ).show()
    }

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
                    onTournamentCreated()
                    true
                }

                else -> false
            }
        }
    }

    private fun onTournamentCreated() {
        parentFragmentManager.setFragmentResult(
            CREATE_TOURNAMENT_FRAGMENT_RESULT,
            bundleOf(CREATE_TOURNAMENT_RESULT_KEY to createTournamentResult())
        )
        findNavController().popBackStack()
    }

    @Parcelize
    data class TournamentCreateFragmentState(
        val tournamentName: String,
        val tournamentDate: String,
        val tournamentParticipantCount: String
    ) : Parcelable

    companion object {
        const val CREATE_TOURNAMENT_FRAGMENT_RESULT = "CREATE_TOURNAMENT_RESULT"
        const val CREATE_TOURNAMENT_RESULT_KEY = "CREATE_TOURNAMENT_RESULT_KEY"
        private const val TOURNAMENT_CRATE_STATE_BUNDLE = "TOURNAMENT_CRATE_STATE_BUNDLE"
    }
}