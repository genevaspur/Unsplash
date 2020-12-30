package com.example.unsplash.util

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.unsplash.R

class DownloadNotificationUtil(context: Context, name: String = "downloadService") : IntentService(name) {

    companion object {
        const val PROGRESS_UPDATE = "progress_update"
    }

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    private val notificationBuilder by lazy {
        NotificationCompat.Builder(this, "download")
    }

    override fun onHandleIntent(intent: Intent?) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("download", "파일 다운로드", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.description = "description"
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationBuilder.setSmallIcon(R.drawable.img_heart_red)
            .setContentTitle("다운로드123")
            .setContentText("다운로드중")
            .setDefaults(0)
            .setAutoCancel(true)

        notificationManager.notify(0, notificationBuilder.build())

    }

    fun updateNotification(currentProgress: Int) {
        notificationBuilder.setProgress(100, currentProgress, false)
        notificationBuilder.setContentText("$currentProgress%")
        notificationManager.notify(0, notificationBuilder.build())
    }

    fun sendProgressUpdate(downloadComplete: Boolean) {
        val intent = Intent(PROGRESS_UPDATE).apply {
            this.putExtra("downloadComplete", downloadComplete)
        }
        // TODO LocalBroadcastManager????
    }

    fun onDownloadComplete(downloadComplete: Boolean) {
        val message = if (downloadComplete) "Download Complete"
        else "Download failed"

        notificationManager.cancel(0)
        notificationBuilder.setProgress(0, 0, false)
        notificationBuilder.setContentText(message)
        notificationManager.notify(0, notificationBuilder.build())
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        notificationManager.cancel(0)
    }

}