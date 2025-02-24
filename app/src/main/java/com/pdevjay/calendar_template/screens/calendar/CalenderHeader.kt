package com.pdevjay.calendar_template.screens.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.YearMonth
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarHeader(
    currentMonth: YearMonth,
    onPreviousClick: () -> Unit,
    onNextClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = onPreviousClick) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "이전 달")
        }
        Text(
            text = currentMonth.format(DateTimeFormatter.ofPattern("MMMM yyyy")),
            style = MaterialTheme.typography.bodyLarge
        )
        IconButton(onClick = onNextClick) {
            Icon(Icons.Default.ArrowForward, contentDescription = "다음 달")
        }
    }
}