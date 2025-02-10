package com.example.helloandroidagain.presentation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.helloandroidagain.R
import javax.inject.Inject

class NotificationHelper @Inject constructor(
    private val context: Context,
    private val notificationManager: NotificationManager
) {
    companion object {
        const val PERSISTENT_CHANNEL = "persistent_channel"
        const val PUSH_CHANNEL = "push_channel"
        const val PERSISTENT_NOTIFICATION_ID = 1
        const val SUMMARY_GROUP_PUSH_NOTIFICATION_ID = 2

        const val PUSH_GROUP = "push_group"
    }

    fun createNotificationChannels() {
        NotificationChannel(
            PERSISTENT_CHANNEL,
            context.getString(R.string.persistent_channel_name),
            NotificationManager.IMPORTANCE_LOW
        ).also { notificationManager.createNotificationChannel(it) }

        NotificationChannel(
            PUSH_CHANNEL,
            context.getString(R.string.push_channel_name),
            NotificationManager.IMPORTANCE_DEFAULT
        ).also { notificationManager.createNotificationChannel(it) }
    }

    fun createPersistenceNotification(notificationContext: Context, text: String): Notification {
        return NotificationCompat.Builder(notificationContext, PERSISTENT_CHANNEL)
            .setContentTitle(context.getString(R.string.persistent_notification_title))
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()
    }

    private fun createSummaryNotification(
        notificationContext: Context,
        text: String,
        groupKey: String
    ): Notification =
        NotificationCompat.Builder(notificationContext, PUSH_CHANNEL)
            .setContentTitle(context.getString(R.string.push_summary_notification_title))
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setGroup(groupKey)
            .setGroupSummary(true)
            .build()

    private fun createPushNotification(
        notificationContext: Context,
        text: String,
        groupKey: String? = null
    ): Notification = NotificationCompat.Builder(notificationContext, PUSH_CHANNEL)
        .setContentTitle(context.getString(R.string.push_notification_title))
        .setContentText(text)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setGroup(groupKey)
        .build()

    fun showPersistenceNotification(notificationContext: Context, text: String) {
        notificationManager.notify(
            PERSISTENT_NOTIFICATION_ID,
            createPersistenceNotification(notificationContext, text)
        )
    }

    fun showSummaryNotification(notificationContext: Context, text: String, group: String) {
        notificationManager.notify(
            SUMMARY_GROUP_PUSH_NOTIFICATION_ID,
            createSummaryNotification(notificationContext, text, group)
        )
    }

    fun showPushNotification(notificationContext: Context, text: String, group: String? = null) {
        notificationManager.notify(
            System.currentTimeMillis().toInt(),
            createPushNotification(notificationContext, text, group)
        )
    }
}