package com.yj.coffick.features.map.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yj.coffick.core.domain.entities.SearchResponse
import com.yj.coffick.core.services.RetrofitService
import com.yj.coffick.features.map.presentation.states.CafeMapUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CafeMapViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(CafeMapUiState())
    val uiState: StateFlow<CafeMapUiState> = _uiState.asStateFlow()

    fun initTag() {

    }

    fun search(longitude: String, latitude: String) {
        viewModelScope.launch {
            // 1. 로딩 상태 시작
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val response: SearchResponse = RetrofitService.searchService.searchCafe(
                    x = longitude, y = latitude, query = _uiState.value.selectedTag
                )

                // 2. 성공 시: 리스트 업데이트 + 로딩 종료
                _uiState.value = _uiState.value.copy(
                    searchCafeList = response.documents,
                    isLoading = false,
                    errorMessage = null
                )
            } catch (e: Exception) {
                // 3. 실패 시: 에러 메시지 + 로딩 종료
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "검색에 실패했습니다."
                )
            }
        }
    }

    fun selectedTag(clickedTag: String) {
        _uiState.update { it ->
            if (it.selectedTag != clickedTag) {
                it.copy(selectedTag = clickedTag)
            } else {
                it.copy(selectedTag = "")
            }
        }
    }
}