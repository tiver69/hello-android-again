package com.example.helloandroidagain.presentation.ui

import android.location.Location
import androidx.lifecycle.ViewModel
import com.example.helloandroidagain.domain.GetLiveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(
    private val getLiveLocationUseCase: GetLiveLocationUseCase
) : ViewModel() {

    fun getLocationFlow(): StateFlow<Location?> = getLiveLocationUseCase.invoke()
}