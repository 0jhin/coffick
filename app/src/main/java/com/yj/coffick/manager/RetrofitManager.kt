package com.yj.coffick.manager

import com.yj.coffick.model.SearchCafeList
import com.yj.coffick.model.SearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Query

interface SearchCafe {
    @GET("keyword.json")
    suspend fun getCafe(): SearchCafeList
}

object RetrofitManager {
    private var searchRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com/v2/local/search/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://randomuser.me/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    // 사용할려고 하는 인터페이스 서비스만 공개할 것이다.
    val searchCafe = retrofit.create<SearchCafe>(SearchCafe::class.java)
    val searchService = searchRetrofit.create<SearchService>(SearchService::class.java)

}

interface SearchService {
    @Headers("Authorization: KakaoAK 64d1891fc48d1b964e68e2772ef02127")
    @GET("keyword.json")
    suspend fun searchCafe(
        @Query("x") x: String,
        @Query("y") y: String,
        @Query("radius") radius: Int = 2000,
        @Query("category_group_code") category_group_code: String = "CE7",
        @Query("query") query: String,
        @Query("size") size: Int = 15,
    ): SearchResponse
}
