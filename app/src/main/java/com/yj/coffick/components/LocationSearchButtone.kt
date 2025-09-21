package com.yj.coffick.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun LocationSearchButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(32.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF0D0D0D), shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable(onClick = {onClick()})
            .padding(4.dp)
    ) {
        Text("현재 위치에서 재검색",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF0D0D0D)
        )
    }
}