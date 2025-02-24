package com.pdevjay.calendar_template.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pdevjay.calendar_template.screens.calendar.MainCalendarView
import com.pdevjay.calendar_template.viewmodels.CalendarViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarAndTodoScreen(
    modifier: Modifier = Modifier,
    viewModel: CalendarViewModel = hiltViewModel()
) {
    // ViewModel의 상태 구독 (캘린더의 선택된 날짜 포함)
    val state by viewModel.state.collectAsState()

    // 현재 기기 화면의 설정을 가져옴
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp

    // date 선택 시 캘린더가 확장/축소되는지 여부
    val expanded = state.selectedDate == null

    // 확장 시 100%, 축소 시 50% 높이 사용 (비율은 필요에 따라 조정)
    val expandedHeight = screenHeight * 1f
    val collapsedHeight = screenHeight * 0.5f

    // animateDpAsState를 사용하여 높이 변화에 애니메이션 효과 적용
    val calendarHeight by animateDpAsState(
        targetValue = if (expanded) expandedHeight else collapsedHeight,
        animationSpec = tween(durationMillis = 300)
    )

    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        // 캘린더 영역
        Box(modifier = Modifier.height(calendarHeight)) {
            MainCalendarView(viewModel = viewModel)
        }

        TodoList(expanded)
        // 날짜가 선택되면 TodoList가 슬라이드되어 나타납니다.
    }
}

@Composable
fun TodoList(expanded:Boolean) {
    AnimatedVisibility(
        visible = !expanded,
        enter = slideInVertically(
            // 아래에서 위로 슬라이드 (애니메이션 duration: 300ms)
            initialOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300)
        ),
        exit = slideOutVertically(
            targetOffsetY = { fullHeight -> fullHeight },
            animationSpec = tween(durationMillis = 300)
        )
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
                .padding(16.dp)
        ) {
            items((1..20).toList()) { event ->
                Text(
                    text = "Event $event",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun CalendarAndTodoListPreview() {
    CalendarAndTodoScreen()
}