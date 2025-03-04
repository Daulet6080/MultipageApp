package com.example.multi_pageapp.fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.multi_pageapp.R

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = findNavController()

        view.findViewById<Button>(R.id.btn_intents).setOnClickListener {
            navController.navigate(R.id.intentsFragment)
        }

        view.findViewById<Button>(R.id.btn_foreground_service).setOnClickListener {
            navController.navigate(R.id.foregroundServiceFragment)
        }

        view.findViewById<Button>(R.id.btn_broadcast_receiver).setOnClickListener {
            navController.navigate(R.id.broadcastReceiverFragment)
        }

        view.findViewById<Button>(R.id.btn_content_provider).setOnClickListener {
            navController.navigate(R.id.contentProviderFragment)
        }
    }
}
