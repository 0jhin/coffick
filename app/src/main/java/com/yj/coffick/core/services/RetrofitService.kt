package com.yj.coffick.core.services

import com.yj.coffick.core.domain.entities.SearchResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query



object RetrofitService {
    private var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com/v2/local/search/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    // 사용할려고 하는 인터페이스 서비스만 공개
    val searchService: Search = retrofit.create<Search>(Search::class.java)

}

//interface Search {
//    @GET("keyword.json")
//    suspend fun getCafe(): SearchCafeList
//}

interface Search {
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
