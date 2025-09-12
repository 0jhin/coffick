package com.example.coffick.Screens

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.coffick.R
import com.example.coffick.manager.SupabaseManager
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.Pickable
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
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.delay


@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)
    val cameraPositionState = rememberCameraPositionState()
    val markerState = rememberMarkerState()
    val allPickers: List<Pickable> = listOf()
    val cafeMakers = SupabaseManager.cafeStateFlow.collectAsState()

    val nowCoordinate = cameraPositionState.coveringBounds // 현재 화면에 대한 좌표

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



    var markerCafeName by remember { mutableStateOf<String?>(null) }
    var markerCafeContent by remember { mutableStateOf<String?>(null) }
    var markerCafeTag by remember { mutableStateOf<Int?>(null) }
    var markerCafeAddress by remember { mutableStateOf<String?>(null) }
    var markerCafeIsEditorPick by remember { mutableStateOf<Boolean?>(null) }
    var markerDetailPopupOpen by remember { mutableStateOf<Boolean>(false) }


    var openSplash by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        openSplash = true
        delay(3000)
        openSplash = false
    }



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
            cafeMakers.value.let{it}
                .map { CafeEntity ->
                    Marker(
                        state = MarkerState(position = LatLng(CafeEntity.y?.toDouble() ?: 0.0, CafeEntity.x?.toDouble() ?: 0.0)),
                        captionText = CafeEntity.cafeName,
                        icon = OverlayImage.fromResource(R.drawable.baseline_location_on_24),
                        onClick = {
                            markerCafeName = CafeEntity.cafeName
                            markerCafeContent = CafeEntity.content
                            markerCafeTag = CafeEntity.tag
                            markerCafeAddress = CafeEntity.address
                            markerCafeIsEditorPick = CafeEntity.editorPick
                            markerDetailPopupOpen = true
                            Log.d("marker", "marker")
                            true
                        }
                    )

                }

        }

        MapMenuFloatingScreen(modifier = Modifier)

//        Text("$nowCoordinate", modifier = Modifier.align(alignment = Alignment.Center))

        if (markerDetailPopupOpen) {
            CafeInfoDetailScreen(
                name = markerCafeName,
                oneLine = markerCafeContent,
                tag = markerCafeTag,
                address = markerCafeAddress,
                isEditorPick = markerCafeIsEditorPick ?: false,
                onClick = {markerDetailPopupOpen = false}
            )
        }

//        val state = remember {
//            MutableTransitionState(false).apply {
//                // Start the animation immediately.
//                targetState = true
//            }
//        }

        AnimatedVisibility(
            visible = openSplash,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            SplashScreen()
        }
    }
}