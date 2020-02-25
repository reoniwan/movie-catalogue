package com.frozenproject.moviecatalogue.reminder

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.StrictMode
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.frozenproject.moviecatalogue.R
import com.frozenproject.moviecatalogue.data.db.movie.ResultMovie
import com.frozenproject.moviecatalogue.data.network.APICatalogueClient
import com.frozenproject.moviecatalogue.data.network.POSTER_BASE_URL
import com.frozenproject.moviecatalogue.data.network.response.MovieCatalogueResponse
import com.frozenproject.moviecatalogue.ui.MainActivity
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class DailyReleaseReceiver : BroadcastReceiver() {

    companion object {
        const val DAILY_RELEASE_ID = 102
    }

    private var movieResult: List<ResultMovie> = ArrayList()

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        getMovieReleaseToday(context)
    }

    private fun getMovieReleaseToday(context: Context) {
        val date: Date = Calendar.getInstance().time

        @SuppressLint("SimpleDateFormat")
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val today = dateFormat.format(date)

        val apiService = APICatalogueClient.getClient()

        val fetchData = apiService.getMovieRelease(today, today)

        fetchData.enqueue(object : Callback<MovieCatalogueResponse> {
            override fun onFailure(call: retrofit2.Call<MovieCatalogueResponse>, t: Throwable) {
                Log.d("ERROR", t.message!!)
            }

            override fun onResponse(
                call: retrofit2.Call<MovieCatalogueResponse>,
                response: Response<MovieCatalogueResponse>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    movieResult = response.body()!!.resultsMovie
                    showAlarmNotification(context)
                }
            }

        })

    }

    private fun showAlarmNotification(context: Context) {
        val CHANNEL_ID = "Channel_2"
        val CHANNEL_NAME = "DilemaXXIReleaseReminder Channel"

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) as Uri

        var movies = 0

        try {
            movies = if (movieResult.isNotEmpty()) movieResult.size else 0
        } catch (e: Exception) {
            Log.d("ERROR", e.message!!)
        }

        var msg: String
        if (movies == 0) {
            msg = context.resources.getString(R.string.no_movies_today)
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                .setContentTitle(context.resources.getString(R.string.title_release_daily))
                .setContentText(msg)
                .setSubText(context.resources.getString(R.string.release_reminder))
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setSound(alarmSound)

            val intent = Intent(context, MainActivity::class.java)
            val pendingIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            builder.setContentIntent(pendingIntent)
            builder.setAutoCancel(true)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
                channel.enableVibration(true)
                channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

                builder.setChannelId(CHANNEL_ID)

                notificationManager.createNotificationChannel(channel)

                val notification = builder.build()

                notificationManager.notify(DAILY_RELEASE_ID, notification)
            }
        } else {
            for (i in 0 until movies) {
                msg =
                    movieResult[i].title + " " + context.resources.getString(R.string.has_been_release_today)
                val bitmap: Bitmap? = getBitmapFromUrl(POSTER_BASE_URL + movieResult[i].backdrop)
                val builderExistMovie = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                    .setContentTitle(context.resources.getString(R.string.title_release_daily))
                    .setContentText(msg)
                    .setSubText(context.resources.getString(R.string.release_reminder))
                    .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                    .setLargeIcon(bitmap)
                    .setStyle(
                        NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(
                            null
                        )
                    )
                    .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                    .setSound(alarmSound)

                val intent = Intent(context, MainActivity::class.java)
                val pendingIntent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

                builderExistMovie.setContentIntent(pendingIntent)
                builderExistMovie.setAutoCancel(true)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val channel = NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_NAME,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                    channel.enableVibration(true)
                    channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)

                    builderExistMovie.setChannelId(CHANNEL_ID)

                    notificationManager.createNotificationChannel(channel)

                    val notification = builderExistMovie.build()

                    notificationManager.notify(DAILY_RELEASE_ID + i, notification)

                }
            }
        }
    }

    private fun getBitmapFromUrl(stringURL: String): Bitmap? {
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)

        try {
            val url = URL(stringURL)
            val connection = url.openConnection()
            connection.doInput = true
            connection.connect()

            val input = connection.getInputStream()
            return BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

    fun setDailyRelease(context: Context) {
        val intent = Intent(context, DailyReleaseReceiver::class.java)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 8)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            DAILY_RELEASE_ID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelDailyRelease(context: Context) {
        val intent = Intent(context, DailyReleaseReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, DAILY_RELEASE_ID, intent, 0)
        pendingIntent.cancel()

        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).cancel(pendingIntent)

    }
}



