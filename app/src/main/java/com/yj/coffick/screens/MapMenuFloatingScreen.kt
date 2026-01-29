package com.yj.coffick.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yj.coffick.components.LocationSearchButton
import com.yj.coffick.components.TagUiComponent
import com.yj.coffick.common.services.SupabaseService
import com.yj.coffick.model.TagEntity

enum class CLICK{
    LOCATION, CAFE, TRACKING
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapMenuFloatingScreen(modifier: Modifier = Modifier,
                          onClick: (CLICK) -> Unit,
                          tagClick: (TagEntity) -> Unit,
                          tagButtonColor: (TagEntity) -> Color,
                          tagTextColor: (TagEntity) -> Color
) {
//    val cafeList by SupabaseManager.cafeTaggingStateFlow.collectAsState()
    val tagList by SupabaseService.tagStateFlow.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Column(horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 28.dp)
        ) {
//            Spacer(modifier = Modifier.height(20.dp))
            // 태그 리스트 버튼
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp)
            ) {
                tagList.forEach { it
                    TagUiComponent(it.tag,
                        onClick = { tagClick(it) },
                        buttonColor = tagButtonColor(it),
                        textColor = tagTextColor(it)
                    )
                }
            }
            // 태그 리스트 버튼

            // 하단 카페 리스트
//            Row(
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(12.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(60.dp)
//                .horizontalScroll(rememberScrollState())
////                    .align(alignment = Alignment.TopCenter)
//            ) {
//                cafeList.forEach { it
//                    CafeListComponent(it.cafeName,
//                        onClick = { onClick(CLICK.CAFE) },
//                    )
//                }
//            }
            // 하단 카페 리스트

            // 하단 매뉴들
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .height(40.dp)
                    .width(260.dp)
                    .horizontalScroll(rememberScrollState())
            ) {
                // 현재 위치에서 검색 버튼
                LocationSearchButton(
                    onClick = { onClick(CLICK.LOCATION) },
                    modifier = Modifier
                )
                // 현재 위치에서 검색 버튼

                // 현재 위치 버튼
//                Icon(
//                    painter = painterResource(id = R.drawable.location_searching_24dp_f5f5f5_fill0_wght400_grad0_opsz24),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(44.dp)
//                        .background(Color.White, shape = CircleShape)
//                        .clip(CircleShape)
//                        .clickable(
//                            indication = null, // Disable the ripple effect
//                            interactionSource = remember { MutableInteractionSource() }
//                        ) {
//                            onClick(CLICK.TRACKING)
//                        }
//                        .clipToBounds()
//                        .padding(8 .dp)
//                )
                // 현재 위치 버튼
            }
            // 하단 매뉴들
        }
    }
}