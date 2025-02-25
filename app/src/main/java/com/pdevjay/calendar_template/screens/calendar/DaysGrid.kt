package com.pdevjay.calendar_template.screens.calendar

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@SuppressLint("UnusedBoxWithConstraintsScope")
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate? = null,
    isExpanded: Boolean = false,
    cellSize: Dp,
    onDateSelected: (LocalDate) -> Unit,
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayWeekIndex = firstDayOfMonth.dayOfWeek.value % 7
    val totalWeeks = 6
    val selectedWeekIndex = selectedDate?.let { date ->
        (firstDayWeekIndex + date.dayOfMonth - 1) / 7
    } ?: 0

    BoxWithConstraints {

        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(color = Color.White)
        ) {
            for (week in 0 until totalWeeks) {
                val targetHeight = when {
                    isExpanded -> cellSize
                    week == selectedWeekIndex && !isExpanded -> cellSize / 2
                    else -> 0.dp
                }
                val animatedHeight by animateDpAsState(
                    targetValue = targetHeight,
                    animationSpec = tween(durationMillis = 300)
                )
                WeekRow(
                    modifier = Modifier.height(animatedHeight),
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
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysGridPreview() {
    DaysGrid(
        currentMonth = YearMonth.now(),
        selectedDate = LocalDate.now(),
        onDateSelected = {},
        cellSize = 40.dp,
    )
}
