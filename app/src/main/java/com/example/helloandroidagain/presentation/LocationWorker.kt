package com.example.helloandroidagain.presentation

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.helloandroidagain.R
import com.example.helloandroidagain.data.LocationRepository
import com.example.helloandroidagain.presentation.NotificationHelper.Companion.PUSH_GROUP
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class LocationWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted params: WorkerParameters,
    private val locationRepository: LocationRepository,
    private val notificationHelper: NotificationHelper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        if (isBackgroundPermissionDenied()) {
            notificationHelper.showPushNotification(
                context,
                context.getString(R.string.permission_required_text)
            )
            return Result.failure()
        }

        val location = locationRepository.getCurrentLocation()
        location?.let {
            val notificationText = context.getString(
                R.string.location_format,
                location.latitude,
                location.longitude
            )
            notificationHelper.showPushNotification(
                context, notificationText, PUSH_GROUP
            )
            notificationHelper.showSummaryNotification(
                context, notificationText, PUSH_GROUP
            )
        }
        return if (location != null) Result.success() else Result.retry()
    }

    private fun isBackgroundPermissionDenied(): Boolean =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q &&
                ActivityCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
}