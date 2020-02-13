package com.frozenproject.moviecatalogue.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.ui.MainActivity
import java.util.*

class DailyReminderReceiver : BroadcastReceiver() {

    companion object{
        const val DAILY_REMINDER_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        showAlarmNotification(context)
    }

    private fun showAlarmNotification(context: Context) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "DilemaXXIDailyReminder channel"

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
            .setContentTitle(context.resources.getString(R.string.title_reminder))
            .setContentText(context.resources.getString(R.string.text_reminder))
            .setSubText(context.resources.getString(R.string.daily_reminder))
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000,1000,1000,1000,1000))
            .setStyle(NotificationCompat.BigTextStyle().bigText(context.resources.getString(R.string.text_reminder)))
            .setSound(alarmSound)

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        builder.setContentIntent(pendingIntent)
        builder.setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,NotificationManager.IMPORTANCE_DEFAULT)

            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000,1000,1000,1000,1000)

            builder.setChannelId(CHANNEL_ID)

            notificationManager.createNotificationChannel(channel)

            val notification = builder.build()

            notificationManager.notify(DAILY_REMINDER_ID, notification)

        }
    }

    fun setDailyReminder(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 7)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, DAILY_REMINDER_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY,pendingIntent)

    }

    fun cancelDailyReminder(context: Context){
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, DailyReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, DAILY_REMINDER_ID,intent,0)
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }
}
