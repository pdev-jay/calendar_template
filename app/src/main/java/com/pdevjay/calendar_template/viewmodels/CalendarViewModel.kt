package com.pdevjay.calendar_template.viewmodels

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.pdevjay.calendar_template.intents.CalendarIntent
import com.pdevjay.calendar_template.states.CalendarState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class CalendarViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(CalendarState())
    val state: StateFlow<CalendarState> = _state

    fun processIntent(intent: CalendarIntent) {
        when (intent) {
            is CalendarIntent.PreviousMonth -> {
                val newMonth = _state.value.currentMonth.minusMonths(1)
                _state.value = _state.value.copy(currentMonth = newMonth)
            }
            is CalendarIntent.NextMonth -> {
                val newMonth = _state.value.currentMonth.plusMonths(1)
                _state.value = _state.value.copy(currentMonth = newMonth)
            }
            is CalendarIntent.DateSelected -> {
                _state.value = _state.value.copy(selectedDate = intent.date)
            }
            is CalendarIntent.DateUnselected -> {
                _state.value = _state.value.copy(selectedDate = null)
            }
            is CalendarIntent.MonthChanged -> {
                _state.value = _state.value.copy(currentMonth = intent.month)
            }

        }
    }
}
