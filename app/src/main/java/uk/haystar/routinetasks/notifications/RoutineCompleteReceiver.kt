package uk.haystar.routinetasks.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class RoutineCompleteReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val routineNotification =
            RoutineNotification(context)
        routineNotification.notifyUser("Completed")
    }
}
