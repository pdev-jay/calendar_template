package com.pdevjay.calendar_template.screens.calendar


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.calendar_template.intents.CalendarIntent
import com.pdevjay.calendar_template.viewmodels.CalendarViewModel
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainCalendarView(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    // baseMonth는 ViewModel의 초기 currentMonth (보통 YearMonth.now())
    val initialMonth = remember { state.currentMonth }
    // VerticalPager의 초기 페이지를 500으로 설정하여 양방향 무한 스크롤 느낌을 줌
    val INITIAL_PAGE = 500
    val pagerState = rememberPagerState(
        initialPage = INITIAL_PAGE,
        pageCount = {1000}
    )

    LaunchedEffect(key1 = pagerState.currentPage) {
        val newMonth = initialMonth.plusMonths((pagerState.currentPage - INITIAL_PAGE).toLong())
        if (newMonth != state.currentMonth) {
            viewModel.processIntent(CalendarIntent.MonthChanged(newMonth))
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    // "MMM" 패턴을 사용하면 월을 축약된 형태("Jan", "Feb", ...)로 표시합니다.
                    val formatter = DateTimeFormatter.ofPattern("MMMM")
                    Column {
                        Text(
                            text = state.currentMonth.year.toString(),
                            style = TextStyle(fontSize = 14.sp)
                        )
                        Text(text = "${state.currentMonth.format(formatter)}")

                    }
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize()
                .animateContentSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // 요일 헤더
            WeekHeader()

            VerticalPager(
                state = pagerState,
                    modifier = Modifier.fillMaxSize()
            ) { page ->
                // 각 페이지에 해당하는 달 계산
                val monthForPage = initialMonth.plusMonths((page - INITIAL_PAGE).toLong())
                DaysGrid(
                    currentMonth = monthForPage,
                    selectedDate = state.selectedDate,
                    onDateSelected = { date ->
                        if (state.selectedDate == null || state.selectedDate != date) {
                            viewModel.processIntent(CalendarIntent.DateSelected(date))
                        } else if (state.selectedDate == date) {
                            viewModel.processIntent(CalendarIntent.DateUnselected)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Selected Date: ${state.selectedDate}")
        }
    }
}









