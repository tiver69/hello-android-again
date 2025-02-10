package com.example.helloandroidagain.presentation

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.example.helloandroidagain.R
import com.example.helloandroidagain.domain.GetLiveLocationUseCase
import com.example.helloandroidagain.presentation.NotificationHelper.Companion.PERSISTENT_NOTIFICATION_ID
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
    lateinit var notificationHelper: NotificationHelper


    private val serviceScope = CoroutineScope(Job())

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.i("LocationService", "Starting foreground with command location service")
        startForeground(
            PERSISTENT_NOTIFICATION_ID,
            notificationHelper.createPersistenceNotification(
                this,
                getString(R.string.waiting_for_location_service)
            )
        )
        serviceScope.launch {
            getLiveLocationUseCase.invoke().collect { location ->
                location?.let {
                    notificationHelper.showPersistenceNotification(
                        this@LocationService, getString(
                            R.string.location_format,
                            location.latitude,
                            location.longitude
                        )
                    )
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        serviceScope.cancel()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}