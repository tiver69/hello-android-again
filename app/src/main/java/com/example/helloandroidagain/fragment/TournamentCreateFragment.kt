package com.example.helloandroidagain.fragment

import android.graphics.drawable.Drawable
import android.os.Bundle
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
import com.bumptech.glide.request.target.Target
import com.example.helloandroidagain.R
import com.example.helloandroidagain.databinding.FragmentTournamentCreateBinding
import com.example.helloandroidagain.model.ImageService
import com.example.helloandroidagain.model.RetrofitInstance
import com.example.helloandroidagain.model.TOURNAMENT_LOGO_PER_PAGE
import com.example.helloandroidagain.model.Tournament
import com.example.helloandroidagain.model.TournamentLogo
import com.example.helloandroidagain.navigation.router
import com.example.helloandroidagain.util.convertToLocalDate
import com.example.helloandroidagain.util.convertToLocalDateAsEpochMilli
import com.example.helloandroidagain.util.convertToLongAsEpochMilli
import com.example.helloandroidagain.util.convertToString
import com.google.android.material.datepicker.MaterialDatePicker
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

class TournamentCreateFragment : Fragment(), FragmentToolbar {

    private lateinit var binding: FragmentTournamentCreateBinding
    private lateinit var tournamentsDisposable: Disposable
    private lateinit var preloadedLogos: List<TournamentLogo>
    private var preloadedLogosPosition: Int = 0
    private var tournamentLogosPage: Int = 1

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
        preloadedLogosPosition = savedInstanceState?.getInt(TOURNAMENT_LOGO_PRELOAD_POSITION) ?: 0
        tournamentLogosPage = savedInstanceState?.getInt(TOURNAMENT_LOGO_PAGE) ?: 1

        binding = FragmentTournamentCreateBinding.inflate(inflater, container, false)
        binding.tournamentCreateName.editText?.setText(tournamentName)
        binding.tournamentCreateParticipantCount.editText?.setText(tournamentParticipantCount)
        binding.tournamentCreateDate.editText?.setText(tournamentDate)
        binding.tournamentCreateDate.editText?.setOnClickListener {
            createDatePicker().show(parentFragmentManager, "CREATE_TOURNAMENT_DATE")
        }
        binding.tournamentCreateRegenerateImageButton.setOnClickListener {
            if (preloadedLogos.isEmpty()) reloadTournamentLogos(tournamentLogosPage)
            else if (preloadedLogosPosition < TOURNAMENT_LOGO_PER_PAGE) {
                loadLogoFromPreloaded(preloadedLogosPosition++)
            } else {
                preloadedLogosPosition = 0
                reloadTournamentLogos(tournamentLogosPage)
            }
        }
        binding.tournamentCreateSaveButton.setOnClickListener {
            router().createResult(createTournamentResult())
            router().navBack()
        }
        reloadTournamentLogos(tournamentLogosPage)
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
        outState.putInt(TOURNAMENT_LOGO_PRELOAD_POSITION, preloadedLogosPosition)
        outState.putInt(TOURNAMENT_LOGO_PAGE, tournamentLogosPage)
        super.onSaveInstanceState(outState)
    }

    private fun createTournamentResult(): Tournament = Tournament(
        id = 0L,
        name = binding.tournamentCreateName.editText?.text.toString(),
        participantCount = Integer.valueOf(binding.tournamentCreateParticipantCount.editText?.text.toString()),
        date = binding.tournamentCreateDate.editText?.text.toString().convertToLocalDate(),
        logo = if (preloadedLogos.isNotEmpty()) preloadedLogos[preloadedLogosPosition - 1]
                else TournamentLogo.default()
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

    private fun reloadTournamentLogos(page: Int) {
        tournamentsDisposable =
            RetrofitInstance.retrofit.create(ImageService::class.java).searchLogo(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { logos ->
                        preloadedLogos = logos
                        tournamentLogosPage++
                        loadLogoFromPreloaded(preloadedLogosPosition++)
                    },
                    {
                        preloadedLogos = emptyList()
                        Glide.with(requireContext())
                            .load(R.drawable.ic_image_placeholder)
                            .into(binding.tournamentCreateLogo)
                        showLogoErrorToast()
                    })
    }

    override fun onDestroy() {
        tournamentsDisposable.dispose()
        super.onDestroy()
    }

    override fun getFragmentTitle(): Int = R.string.create_tournament_fragment_name

    private fun loadLogoFromPreloaded(preloadedLogosPosition: Int) {
        Glide.with(requireContext())
            .load(preloadedLogos[preloadedLogosPosition].regularUrl)
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
        private const val TOURNAMENT_LOGO_PRELOAD_POSITION = "TOURNAMENT_LOGO_PRELOAD_POSITION"
        private const val TOURNAMENT_LOGO_PAGE = "TOURNAMENT_LOGO_PAGE"
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