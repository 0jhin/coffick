package com.example.coffick.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TagUiComponent(title: String?, onClick: () -> Unit, buttonColor: Color) {


    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
//            .height(32.dp)
            .background(buttonColor, shape = RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF0D0D0D), shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable(onClick = { onClick() })
            .clipToBounds()
            .padding(8.dp)
    ) {
        Text(title ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF0D0D0D)
            )
    }
}

@Composable
fun CafeListComponent(title: String?, onClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .height(32.dp)
            .background(Color(0xFFF5F5F5), shape = RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF0D0D0D), shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable(onClick = {onClick()})
            .clipToBounds()
            .padding(4.dp)
    ) {
        Text(title ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color(0xFF0D0D0D)
        )
    }
}

// 블랙
// 0xFF1A1A1A
// 0xFF2C2C2C
// 0xFF222222
// 0xFF0D0D0D - 이거

// 화이트
// 0xFFF5F5F5