package com.example.coffick.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CafeInfoDetailScreen(
    modifier: Modifier = Modifier,
    name: String?,
    oneLine: String?,
    tag: Int?,
    address: String?,
    isEditorPick: Boolean
) {
    Box(contentAlignment = Alignment.Center,
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.7f))
            .clickable(onClick = {}, enabled = false)
    ) {
        Column(
            modifier = Modifier
                .width(300.dp)
                .height(600.dp)
                .background(Color.White)
        ) {
            Text(name ?: "", fontSize = 24.sp, fontWeight = Bold) // 카페 이름
            Text(oneLine ?: "")
            Text("#$tag")
            Text(address ?: "") // 길찾기(핸드 오버) 버튼
            if (isEditorPick) {
                Text("에디터 추천")
            }

        }
    }
}