package com.example.coffick.Screens

import android.Manifest
import android.app.Activity
import android.location.Location
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresPermission
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.coffick.R
import com.example.coffick.manager.SupabaseManager
import com.example.coffick.model.CafeImages
import com.example.coffick.model.CafeTaggingEntity
import com.google.android.gms.location.LocationServices
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraAnimation
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.NaverMap
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
import com.naver.maps.map.overlay.OverlayImage
import io.github.jan.supabase.auth.providers.Zoom
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier) {

    val context = LocalContext.current

    var naverMap: NaverMap

    // 현재 위치 좌표 정보
    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)

    // 현재 화면(카메라) 좌표 정보
    val cameraPositionState = rememberCameraPositionState()

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
        if (location != null) {
            cameraPositionState.position = CameraPosition(LatLng(location), 16.0)
        }
    }

    // 마커 찍을 카페(좌표) 정보 받아오기
    val cafeList = SupabaseManager.cafeTaggingStateFlow.collectAsState()

    // 현재 화면에 대한 좌표
    var nowCoordinate by remember { mutableStateOf(cameraPositionState.coveringBounds) }

    // 코루틴 스코프
    val scope = rememberCoroutineScope()

    // 스플레시 화면 트리거
    var openSplash by remember { mutableStateOf(false) }

    // 팝업 화면 트리거
    var markerDetailPopupOpen by remember { mutableStateOf<Boolean>(false) }

    // 디테일 화면에 들어갈 키페 정보들
    var markerCafeName by remember { mutableStateOf<String?>(null) }
    var markerCafeContent by remember { mutableStateOf<String?>(null) }
    var markerCafeTags = remember { mutableStateListOf<String?>() }
    var markerCafeAddress by remember { mutableStateOf<String?>(null) }
    var markerCafeImages: MutableList<CafeImages> = remember { mutableStateListOf() }
    var markerCafeIsEditorPick by remember { mutableStateOf<Boolean>(false) }

    // 선탠 된 태그들
    val selectedTags = remember { mutableStateListOf<String>() }


    // 앱 시작시 스플레시 화면 시작/종료
    LaunchedEffect(Unit) {
        openSplash = true
        SupabaseManager.fetchTaggingAllCafes()
        SupabaseManager.fetchTags()
        delay(3000)
        openSplash = false
    }

    fun DetailInfoClear() {
        markerDetailPopupOpen = false
        markerCafeName = null
        markerCafeContent = null
        markerCafeTags.clear()
        markerCafeAddress = null
        markerCafeImages.clear()
        markerCafeIsEditorPick = false
    }

    // 뒤로가기
    var backPressedTime = 0L

    BackHandler(enabled = true) {
        if (markerDetailPopupOpen) {
            DetailInfoClear()
        } else {
            if(System.currentTimeMillis() - backPressedTime <= 2000L) {
                (context as Activity).finish() // 앱 종료
            } else {
                Toast.makeText(context, "한 번 더 누르면 앱이 종료됩니다.", Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }
    // 뒤로가기

    // 지도 화면
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
                    isZoomGesturesEnabled = true,
                    isCompassEnabled = false
                    )
            )
        }



        // 지도
        NaverMap(
            properties = mapProperties,
            uiSettings = mapUiSettings,
            locationSource = locationSource,
            cameraPositionState = cameraPositionState,
            onLocationChange = { location ->

            },
            onMapDoubleTab = { PointF, LatLng ->
                scope.launch {
                cameraPositionState.animate(update = CameraUpdate.zoomIn(), animation =  CameraAnimation.Easing)
                }
                true
            }
        ) {

            cafeList.value
                .map { CafeTaggingEntity ->
                    if (selectedTags.isEmpty()) {
                        Marker(
                            state = MarkerState(position = LatLng(CafeTaggingEntity.latitude?.toDouble() ?: 0.0, CafeTaggingEntity.longitude?.toDouble() ?: 0.0)),
                            captionText = CafeTaggingEntity.cafeName,
                            captionColor = Color(0xFF0D0D0D),
                            iconTintColor = Color(0xFF0D0D0D),
//                            captionHaloColor = Color(0xFF0D0D0D),
                            icon = if (CafeTaggingEntity.editorPick) OverlayImage.fromResource(R.drawable.coffick_logo_black) else OverlayImage.fromResource(R.drawable.baseline_location_on_24), // 에디터 픽은 앱 아이콘으로
                            height = if (CafeTaggingEntity.editorPick) 48.dp else 32.dp,
                            width = if (CafeTaggingEntity.editorPick) 48.dp else 32.dp,
                            onClick = {
                                scope.launch {
                                    val thisCafeImages = SupabaseManager.fetchCafeImages(CafeTaggingEntity.cafeId)
                                    markerCafeName = CafeTaggingEntity.cafeName
                                    markerCafeContent = CafeTaggingEntity.content
                                    CafeTaggingEntity.tags.forEach { it
                                        markerCafeTags.add(it)
                                    }
                                    markerCafeAddress = CafeTaggingEntity.address
                                    markerCafeIsEditorPick = CafeTaggingEntity.editorPick
                                    markerCafeImages.addAll(thisCafeImages)
                                    markerDetailPopupOpen = true
                                }
                                true
                            }
                        )
                    } else {
                        if (selectedTags.any{it in CafeTaggingEntity.tags}) {
                            Marker(
                                state = MarkerState(position = LatLng(CafeTaggingEntity.latitude?.toDouble() ?: 0.0, CafeTaggingEntity.longitude?.toDouble() ?: 0.0)),
                                captionText = CafeTaggingEntity.cafeName,
                                captionColor = Color(0xFF0D0D0D),
                                iconTintColor = Color(0xFF0D0D0D),
                                icon = if (CafeTaggingEntity.editorPick) OverlayImage.fromResource(R.drawable.coffick_logo_black) else OverlayImage.fromResource(R.drawable.baseline_location_on_24), // 에디터 픽은 앱 아이콘으로
                                height = if (CafeTaggingEntity.editorPick) 48.dp else 32.dp,
                                width = if (CafeTaggingEntity.editorPick) 48.dp else 32.dp,
                                onClick = {
                                    scope.launch {
                                        val thisCafeImages = SupabaseManager.fetchCafeImages(CafeTaggingEntity.cafeId)
                                        markerCafeName = CafeTaggingEntity.cafeName
                                        markerCafeContent = CafeTaggingEntity.content
                                        CafeTaggingEntity.tags.forEach { it
                                            markerCafeTags.add(it)
                                        }
                                        markerCafeAddress = CafeTaggingEntity.address
                                        markerCafeIsEditorPick = CafeTaggingEntity.editorPick
                                        markerCafeImages.addAll(thisCafeImages)
                                        markerDetailPopupOpen = true
                                    }
                                    true
                                }
                            )
                        }
                    }
                }
        }


        // 지도 위에 떠있는 고정 된 화면(매뉴들)
        MapMenuFloatingScreen(
            onClick = {
                when(it) {
                    CLICK.LOCATION -> {
                        Log.d("click", "click")
                    }
//                    CLICK.TAG -> {
//                        Log.d("click", "click")
//                    }
                    CLICK.CAFE -> {
                        Log.d("click", "click")
                    }
                    CLICK.TRACKING -> {
                        cameraPositionState.position
                        Log.d("click", "click")
                    }
                }
            },
            tagClick = {
                if (!selectedTags.contains(it.tag)) {
                    selectedTags.add(it.tag)
                } else {
                    selectedTags.remove(it.tag)
                }
            },
            modifier = Modifier,
            tagButtonColor = {
                if (selectedTags.contains(it.tag)) Color.Cyan else Color(0xFFF5F5F5)
            }
        )


        // Latitude 위도 37.00000 y
        // Longitude 경도 127.0000 x

        if (markerDetailPopupOpen) {
            CafeInfoDetailScreen(
                name = markerCafeName,
                oneLine = markerCafeContent,
                tags = markerCafeTags,
                address = markerCafeAddress,
                isEditorPick = markerCafeIsEditorPick ?: false,
                images = markerCafeImages,
                onClick = {
                    DetailInfoClear()
                }
            )
        }

        AnimatedVisibility(
            visible = openSplash,
            enter = EnterTransition.None,
            exit = fadeOut()
        ) {
            SplashScreen()
        }
    }
}