package com.example.coffick.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex

@Composable
fun CafeInfoDetailScreen(
    modifier: Modifier = Modifier,
    name: String?,
    oneLine: String?,
    tag: String?,
    address: String?,
    isEditorPick: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(28.dp)

    ) {
        Column(
            modifier = Modifier
        ) {
            Box(contentAlignment = Alignment.CenterEnd,
                modifier = Modifier.fillMaxWidth().zIndex(1f)
            ) {
                Icon(Icons.Default.Clear,
                    contentDescription = "닫기",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onClick()
                            }
                        )
                        .size(36.dp)
                )
            }
            Box() {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(name ?: "", fontSize = 24.sp, fontWeight = Bold) // 카페 이름

                    Text(oneLine ?: "")

                    Text("#$tag")

                    Text(address ?: "") // 길찾기(핸드 오버) 버튼
                    if (isEditorPick) {
                        Text("에디터 추천", color = Color.Green, fontWeight = Bold, fontSize = 20.sp)
                    }
                }
            }



        }
    }
}