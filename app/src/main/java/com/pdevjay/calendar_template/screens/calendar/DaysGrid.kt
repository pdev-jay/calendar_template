package com.pdevjay.calendar_template.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate? = null,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    // 월요일(1) ~ 일요일(7)이므로 일요일부터 시작하게 하기 위해 % 7 사용
    val firstDayWeekIndex = firstDayOfMonth.dayOfWeek.value % 7
    // 전체 셀 수 (빈 칸 포함)
    val totalCells = ((firstDayWeekIndex + daysInMonth + 6) / 7) * 7

    Column(modifier = Modifier.fillMaxHeight().background(color= Color.White)) {
        for (week in 0 until totalCells / 7) {
            WeekRow(
                modifier = Modifier.weight(1f),
                week = week,
                firstDayWeekIndex = firstDayWeekIndex,
                daysInMonth = daysInMonth,
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                onDateSelected = onDateSelected
            )
        }
    }
}

@Composable
@Preview
@RequiresApi(Build.VERSION_CODES.O)
fun DaysGridPreview() {
    DaysGrid(
        currentMonth = YearMonth.now(),
        selectedDate = LocalDate.now(),
        onDateSelected = {}
    )
}