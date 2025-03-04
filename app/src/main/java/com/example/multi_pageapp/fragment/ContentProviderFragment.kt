package com.example.multi_pageapp.fragment
import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.CalendarContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.multi_pageapp.adapter.EventAdapter
import com.example.multi_pageapp.databinding.FragmentContentProviderBinding
import com.example.multi_pageapp.model.CalendarEvent
import java.text.SimpleDateFormat
import java.util.*

class ContentProviderFragment : Fragment() {

    private var _binding: FragmentContentProviderBinding? = null
    private val binding get() = _binding!!
    private val eventsList = mutableListOf<CalendarEvent>()

    // Register permission request callback
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                fetchCalendarEvents()
            } else {
                // Handle permission denied case (optional)
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerViewEvents.layoutManager = LinearLayoutManager(requireContext())

        checkAndRequestPermission()
    }

    private fun checkAndRequestPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(), Manifest.permission.READ_CALENDAR
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fetchCalendarEvents()
        } else {
            requestPermissionLauncher.launch(Manifest.permission.READ_CALENDAR)
        }
    }

    private fun fetchCalendarEvents() {
        val uri: Uri = CalendarContract.Events.CONTENT_URI
        val projection = arrayOf(CalendarContract.Events.TITLE, CalendarContract.Events.DTSTART)
        val selection = "${CalendarContract.Events.DTSTART} >= ?"
        val selectionArgs = arrayOf(System.currentTimeMillis().toString())
        val sortOrder = "${CalendarContract.Events.DTSTART} ASC"

        val cursor = requireContext().contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)

        cursor?.use {
            while (it.moveToNext()) {
                val title = it.getString(0)
                val timestamp = it.getLong(1)
                val date = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.getDefault()).format(Date(timestamp))
                eventsList.add(CalendarEvent(title, date))
            }
        }

        binding.recyclerViewEvents.adapter = EventAdapter(eventsList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}