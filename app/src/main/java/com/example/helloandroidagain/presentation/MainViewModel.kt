package com.example.helloandroidagain.presentation

import android.location.Location
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.helloandroidagain.domain.GetLiveLocationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getLiveLocationUseCase: GetLiveLocationUseCase
) : ViewModel() {

    suspend fun getLocationFlow(): StateFlow<Location?> = getLiveLocationUseCase.invoke()
        .stateIn(viewModelScope)
}