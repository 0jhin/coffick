package com.yj.coffick.features.map.presentation.states

import com.yj.coffick.core.domain.entities.SearchCafeList

data class CafeMapUiState(
    // 1. 서버/검색 데이터
    val searchCafeList: List<SearchCafeList> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,

    // 2. UI 제어 상태
    val isSplashOpen: Boolean = true,
    val isDetailPopupOpen: Boolean = false,
    val selectedTag: String = "",

    // 3. 현재 선택된 카페 (개별 변수 대신 객체 하나로!)
    val selectedCafe: SearchCafeList? = null
) { // { } 안: 데이터를 기반으로 계산되는 고정된 로직
    val searchResultCount: Int = searchCafeList.size

    val isEmpty: Boolean = !isLoading && searchCafeList.isEmpty()
}