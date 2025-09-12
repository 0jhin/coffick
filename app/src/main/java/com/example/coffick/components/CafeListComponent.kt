package com.example.coffick.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CafeListComponent(cafeName: String?) {
    Column(
        modifier = Modifier
            .width(120.dp)
            .height(40.dp)
            .background(Color.White, shape = RoundedCornerShape(20.dp))
    ) {
        Text(cafeName ?: "")
    }
}