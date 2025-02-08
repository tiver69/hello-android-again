package com.example.helloandroidagain.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.location.Location
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.helloandroidagain.R
import com.example.helloandroidagain.domain.GetLiveLocationUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LocationService : Service() {

    @Inject
    lateinit var getLiveLocationUseCase: GetLiveLocationUseCase

    @Inject
    lateinit var notificationManager: NotificationManager

    private val notificationId = 1017
    private val channelId = "persistent_channel"
    private val serviceScope = CoroutineScope(Job())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("LocationService", "Starting foreground with command location service")
        startForeground(
            notificationId,
            createNotification(getString(R.string.waiting_for_location_service))
        )
        serviceScope.launch {
            getLiveLocationUseCase.invoke().collect { location ->
                location?.let { updateNotification(it) }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun createNotification(text: String): Notification {
        val serviceChannel = NotificationChannel(
            channelId,
            getString(R.string.persistent_channel_name),
            NotificationManager.IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(serviceChannel)

        return NotificationCompat.Builder(this, channelId)
            .setContentTitle(getString(R.string.persistent_notification_title))
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()
    }

    private fun updateNotification(location: Location) {
        notificationManager.notify(
            notificationId,
            createNotification(
                getString(
                    R.string.location_format,
                    location.latitude,
                    location.longitude
                )
            )
        )
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}