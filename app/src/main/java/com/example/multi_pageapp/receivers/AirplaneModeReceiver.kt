package com.example.multi_pageapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AirplaneModeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
            val isEnabled = intent.getBooleanExtra("state", false) // Получаем состояние режима полета
            val message = if (isEnabled) "Авиарежим включен" else "Авиарежим выключен"
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            Log.d("AirplaneModeReceiver", message)
        }
    }
}
