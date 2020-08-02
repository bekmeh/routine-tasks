package uk.haystar.routinetasks

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService

class RoutineNotification(private var context: Context?) {

    private lateinit var channel : NotificationChannel
    private var isChannelCreated = false
    private val ROUTINE_CHANNEL_ID = "ROUTINE_CHANNEL_ID"
    private val NOTIFICATION_ID = 1

    fun notifyUser() {
        if (!isChannelCreated && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val mBuilder = NotificationCompat.Builder(context!!, ROUTINE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Current Routine")
            .setContentText("Routine blah blah " + System.currentTimeMillis())
            .setOngoing(true)
        val notification = mBuilder.build()
        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel() {
        channel = NotificationChannel(ROUTINE_CHANNEL_ID, "Routine", NotificationManager.IMPORTANCE_DEFAULT)
        channel.description = "A channel for routine notifications."
        channel.lightColor = Color.YELLOW
        val notificationManager = getSystemService(context!!, NotificationManager::class.java)
        notificationManager!!.createNotificationChannel(channel)
        isChannelCreated = true
    }
}