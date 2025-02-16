package com.example.helloandroidagain.presentation

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class LocationWorkManagerHelper @Inject constructor(
    private val workManager: WorkManager
) {
    private val locationWorkName = "DailyLocationWork"

    fun scheduleDailyWork() {
        val workRequest = PeriodicWorkRequestBuilder<LocationWorker>(24, TimeUnit.HOURS)
            .setConstraints(Constraints.Builder().setRequiresBatteryNotLow(true).build())
            .build()

        workManager.enqueueUniquePeriodicWork(
            locationWorkName,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }

    fun enqueueTestWork() {
        workManager.enqueue(OneTimeWorkRequestBuilder<LocationWorker>().build())
    }

    fun delayTestWorkChain() {
        workManager
            .beginWith(OneTimeWorkRequestBuilder<LocationWorker>().setInitialDelay(1, TimeUnit.MINUTES).build())
            .then(OneTimeWorkRequestBuilder<LocationWorker>().setInitialDelay(1, TimeUnit.MINUTES).build())
            .enqueue()
    }
}