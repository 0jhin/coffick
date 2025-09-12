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
import androidx.compose.runtime.collectAsState
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
import com.example.coffick.manager.SupabaseManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.NaverMap
import com.naver.maps.map.Pickable
import com.naver.maps.map.Symbol
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.LocationTrackingMode
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.NaverMapConstants
import com.naver.maps.map.compose.rememberCameraPositionState
import com.naver.maps.map.compose.rememberFusedLocationSource
import com.naver.maps.map.compose.rememberMarkerState
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.widget.ZoomControlView



@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val locationSource = rememberFusedLocationSource()
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState()
    val allPickers: List<Pickable> = listOf()
    val cafeMakers = SupabaseManager.cafeStateFlow.collectAsState()



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




//    marker.position = LatLng(37.5670135, 126.9783740)
//    marker.map


    Box(modifier.fillMaxSize().background(Color.White)) {
        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = locationSource,
            cameraPositionState = cameraPositionState,
            onMapDoubleTab = {point, coord ->
                true
            },
        ) {

            cafeMakers.value
                .map {
                    Marker(state = MarkerState(position = LatLng(it.y?.toDouble() ?: 0.0, it.x?.toDouble() ?: 0.0)), captionText = it.cafeName)
                }
        }



        MapMenuFloatingScreen(modifier = Modifier)
    }
}