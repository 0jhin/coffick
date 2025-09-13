package com.example.coffick.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.coffick.components.CafeListComponent
import com.example.coffick.components.LocationSearchButton
import com.example.coffick.manager.SupabaseManager
import com.example.coffick.model.CafeEntity

@Composable
fun MapMenuFloatingScreen(modifier: Modifier = Modifier, onClick: () -> Unit) {
    val cafeNames by SupabaseManager.cafeStateFlow.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp)
                    .horizontalScroll(rememberScrollState())
//                    .align(alignment = Alignment.TopCenter)
            ) {
                cafeNames.forEach { it
                    CafeListComponent(it.cafeName)
                }
            }
            LocationSearchButton(
                onClick,
                modifier = Modifier
            )
        }

    }

}