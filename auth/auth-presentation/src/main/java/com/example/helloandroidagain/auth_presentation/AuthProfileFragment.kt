package com.example.helloandroidagain.auth_presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.helloandroidagain.auth_presentation.databinding.FragmentAuthProfileBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthProfileFragment : Fragment() {

    private lateinit var binding: FragmentAuthProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthProfileBinding.inflate(inflater, container, false)

        binding.authProfileLogoutButton.setOnClickListener {
            findNavController().navigate(R.id.navToLanding)
        }

        binding.authProfileManageTournamentButton.setOnClickListener {
//            findNavController().navigate(R.id.tournamentActivity)
        }

        return binding.root
    }
}