package com.example.helloandroidagain.data

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationAvailability
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.Priority
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LocationRepository @Inject constructor(
    private val fusedLocationProvider: FusedLocationProviderClient
) {
    @SuppressLint("MissingPermission")
    fun getLocationFlow(): Flow<Location?> = callbackFlow {
        val locationRequest = LocationRequest.Builder(5000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationAvailability(status: LocationAvailability) {
                if (!status.isLocationAvailable) {
                    Log.w("LocationRepository", "Location Service was disabled")
                    trySend(null)
                }
            }

            override fun onLocationResult(result: LocationResult) {
                result.lastLocation?.let {
                    trySend(it)
                }
            }
        }

        fusedLocationProvider.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )

        awaitClose {
            fusedLocationProvider.removeLocationUpdates(locationCallback)
        }
    }.flowOn(Dispatchers.IO)
}