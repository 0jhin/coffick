package com.example.coffick.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.coffick.R
import com.example.coffick.components.CafeListComponent
import com.example.coffick.components.LocationSearchButton
import com.example.coffick.components.TagUiComponent
import com.example.coffick.manager.SupabaseManager
import com.example.coffick.model.TagEntity

enum class CLICK{
    LOCATION, CAFE, TRACKING
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapMenuFloatingScreen(modifier: Modifier = Modifier,
                          onClick: (CLICK) -> Unit,
                          tagClick: (TagEntity) -> Unit,
                          tagButtonColor: (TagEntity) -> Color
) {
    val cafeList by SupabaseManager.cafeTaggingStateFlow.collectAsState()
    val tagList by SupabaseManager.tagStateFlow.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Column(horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
        ) {

            // 태그 리스트 버튼
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .horizontalScroll(rememberScrollState())
//                    .align(alignment = Alignment.TopCenter)
            ) {
                tagList.forEach { it
                    TagUiComponent(it.tag,
                        onClick = { tagClick(it) },
                        buttonColor = tagButtonColor(it)
                    )
                }
            }
            // 카페 리스트 버튼
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
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
//                    .fillMaxWidth()
                    .height(60.dp)
                    .horizontalScroll(rememberScrollState())
//                    .align(alignment = Alignment.TopCenter)
            ) {
//                LocationSearchButton(
//                    onClick = { onClick(CLICK.LOCATION) },
//                    modifier = Modifier
//                )
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
            }

        }

    }

}