package com.yj.coffick.Screens

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yj.coffick.R
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
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import com.yj.coffick.manager.SupabaseManager

import com.yj.coffick.model.CafeImages
import com.yj.coffick.model.RecommendedMenuEntity
import com.yj.coffick.model.SearchCafeList
import com.yj.coffick.viewmodel.LocationSearchViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


@RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun MapScreen(modifier: Modifier = Modifier,
              viewModel: LocationSearchViewModel = viewModel()) {

    val context = LocalContext.current

    var naverMap: NaverMap

    // 현재 위치 좌표 정보
    val locationSource = rememberFusedLocationSource(isCompassEnabled = true)

    // 현재 화면(카메라) 좌표 정보
    val cameraPositionState = rememberCameraPositionState()

    val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)



    // 마커 찍을 카페 정보 받아오기
    val cafeList = viewModel.searchItemList.collectAsState()

    // 현재 화면에 대한 좌표
//    var nowCoordinate by remember { mutableStateOf(cameraPositionState.coveringBounds) }

    // 코루틴 스코프
    val scope = rememberCoroutineScope()

    // 스플레시 화면 트리거
    var openSplash by remember { mutableStateOf(false) }

    // 팝업 화면 트리거
    var markerDetailPopupOpen by remember { mutableStateOf<Boolean>(false) }

    // 디테일 화면에 들어갈 키페 정보들
    var markerCafeName by remember { mutableStateOf<String?>(null) }
    var markerCafeContent by remember { mutableStateOf<String?>(null) }
    val markerCafeTags = remember { mutableStateSetOf<String?>() }
    var markerCafeAddress by remember { mutableStateOf<String?>(null) }
    val markerCafeImages: MutableSet<CafeImages> = remember { mutableStateSetOf() }
    val markerCafeMenus: MutableSet<RecommendedMenuEntity> = remember { mutableStateSetOf() }
    var markerCafeIsEditorPick by remember { mutableStateOf<Boolean>(false) }

    // 선탠 된 태그
    var selectedTag by remember { mutableStateOf("") }

    val tags = SupabaseManager.tagStateFlow.collectAsState()


    // 앱 시작시 스플레시 화면 시작/종료
    LaunchedEffect(Unit) {
        openSplash = true // 스플레시 시작
        SupabaseManager.fetchTags() // 태그들 불러오기
        val firstTag = SupabaseManager.tagStateFlow
        firstTag.value.firstOrNull()?.let {
            selectedTag = it.tag
        }
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                cameraPositionState.position = CameraPosition(LatLng(location), 15.0)
                viewModel.search(longitude = location.longitude.toString(), latitude = location.latitude.toString(), term = selectedTag) // 맨첨에 카페 정보 끌어오기
            }
        }
        delay(3000)
        openSplash = false // 스플레시 종료
    }

//    cameraPositionState.position.target

    fun detailInfoClear() {
        markerDetailPopupOpen = false
        markerCafeName = null
        markerCafeContent = null
        markerCafeTags.clear()
        markerCafeAddress = null
        markerCafeImages.clear()
        markerCafeMenus.clear()
        markerCafeIsEditorPick = false
    }

    // 뒤로가기
    var backPressedTime = 0L

    BackHandler(enabled = true) {
        if (markerDetailPopupOpen) {
            detailInfoClear()
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
                .map { SearchCafeList ->
                    Marker(
                        state = MarkerState(position = LatLng(SearchCafeList.y.toDouble(), SearchCafeList.x.toDouble())),
                        captionText = SearchCafeList.place_name,
                        captionColor = Color(0xFF0D0D0D),
                        iconTintColor =  Color(0xFF0D0D0D),
                        icon = OverlayImage.fromResource(R.drawable.baseline_location_on_24),
                        height = 36.dp,
                        width = 36.dp,
                        onClick = {
                                scope.launch {
                                    markerCafeName = SearchCafeList.place_name
                                    markerCafeContent = SearchCafeList.category_name
                                    markerCafeAddress = SearchCafeList.road_address_name
                                    markerDetailPopupOpen = true
                                }
                                true
                            }
                    )
                }
        }



        // 지도 위에 떠있는 고정 된 화면(매뉴들)
        MapMenuFloatingScreen(
            onClick = {
                when(it) {
                    CLICK.CAFE -> {

                    }
                    CLICK.LOCATION -> {
                        viewModel.search(longitude = cameraPositionState.position.target.longitude.toString(), latitude = cameraPositionState.position.target.latitude.toString(), term = selectedTag)
                    }
                    CLICK.TRACKING -> {

                    }
                }
            },
            tagClick = {
                    if (selectedTag != it.tag) {
                        selectedTag = it.tag
                    } else {
                        selectedTag = ""
                    }
                viewModel.search(longitude = cameraPositionState.position.target.longitude.toString(), latitude = cameraPositionState.position.target.latitude.toString(), term = selectedTag)
            },
            modifier = Modifier,
            tagButtonColor = {
                if (selectedTag == it.tag) Color(0xFF0D0D0D) else Color(0xFFF5F5F5)
            },
            tagTextColor = {
                if (selectedTag == it.tag) Color(0xFFF5F5F5) else Color(0xFF0D0D0D)
            }
        )


        if (markerDetailPopupOpen) {
            CafeInfoDetailScreen(
                name = markerCafeName,
                oneLine = markerCafeContent,
                tags = markerCafeTags,
                address = markerCafeAddress,
                isEditorPick = markerCafeIsEditorPick ?: false,
                images = markerCafeImages,
                menus = markerCafeMenus,
                onClick = {
                    detailInfoClear()
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