package com.example.helloandroidagain.presentation.tournament.export

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.helloandroidagain.R
import com.example.helloandroidagain.databinding.FragmentTournamentExportBinding
import com.example.helloandroidagain.presentation.component.glide.CustomCacheLoader.SQLiteCacheFetcher.Companion.SKIP_CUSTOM_CACHE
import com.example.helloandroidagain.util.convertToString
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import androidx.core.graphics.createBitmap

@AndroidEntryPoint
class TournamentExportFragment : Fragment() {

    private lateinit var binding: FragmentTournamentExportBinding

    private val args: TournamentExportFragmentArgs by navArgs()

    private val viewModel: TournamentExportViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTournamentExportBinding.inflate(inflater, container, false)
        with(args.tournament) {
            binding.tournamentExportNameOverlay.text = name
            binding.tournamentExportDetailOverlay.text = getString(
                R.string.export_tournament_detail_format,
                participantCount,
                date.convertToString()
            )
            loadItemLogo(logo.regularUrl, binding.tournamentExportImageBg)
            subscribeToSavingResult()

            binding.tournamentExportSaveButton.setOnClickListener {
                val bitmap = createBitmapFromView(binding.tournamentExportFrame)
                viewModel.saveImageToMediaStore(args.tournament, bitmap)
            }
        }
        return binding.root
    }

    private fun createBitmapFromView(view: View): Bitmap {
        val bitmap = createBitmap(view.width, view.height)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        return bitmap
    }

    private fun loadItemLogo(url: String, into: ImageView) {
        Glide.with(requireContext().applicationContext)
            .load(url)
            .error(R.drawable.ic_image_placeholder)
            .apply(RequestOptions().set(SKIP_CUSTOM_CACHE, true))
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(into)
    }

    private fun subscribeToSavingResult() {
        lifecycleScope.launch {
            viewModel.saveImageResultMessage.collect { status ->
                status?.let {
                    val message: String = getString(status)
                    Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                    if (viewModel.isImageSaved) {
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }
}