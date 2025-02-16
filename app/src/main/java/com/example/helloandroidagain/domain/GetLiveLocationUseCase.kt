package com.example.helloandroidagain.domain

import android.location.Location
import com.example.helloandroidagain.data.LocationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLiveLocationUseCase @Inject constructor(
    private val locationRepository: LocationRepository
) {
    fun invoke(): Flow<Location?> = locationRepository.getLocationFlow()
}