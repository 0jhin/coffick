package com.example.coffick.Screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.PointF
import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition.Center.position
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.DefaultCameraDistance
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.Pickable
import com.naver.maps.map.Symbol
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.widget.ZoomControlView

enum class TEST{
    Follow, Face
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val locationSource = rememberFusedLocationSource()
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState()
    val allPickers: List<Pickable> = listOf()




//    val marker = Marker(
//        state = markerState,
//        onClick = {overlay ->
//            Toast.makeText(context, "마커 1 클릭", Toast.LENGTH_SHORT).show()
//            // 이벤트 소비, OnMapClick 이벤트는 발생하지 않음
//            false
//        }
//    )



//    naverMap.setOnMapClickListener {
//        Toast.makeText(this, "지도 클릭", Toast.LENGTH_SHORT).show()
//    }
//
//    naverMap.setOnSymbolClickListener { symbol ->
//        if (symbol.caption == "서울특별시청") {
//            Toast.makeText(this, "서울시청 클릭", Toast.LENGTH_SHORT).show()
//            // 이벤트 소비, OnMapClick 이벤트는 발생하지 않음
//            true
//        } else {
//            // 이벤트 전파, OnMapClick 이벤트가 발생함
//            false
//        }
//    }


    var mapProperties by remember {
        mutableStateOf(
//            MapProperties()
            MapProperties(
                maxZoom = 20.0,
                minZoom = 5.0,
                locationTrackingMode = LocationTrackingMode.Follow,
            ),
        )
    }
    var mapUiSettings by remember {
        mutableStateOf(
            MapUiSettings(
                isLocationButtonEnabled = true,
                isZoomControlEnabled = false,
                isTiltGesturesEnabled = false,
                isLogoClickEnabled = true,
                pickTolerance = NaverMapConstants.DefaultPickTolerance,

            )
        )
    }



    Box(modifier.fillMaxSize().background(Color.White)) {
        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = locationSource,
            cameraPositionState = cameraPositionState,
            onSymbolClick = {symbol ->
                if (symbol.caption == "서울특별시청") {
                    Toast.makeText(context, "${symbol.position}", Toast.LENGTH_SHORT).show()
                    // 이벤트 소비, OnMapClick 이벤트는 발생하지 않음
                    true
                } else {
                    // 이벤트 전파, OnMapClick 이벤트가 발생함
                    false
                }
            },
            onMapClick = { PointF, LatLng ->
                Toast.makeText(context, "$LatLng", Toast.LENGTH_SHORT).show()

            },
            onMapDoubleTab = {point, coord ->
                Toast.makeText(
                    context,
                    "${coord.latitude}, ${coord.longitude}",
                    Toast.LENGTH_SHORT
                ).show()
                true
            },

        )
    }
}