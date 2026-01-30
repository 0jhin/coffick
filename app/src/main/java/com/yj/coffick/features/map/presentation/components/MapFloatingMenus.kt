package com.yj.coffick.features.map.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yj.coffick.core.domain.entities.TagEntity
import com.yj.coffick.core.services.SupabaseService

sealed class ClickEvent {
    object Research : ClickEvent() // 데이터가 필요 없는 경우
    data class Tag(val entity: TagEntity) : ClickEvent() // 데이터가 필요한 경우
}

@Composable
fun MapFloatingMenus(
    modifier: Modifier = Modifier,
    onClick: (ClickEvent) -> Unit,
    tagButtonColor: (TagEntity) -> Color,
    tagTextColor: (TagEntity) -> Color
) {
    val tagList by SupabaseService.tagStateFlow.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 28.dp)
        ) {
            // 태그 버튼 리스트
            TagList(tagList, onClick, tagButtonColor, tagTextColor)

            // 현재 위치에서 재검색 버튼
            ResearchButton(onClick)
        }
    }
}



