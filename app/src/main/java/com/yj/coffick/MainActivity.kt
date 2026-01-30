package com.yj.coffick

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.yj.coffick.features.map.presentation.screens.CafeMapScreen
import com.yj.coffick.ui.theme.CoffickTheme


class MainActivity : ComponentActivity() {


    // 위치 권한 요청
    @OptIn(ExperimentalNaverMapApi::class)
    @RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                Color.Transparent.toArgb(), Color.Transparent.toArgb()
            ),
//            navigationBarStyle = SystemBarStyle.light(
//                Color.Transparent.toArgb(), Color.Transparent.toArgb()
//            )
        )
        setContent {
            val navController = rememberNavController()
            CoffickTheme {
                // 현재 위치 좌표 정보
                val locationSource = rememberFusedLocationSource(isCompassEnabled = true)
                Scaffold(
                    contentWindowInsets = WindowInsets.navigationBars,
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    CafeMapScreen(
                        modifier = Modifier.padding(innerPadding),
                        locationSource = locationSource
                    )
                }
            }
        }
    }
}
