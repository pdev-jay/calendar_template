package com.pdevjay.calendar_template.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DaysGrid(
    currentMonth: YearMonth,
    selectedDate: LocalDate? = null,
    isExpanded: Boolean = false,
    onDateSelected: (LocalDate) -> Unit
) {
    val firstDayOfMonth = currentMonth.atDay(1)
    val daysInMonth = currentMonth.lengthOfMonth()
    // 월요일(1) ~ 일요일(7)이므로 일요일부터 시작하게 하기 위해 % 7 사용
    val firstDayWeekIndex = firstDayOfMonth.dayOfWeek.value % 7
    // 전체 셀 수 (빈 칸 포함)
    val totalWeeks = ((firstDayWeekIndex + daysInMonth + 6) / 7)
    val totalCells = totalWeeks * 7
    val selectedWeekIndex = selectedDate?.let { date ->
        (firstDayWeekIndex + date.dayOfMonth - 1) / 7
    } ?: 0

    BoxWithConstraints {
        val fullCellHeight = maxHeight / totalWeeks

//        val cellHeight by animateDpAsState(
//            targetValue = if (isExpanded) fullCellHeight else fullCellHeight / 2,
//            animationSpec = tween(durationMillis = 300)
//        )
        Column(modifier = Modifier.fillMaxHeight().background(color= Color.White)) {
            for (week in 0 until totalCells / 7) {
                // Determine target height for this week row:
                val targetHeight = when {
                    isExpanded -> fullCellHeight
                    week == selectedWeekIndex && !isExpanded -> fullCellHeight / 2
                    else -> 0.dp  // all other week rows collapse to 0.dp
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