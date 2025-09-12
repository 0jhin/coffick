package com.example.coffick.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coffick.components.CafeListComponent
import com.example.coffick.manager.SupabaseManager
import com.example.coffick.model.CafeEntity

@Composable
fun MapMenuFloatingScreen(modifier: Modifier = Modifier) {
    val cafeNames by SupabaseManager.cafeStateFlow.collectAsState()
//    lateinit var cafeList: List<CafeEntity>

    LaunchedEffect(Unit) {
        SupabaseManager.fetchAllCafe()
    }

//    LaunchedEffect(Unit) {
//        cafeList = SupabaseManager.fetchAllCafe()
//    }


    Box(modifier = modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.4f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .background(Color.Cyan)
                .align(alignment = Alignment.BottomCenter)
        ) {
//            cafeNames.forEach {
//                CafeListComponent(it.cafeName)
//            }
            cafeNames.forEach { it
            CafeListComponent(it.cafeName)}
        }
    }
}