package com.example.helloandroidagain.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.location.Location
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.helloandroidagain.R
import com.example.helloandroidagain.data.LocationRepository
import com.google.android.gms.location.LocationServices

class LocationWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val locationRepository =
        LocationRepository(LocationServices.getFusedLocationProviderClient(context))
    private val notificationManager =
        context.getSystemService(NotificationManager::class.java)
    private val notificationId = 1018
    private val channelId = "push_channel"

    override suspend fun doWork(): Result {
        val location = locationRepository.getCurrentLocation()
        location?.let { notificationManager.notify(notificationId, createNotification(it)) }
        return if (location != null) Result.success() else Result.retry()
    }

    private fun createNotification(location: Location): Notification {
        val workerChannel = NotificationChannel(
            channelId,
            context.getString(R.string.push_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        )
        notificationManager.createNotificationChannel(workerChannel)

        return NotificationCompat.Builder(context, channelId)
            .setContentTitle(context.getString(R.string.push_notification_title))
            .setContentText(
                context.getString(
                    R.string.location_format,
                    location.latitude,
                    location.longitude
                )
            )
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }
}