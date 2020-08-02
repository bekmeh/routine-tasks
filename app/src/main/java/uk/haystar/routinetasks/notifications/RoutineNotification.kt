package uk.haystar.routinetasks.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import uk.haystar.routinetasks.R

class RoutineNotification(private var context: Context?) {

    private lateinit var channel : NotificationChannel
    private var isChannelCreated = false
    private val ROUTINE_CHANNEL_ID = "ROUTINE_CHANNEL_ID"

    /**
     * By always publishing to this notification ID, we only ever create one notification. A
     * secondary notification should be created with a separate ID.
     */
    private val NOTIFICATION_ID = 1

    fun notifyUser(status : String) {
        if (!isChannelCreated && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel()
        }

        val pauseIntent = Intent(context, RoutinePauseReceiver::class.java)
        val pauser = PendingIntent.getBroadcast(context, 1, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val completeIntent = Intent(context, RoutineCompleteReceiver::class.java)
        val completer = PendingIntent.getBroadcast(context, 1, completeIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val stopIntent = Intent(context, RoutineStopReceiver::class.java)
        val stopper = PendingIntent.getBroadcast(context, 1, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val mBuilder = NotificationCompat.Builder(context!!, ROUTINE_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_black_24dp)
            .setContentTitle("Current Routine")
            .setContentText("Routine : $status : " + System.currentTimeMillis())
            .setOngoing(true)
            .addAction(1, "Pause", pauser)
            .addAction(2, "Complete", completer)
            .addAction(3, "Stop", stopper)
        val notification = mBuilder.build()
        val notificationManager = NotificationManagerCompat.from(context!!)
        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    fun removeNotification() {
        NotificationManagerCompat.from(context!!).cancel(NOTIFICATION_ID)
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