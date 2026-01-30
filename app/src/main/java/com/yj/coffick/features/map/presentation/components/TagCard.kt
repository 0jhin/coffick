package com.yj.coffick.features.map.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun TagCard(title: String?, onClick: () -> Unit, buttonColor: Color, textColor: Color) {


    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .shadow(
                elevation = 8.dp, // 그림자 깊이
                shape = RoundedCornerShape(20.dp), // 그림자 모양
            )
            .background(color = buttonColor, shape = RoundedCornerShape(20.dp))
            .border(1.dp, Color(0xFF0D0D0D).copy(0.3f), shape = RoundedCornerShape(20.dp))
            .clip(shape = RoundedCornerShape(20.dp))
            .clickable(onClick = { onClick() })
            .clipToBounds()
            .padding(8.dp)
    ) {
        Text(title ?: "",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = textColor
            )
    }
}