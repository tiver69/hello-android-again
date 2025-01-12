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
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
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
import com.example.helloandroidagain.presentation.navigation.router
import com.example.helloandroidagain.util.convertToLocalDate
import com.example.helloandroidagain.util.convertToLocalDateAsEpochMilli
import com.example.helloandroidagain.util.convertToLongAsEpochMilli
import com.example.helloandroidagain.util.convertToString
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

@AndroidEntryPoint
class TournamentCreateFragment : Fragment(), FragmentToolbar, TournamentCreateContract.View {

    private lateinit var binding: FragmentTournamentCreateBinding
    @Inject lateinit var presenter: TournamentCreateContract.Presenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        requireActivity().addMenuProvider(tournamentCreateMenuProvider, viewLifecycleOwner)
        val state: TournamentCreateFragmentState? =
            savedInstanceState?.getParcelable(TOURNAMENT_CRATE_STATE_BUNDLE)
        binding = FragmentTournamentCreateBinding.inflate(inflater, container, false)

        val tournamentName = state?.tournamentName ?: "Tournament${
            arguments?.getInt(NEXT_TOURNAMENT_COUNT)
        }"
        binding.tournamentCreateName.editText?.setText(tournamentName)
        binding.tournamentCreateParticipantCount.editText?.setText(
            state?.tournamentParticipantCount ?: "2"
        )
        binding.tournamentCreateDate.editText?.setText(state?.tournamentDate ?: "17.03.2025")
        binding.tournamentCreateDate.editText?.setOnClickListener {
            createDatePicker().show(parentFragmentManager, "CREATE_TOURNAMENT_DATE")
        }
        presenter.attachView(
            view = this,
            preloadedLogosPosition = state?.tournamentLogoPreloadPosition ?: 0,
            tournamentLogosPage = state?.tournamentLogoPage ?: 1
        )
        presenter.fetchTournamentLogoPage()
        binding.tournamentCreateRegenerateImageButton.setOnClickListener {
            presenter.regenerateTournamentLogo()
        }
        binding.tournamentCreateSaveButton.setOnClickListener {
            router().createResult(createTournamentResult())
            router().navBack()
        }
        return binding.root
    }

    override fun onSaveInstanceState(outState: Bundle) {
        val state = TournamentCreateFragmentState(
            tournamentName = binding.tournamentCreateName.editText?.text.toString(),
            tournamentParticipantCount = binding.tournamentCreateParticipantCount.editText?.text.toString(),
            tournamentDate = binding.tournamentCreateDate.editText?.text.toString(),
            tournamentLogoPreloadPosition = presenter.getCurrentPreloadedPosition(),
            tournamentLogoPage = presenter.getCurrentLogosPage(),
        )
        outState.putParcelable(TOURNAMENT_CRATE_STATE_BUNDLE, state)
        super.onSaveInstanceState(outState)
    }

    private fun createTournamentResult(): Tournament = Tournament(
        id = 0L,
        name = binding.tournamentCreateName.editText?.text.toString(),
        participantCount = Integer.valueOf(binding.tournamentCreateParticipantCount.editText?.text.toString()),
        date = binding.tournamentCreateDate.editText?.text.toString().convertToLocalDate(),
        logo = presenter.getCurrentLogo()
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

    override fun onDestroyView() {
        presenter.onDestroyView()
        super.onDestroyView()
    }

    override fun getFragmentTitle(): Int = R.string.create_tournament_fragment_name

    override fun loadLogo(logoUrl: String) {
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

    override fun loadPlaceholderImage() {
        Glide.with(requireContext())
            .load(R.drawable.ic_image_placeholder)
            .into(binding.tournamentCreateLogo)
    }

    override fun showLogoErrorToast() {
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
                    router().createResult(createTournamentResult())
                    router().navBack()
                    return true
                }

                else -> false
            }
        }
    }

    @Parcelize
    data class TournamentCreateFragmentState(
        val tournamentName: String,
        val tournamentDate: String,
        val tournamentParticipantCount: String,
        val tournamentLogoPreloadPosition: Int,
        val tournamentLogoPage: Int,
    ) : Parcelable

    companion object {
        private const val TOURNAMENT_CRATE_STATE_BUNDLE = "TOURNAMENT_CRATE_STATE_BUNDLE"
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