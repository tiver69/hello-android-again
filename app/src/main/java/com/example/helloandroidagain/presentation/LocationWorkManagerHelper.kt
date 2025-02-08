package com.example.helloandroidagain.presentation

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationWorkManagerHelper @Inject constructor(private val workManager: WorkManager) {

    private val workName = "DailyLocationWork"

    fun scheduleDailyWork() {
        val workRequest = PeriodicWorkRequestBuilder<LocationWorker>(15, TimeUnit.MINUTES)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()

        workManager.enqueueUniquePeriodicWork(
            workName,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest
        )
    }

    fun enqueueTestWork() {
        workManager.enqueue(OneTimeWorkRequestBuilder<LocationWorker>().build())
    }
}