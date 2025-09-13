package com.example.coffick.Screens

import android.Manifest
import android.location.Location
import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.coffick.R
import com.example.coffick.manager.EventBus
import com.example.coffick.manager.SupabaseManager
import com.google.android.gms.location.LocationServices
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
import kotlinx.coroutines.launch


@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {


    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)

    val cameraPositionState = rememberCameraPositionState()

    // 마커 찍을 카페(좌표) 정보 받아오기
    val cafeMakers = SupabaseManager.cafeStateFlow.collectAsState()

    // 현재 화면에 대한 좌표 4개
    var nowCoordinate by remember { mutableStateOf(cameraPositionState.coveringBounds) }

    // 코루틴 스코프
    val scope = rememberCoroutineScope()

    // 스플레시 화면 트리거
    var openSplash by remember { mutableStateOf(false) }

    // 앱 시작시 스플레시 화면 시작/종료
    LaunchedEffect(Unit) {
        openSplash = true
        delay(3000)
        openSplash = false
    }

    //
    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    // 위치 권한이 있다고 가정하고 진행
    fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            scope.launch {
                nowCoordinate = cameraPositionState.coveringBounds
                SupabaseManager.fetchNowScreenCafe(nowCoordinate)
            }
        }
    }


    Box(modifier.fillMaxSize().background(Color.White)) {

        // 맵 매개변수
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
        // 맵 매개변수
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

        // 마커, 디테일 화면에 들어갈 정보들
        var markerCafeName by remember { mutableStateOf<String?>(null) }
        var markerCafeContent by remember { mutableStateOf<String?>(null) }
        var markerCafeTag by remember { mutableStateOf<Int?>(null) }
        var markerCafeAddress by remember { mutableStateOf<String?>(null) }
        var markerCafeIsEditorPick by remember { mutableStateOf<Boolean>(false) }
        var markerDetailPopupOpen by remember { mutableStateOf<Boolean>(false) }

        // 지도
        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = locationSource,
            cameraPositionState = cameraPositionState,
            onMapDoubleTab = {point, coord ->
                true
            },
        ) {
            cafeMakers.value.let{it} // 마커
                .map { CafeEntity ->
                    Marker(
                        state = MarkerState(position = LatLng(CafeEntity.latitude?.toDouble() ?: 0.0, CafeEntity.longitude?.toDouble() ?: 0.0)),
                        captionText = CafeEntity.cafeName,
                        captionColor = Color(0xFF0D0D0D),
                        iconTintColor = if (CafeEntity.editorPick) Color.Red else Color(0xFF0D0D0D),
                        icon = OverlayImage.fromResource(R.drawable.baseline_location_on_24), // 에디터 픽은 앱 아이콘으로
                        height = 40.dp,
                        width = 40.dp,
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
//            Marker(
//                state = MarkerState(position = LatLng(nowCoordinate?.southWest?.latitude?.toDouble() ?: 0.0, nowCoordinate?.southWest?.longitude?.toDouble() ?: 0.0)),
//                captionText = "southWest",
//                icon = OverlayImage.fromResource(R.drawable.baseline_location_on_24), // 에디터 픽은 앱 아이콘으로
//                height = 40.dp,
//                width = 40.dp,
//            )
//            Marker(
//                state = MarkerState(position = LatLng(nowCoordinate?.northEast?.latitude?.toDouble() ?: 0.0, nowCoordinate?.northEast?.longitude?.toDouble() ?: 0.0)),
//                captionText = "southWest",
//                icon = OverlayImage.fromResource(R.drawable.baseline_location_on_24), // 에디터 픽은 앱 아이콘으로
//                height = 40.dp,
//                width = 40.dp,
//            )
        }

        // 지도 위에 떠있는 고정 된 화면(매뉴들)
        MapMenuFloatingScreen(
            onClick = {
                nowCoordinate = cameraPositionState.coveringBounds // 현재 위치의 화면 좌표 전달
                scope.launch {
                    SupabaseManager.fetchNowScreenCafe(nowCoordinate)
                }
            },
            modifier = Modifier
        )


        // Latitude 위도 37.00000 y
        // Longitude 경도 127.0000 x

//        Column(
//            modifier = Modifier.align(alignment = Alignment.Center).fillMaxWidth()
//        ){Text("${nowCoordinate?.northWest}", modifier = Modifier)
//            Text("${nowCoordinate?.southEast}", modifier = Modifier)
//        }


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