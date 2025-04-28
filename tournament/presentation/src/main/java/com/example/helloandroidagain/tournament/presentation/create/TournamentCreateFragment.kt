package com.example.helloandroidagain.tournament.presentation.create

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
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.example.helloandroidagain.core.util.convertToLocalDate
import com.example.helloandroidagain.core.util.convertToLocalDateAsEpochMilli
import com.example.helloandroidagain.core.util.convertToLongAsEpochMilli
import com.example.helloandroidagain.core.util.convertToString
import com.example.helloandroidagain.tournament.domain.model.Tournament
import com.example.helloandroidagain.tournament.domain.model.TournamentLogo
import com.example.helloandroidagain.tournament.presentation.R
import com.example.helloandroidagain.tournament.presentation.component.glide.CustomActionRequestListener
import com.example.helloandroidagain.tournament.presentation.component.glide.CustomCacheLoader.SQLiteCacheFetcher.Companion.SKIP_CUSTOM_CACHE
import com.example.helloandroidagain.tournament.presentation.databinding.FragmentTournamentCreateBinding
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
        if (savedInstanceState == null) {
//            startIdleRequest()
            viewModel.fetchTournamentLogoPage()
        }
        binding.tournamentCreateRegenerateImageButton.setOnClickListener {
//            startIdleRequest()
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
            .apply(
                RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .set(SKIP_CUSTOM_CACHE, true)
            )
            .listener(getGlideRequestListener(logoUrl))
            .placeholder(R.drawable.ic_image_placeholder)
            .into(binding.tournamentCreateLogo)
    }

    private fun loadPlaceholderImage() {
        Glide.with(requireContext())
            .load(R.drawable.ic_image_placeholder)
            .into(binding.tournamentCreateLogo)
    }

    private fun onGlideLoadFailed(logoUrl: String) {
        showLogoErrorToast()
        analytics.logEvent("unsplash_remote") {
            param(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
            param(FirebaseAnalytics.Param.ITEM_ID, logoUrl)
            param(FirebaseAnalytics.Param.SUCCESS, "FALSE")
        }
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

    //    private fun startIdleRequest() {
//        if (BuildConfig.DEBUG) {
//            CustomActionIdleRequestListener.increment()
//        }
//    }
//
    private fun getGlideRequestListener(logoUrl: String): RequestListener<Drawable> =
//        if (BuildConfig.DEBUG) {
//            CustomActionIdleRequestListener.resetListenerWithAction { onGlideLoadFailed(logoUrl) }
//        } else {
        CustomActionRequestListener().setUpCustomAction { onGlideLoadFailed(logoUrl) }
//        }

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