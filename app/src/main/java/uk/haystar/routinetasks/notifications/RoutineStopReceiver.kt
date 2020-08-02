package uk.haystar.routinetasks.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RoutineStopReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val routineNotification = RoutineNotification(context)
        routineNotification.removeNotification()
    }
}
