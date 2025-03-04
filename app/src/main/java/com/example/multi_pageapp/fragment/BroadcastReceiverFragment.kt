package com.example.multi_pageapp.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.multi_pageapp.R

class BroadcastReceiverFragment : Fragment() {

    private lateinit var airplaneStatusTextView: TextView
    private lateinit var airplaneReceiver: BroadcastReceiver

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_broadcast_receiver, container, false)
        airplaneStatusTextView = view.findViewById(R.id.airplaneStatus)

        // Инициализация BroadcastReceiver
        airplaneReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == Intent.ACTION_AIRPLANE_MODE_CHANGED) {
                    val isEnabled = intent.getBooleanExtra("state", false)
                    val statusText = if (isEnabled) "Авиарежим включен ✈️" else "Авиарежим выключен ✅"
                    airplaneStatusTextView.text = statusText
                }
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val filter = IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        requireActivity().registerReceiver(airplaneReceiver, filter)
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(airplaneReceiver)
    }
}
