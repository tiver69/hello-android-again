package com.example.helloandroidagain.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.helloandroidagain.R
import com.example.helloandroidagain.databinding.FragmentAuthLandingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthLandingFragment : Fragment() {

    private lateinit var binding: FragmentAuthLandingBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthLandingBinding.inflate(inflater, container, false)

        binding.authLandingLoginButton.setOnClickListener {
            findNavController().navigate(R.id.navToProfile)
        }
        return binding.root
    }
}