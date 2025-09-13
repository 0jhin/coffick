package com.example.coffick.Utils

import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.compose.CameraPositionState

//// 현재 화면의 경계(bounds)를 계산하는 함수
//fun getMapBounds(cameraPositionState: CameraPositionState): LatLngBounds? {
//    if (cameraPositionState.isMoving) return null // 카메라가 이동 중일 때는 null 반환
//
//    val projection = cameraPositionState.projection
//    val visibleRegion = projection?.visibleRegion ?: return null
//
//    val farLeft = visibleRegion.farLeft
//    val farRight = visibleRegion.farRight
//    val nearLeft = visibleRegion.nearLeft
//    val nearRight = visibleRegion.nearRight
//
//    // 네 개의 꼭짓점 중 최소/최대 위도 및 경도 찾기
//    val minLat = minOf(farLeft.latitude, farRight.latitude, nearLeft.latitude, nearRight.latitude)
//    val maxLat = maxOf(farLeft.latitude, farRight.latitude, nearLeft.latitude, nearRight.latitude)
//    val minLng = minOf(farLeft.longitude, farRight.longitude, nearLeft.longitude, nearRight.longitude)
//    val maxLng = maxOf(farLeft.longitude, farRight.longitude, nearLeft.longitude, nearRight.longitude)
//
//    return LatLngBounds(
//        LatLng(minLat, minLng),
//        LatLng(maxLat, maxLng)
//    )
//}