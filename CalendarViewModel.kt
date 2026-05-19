package com.kreedaankana.ui.calendar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.kreedaankana.data.local.AppDatabase
import com.kreedaankana.data.local.BookingEntity
import com.kreedaankana.data.model.SlotBooking
import com.kreedaankana.data.remote.FirebaseRepository
import com.kreedaankana.utils.Constants
import com.kreedaankana.utils.dataStore
import com.kreedaankana.utils.getString
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel(app: Application) : AndroidViewModel(app) {

    private val firebaseRepo = FirebaseRepository()
    private val bookingDao = AppDatabase.getInstance(app).bookingDao()
    private val dataStore = app.dataStore

    private val _selectedDate = MutableLiveData<String>()
    val selectedDate: LiveData<String> = _selectedDate

    private val _bookings = MutableLiveData<List<SlotBooking>>()
    val bookings: LiveData<List<SlotBooking>> = _bookings

    private val _bookingResult = MutableLiveData<Pair<Boolean, String>>()
    val bookingResult: LiveData<Pair<Boolean, String>> = _bookingResult

    init {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        selectDate(today)
    }

    fun selectDate(date: String) {
        _selectedDate.value = date
        loadBookingsForDate(date)
    }

    private fun loadBookingsForDate(date: String) {
        firebaseRepo.getBookingsForDate(date).observeForever { list ->
            _bookings.postValue(list)
            // Cache locally for offline use
            viewModelScope.launch {
                list.forEach { booking ->
                    bookingDao.insertBooking(
                        BookingEntity(
                            bookingId = "${booking.date}_${booking.slotIndex}",
                            teamName = booking.teamName,
                            date = booking.date,
                            slotIndex = booking.slotIndex,
                            sport = booking.sport
                        )
                    )
                }
            }
        }
    }

    fun bookSlot(slotIndex: Int) {
        viewModelScope.launch {
            val teamName = dataStore.getString(Constants.PREF_TEAM_NAME).first()
            val sport = dataStore.getString(Constants.PREF_SPORT).first()
            val date = _selectedDate.value ?: return@launch

            if (teamName.isEmpty()) {
                _bookingResult.postValue(Pair(false, "Team profile not set up!"))
                return@launch
            }

            val booking = SlotBooking(
                teamName = teamName,
                date = date,
                slotIndex = slotIndex,
                sport = sport
            )

            try {
                val success = firebaseRepo.bookSlot(booking)
                if (success) {
                    _bookingResult.postValue(Pair(true, "Slot booked successfully! 🎉"))
                } else {
                    _bookingResult.postValue(Pair(false, "Slot already booked by another team!"))
                }
            } catch (e: Exception) {
                _bookingResult.postValue(Pair(false, "Error: ${e.message}"))
            }
        }
    }

    fun getOfflineBookings(date: String): LiveData<List<BookingEntity>> =
        bookingDao.getBookingsForDateLive(date)
}
