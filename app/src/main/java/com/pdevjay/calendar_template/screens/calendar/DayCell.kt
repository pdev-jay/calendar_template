package com.pdevjay.calendar_template.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCell(
    modifier: Modifier = Modifier,
    dayNumber: Int,
    daysInMonth: Int,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .padding(vertical = 4.dp)
            .background(
                color = if (dayNumber in 1..daysInMonth && selectedDate == currentMonth.atDay(dayNumber))
                    Color.LightGray.copy(alpha = 0.3f)
                else Color.Transparent
            )
            .clickable(enabled = dayNumber in 1..daysInMonth) {
                if (dayNumber in 1..daysInMonth) {
                    onDateSelected(currentMonth.atDay(dayNumber))
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        if (dayNumber in 1..daysInMonth) {
            val date = currentMonth.atDay(dayNumber)
            val textColor = if (date == LocalDate.now()) Color.Red else Color.Black
            Text(
                text = dayNumber.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = textColor
            )
        }
    }
}
