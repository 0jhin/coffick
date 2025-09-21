package com.yj.coffick.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yj.coffick.manager.RetrofitManager
import com.yj.coffick.model.SearchCafeList
import com.yj.coffick.model.SearchResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class LocationSearchViewModel: ViewModel() {
    // 검색결과 배열
    var searchItemList: MutableStateFlow<List<SearchCafeList>> = MutableStateFlow(listOf())

    val searchResultCount: MutableStateFlow<Int> = MutableStateFlow<Int>(0)

    // 뷰모델이 생성된다
//    init {
//
//        viewModelScope.launch {
//            searchItemList
//                .map { it.size }
//                .collect(searchResultCount)
//        }
//
//        viewModelScope.launch {
//            val response : SearchResponse = RetrofitManager.searchService.searchCafe(x = "", y = "", query = "")
//            Log.d("[서버응답]", "RandomUserScreen: ${response}")
//            searchItemList.value = response.documents
//        }
//    }

    fun search(longitude: String, latitude: String, term: String){
        viewModelScope.launch {
            val response : SearchResponse = RetrofitManager.searchService.searchCafe(x = longitude, y = latitude, query = term)
            Log.d("[서버응답]", "RandomUserScreen: ${response}")
            searchItemList.value = response.documents
        }
    }

}