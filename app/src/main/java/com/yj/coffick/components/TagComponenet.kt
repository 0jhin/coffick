package com.yj.coffick.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yj.coffick.model.TagEntity

@Composable
fun TagUI(tag: String, backgroundColor: Color = Color(0xFF0D0D0D), fontColor: Color = Color(0xFFF5F5F5)) {
    Box(
        modifier = Modifier
            .background(color = backgroundColor, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp)
    ) {
        Text(tag, color = fontColor, fontSize = 12.sp)
    }
}

@Composable
fun TagList(tagList: List<String>, tag: String) {
    tagList.forEach { _ ->
        TagUI(tag)
    }
}

@Composable
fun MessageList(messages: List<TagEntity>) {
    Column {
        messages.forEach { message ->
            TagUI("")
        }
    }
}