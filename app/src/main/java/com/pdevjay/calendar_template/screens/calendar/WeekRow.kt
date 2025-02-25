package com.pdevjay.calendar_template.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeekRow(
    modifier: Modifier = Modifier,
    week: Int,
    firstDayWeekIndex: Int,
    daysInMonth: Int,
    currentMonth: YearMonth,
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .drawBehind { drawTopBorder(2.dp, Color.LightGray) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (day in 0 until 7) {
            val cellIndex = week * 7 + day
            val dayNumber = cellIndex - firstDayWeekIndex + 1

            DayCell(
                modifier = Modifier.weight(1f),
                dayNumber = dayNumber,
                daysInMonth = daysInMonth,
                currentMonth = currentMonth,
                selectedDate = selectedDate,
                onDateSelected = onDateSelected
            )
        }
    }
}

fun androidx.compose.ui.graphics.drawscope.DrawScope.drawTopBorder(borderThickness: Dp, borderColor: Color) {
    val strokeWidth = borderThickness.toPx()
    drawLine(
        color = borderColor,
        start = androidx.compose.ui.geometry.Offset(0f, 0f),
        end = androidx.compose.ui.geometry.Offset(size.width, 0f),
        strokeWidth = strokeWidth
    )
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun WeekRowPreview() {
    WeekRow(
        week = 0,
        firstDayWeekIndex = 1,
        daysInMonth = 31,
        currentMonth = YearMonth.now(),
        onDateSelected = {},
        selectedDate = null
    )
}