package com.kreedaankana.ui.calendar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.kreedaankana.databinding.FragmentCalendarBinding
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.showToast
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!
    private val viewModel: CalendarViewModel by viewModels()
    private lateinit var slotAdapter: SlotAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        slotAdapter = SlotAdapter { slotIndex ->
            confirmBooking(slotIndex)
        }
        binding.rvSlots.layoutManager = LinearLayoutManager(requireContext())
        binding.rvSlots.adapter = slotAdapter

        // Calendar picker
        binding.calendarView.setOnDateChangeListener { _, year, month, day ->
            val cal = Calendar.getInstance()
            cal.set(year, month, day)
            val fmt = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            viewModel.selectDate(fmt.format(cal.time))
        }

        // Observe selected date label
        viewModel.selectedDate.observe(viewLifecycleOwner) { date ->
            binding.tvSelectedDate.text = "📅  $date"
        }

        // Observe bookings and build slot models
        viewModel.bookings.observe(viewLifecycleOwner) { bookings ->
            val date = viewModel.selectedDate.value ?: return@observe
            val slotModels = Constants.TIME_SLOTS.mapIndexed { idx, label ->
                val booking = bookings.find { it.slotIndex == idx && it.date == date }
                SlotUiModel(idx, label, booking)
            }
            slotAdapter.submitList(slotModels)
        }

        // Observe booking result
        viewModel.bookingResult.observe(viewLifecycleOwner) { (success, msg) ->
            requireContext().showToast(msg)
        }
    }

    private fun confirmBooking(slotIndex: Int) {
        val slot = Constants.TIME_SLOTS[slotIndex]
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Confirm Booking")
            .setMessage("Book slot: $slot?")
            .setPositiveButton("Book It!") { _, _ ->
                viewModel.bookSlot(slotIndex)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
