package com.example.multi_pageapp.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.multi_pageapp.R
import com.example.multi_pageapp.services.MusicService

class ForegroundServiceFragment : Fragment(R.layout.fragment_foreground_service) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnStart = view.findViewById<Button>(R.id.btn_start_music)
        val btnPause = view.findViewById<Button>(R.id.btn_pause_music)
        val btnStop = view.findViewById<Button>(R.id.btn_stop_music)

        btnStart.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java)
            requireContext().startService(intent)
        }

        btnPause.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = "PAUSE"
            }
            requireContext().startService(intent)
        }

        btnStop.setOnClickListener {
            val intent = Intent(requireContext(), MusicService::class.java).apply {
                action = "STOP"
            }
            requireContext().startService(intent)
        }
    }
}
