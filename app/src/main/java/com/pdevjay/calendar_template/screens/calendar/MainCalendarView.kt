package com.pdevjay.calendar_template.screens.calendar


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.calendar_template.intents.CalendarIntent
import com.pdevjay.calendar_template.viewmodels.CalendarViewModel

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCalendarView(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // Capture the initial month once.
    val initialMonth = remember { state.currentMonth }
    val INITIAL_PAGE = 500
    val pagerState = rememberPagerState(
        initialPage = INITIAL_PAGE,
        pageCount = { 1000 }
    )

    LaunchedEffect(key1 = pagerState.currentPage) {
        val newMonth = initialMonth.plusMonths((pagerState.currentPage - INITIAL_PAGE).toLong())
        if (newMonth != state.currentMonth) {
            viewModel.processIntent(CalendarIntent.MonthChanged(newMonth))
        }
    }
        Column(
            modifier = modifier
                .wrapContentSize()
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeekHeader()
            // BoxWithConstraints를 통해 달력 grid의 동적 크기를 계산
            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val totalWeeks = 6  // 6주 고정
                // 각 DayCell의 크기 (너비 기준)
                val cellSize = maxHeight / totalWeeks
                // 전체 grid 높이 = cellSize * 6
                val fullGridHeight = maxHeight

                // expanded 상태(state.isExpanded)에 따라 높이 애니메이션 적용
                val calendarHeight by animateDpAsState(
                    targetValue = if (state.isExpanded) fullGridHeight else cellSize / 2,
                    animationSpec = tween(durationMillis = 300)
                )

                // VerticalPager에 계산된 calendarHeight를 적용
                VerticalPager(
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(calendarHeight)
                ) { page ->
                    // 각 페이지에 해당하는 달 계산 (기준은 initialMonth)
                    val monthForPage = initialMonth.plusMonths((page - INITIAL_PAGE).toLong())
                    DaysGrid(
                        currentMonth = monthForPage,
                        selectedDate = state.selectedDate,
                        isExpanded = state.isExpanded,
                        onDateSelected = { date ->
                            if (state.selectedDate == null || state.selectedDate != date) {
                                viewModel.processIntent(CalendarIntent.DateSelected(date))
                            } else {
                                viewModel.processIntent(CalendarIntent.DateUnselected)
                            }
                        },
                        cellSize = cellSize
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Selected Date: ${state.selectedDate}")
        }
}




