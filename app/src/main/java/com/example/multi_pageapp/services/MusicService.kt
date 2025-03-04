package com.example.multi_pageapp.services

import android.app.*
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.multi_pageapp.R

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPaused = false


    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer.create(this, R.raw.music) // Файл "music.mp3" должен быть в res/raw/
        mediaPlayer?.isLooping = true
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "STOP" -> stopSelf()
            "PAUSE" -> togglePause() // Вызываем функцию переключения паузы
            else -> {
                startForeground(1, createNotification())
                mediaPlayer?.start()
            }
        }
        return START_STICKY
    }

    private fun togglePause() {
        isPaused = !isPaused
        if (isPaused) {
            mediaPlayer?.pause()
        } else {
            mediaPlayer?.start()
        }
        val notification = createNotification()
        val notificationManager = getSystemService(NotificationManager::class.java)
        notificationManager.notify(1, notification)
    }

    override fun onDestroy() {
        mediaPlayer?.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createNotification(): Notification {
        val stopIntent = Intent(this, MusicService::class.java).apply { action = "STOP" }
        val stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_IMMUTABLE)

        val pauseIntent = Intent(this, MusicService::class.java).apply { action = "PAUSE" }
        val pausePendingIntent = PendingIntent.getService(this, 1, pauseIntent, PendingIntent.FLAG_IMMUTABLE)

        val playPauseIcon = if (isPaused) R.drawable.icon_play else R.drawable.icon_pause
        val playPauseText = if (isPaused) "Воспроизвести" else "Пауза"

        return NotificationCompat.Builder(this, "music_channel")
            .setContentTitle("Музыкальный плеер")
            .setContentText(if (isPaused) "Музыка на паузе" else "Играет музыка...")
            .setSmallIcon(R.drawable.iconka_music)
            .addAction(playPauseIcon, playPauseText, pausePendingIntent)
            .addAction(R.drawable.icon_stop, "Стоп", stopPendingIntent)
            .setOngoing(true)
            .build()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("music_channel", "Музыка", NotificationManager.IMPORTANCE_LOW)
            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
        }
    }
}
