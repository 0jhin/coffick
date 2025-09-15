package com.example.coffick.manager

import com.example.coffick.model.CafeEntity
import com.example.coffick.model.TagEntity
import com.example.coffick.model.UserEntity
import com.naver.maps.geometry.LatLngBounds
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import io.github.jan.supabase.storage.storage
import io.github.jan.supabase.storage.upload
import io.ktor.http.ContentType
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.io.File

object SupabaseManager {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://extflbrkrhnmunxppnnh.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImV4dGZsYnJrcmhubXVueHBwbm5oIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTYyNzQzOTYsImV4cCI6MjA3MTg1MDM5Nn0.aXJiJGJ1mx-1gxWJBwsjVydINLsMnifLaejjzyPPRBo"
    ) {
        install(Auth)
        install(Postgrest)
        install(Storage)
        //install other modules
    }

    suspend fun uploadProfileImg(file: File) : String {
        val bucket = supabase.storage.from("cafe/카페객체")

        // 고유한 파일명
        val filename = "카페 이름.jpeg"

        val response = bucket.upload(filename, file) {
            upsert = false
            contentType = ContentType.parse("image/jpeg")
        }



        return "uploadedImgPath"
    }

    // 회원 가입
    @OptIn(DelicateCoroutinesApi::class)
    fun createUsers(emailInput: String, passwordInput: String) {
        GlobalScope.launch {
            val user = supabase.auth.signUpWith(Email) {
                email = emailInput
                password = passwordInput
            }
        }
    }

    // 로그인
    @OptIn(DelicateCoroutinesApi::class)
    fun loginUser(emailInput: String, passwordInput: String) {
        GlobalScope.launch {
            supabase.auth.signInWith(Email) {
                email = emailInput
                password = passwordInput
            }
        }
    }

    // 로그 아웃
    @OptIn(DelicateCoroutinesApi::class)
    fun logoutUser() {
        GlobalScope.launch {
            supabase.auth.signOut()
        }
    }

    suspend fun fetchUser(id: String): UserEntity {
        val user = supabase.from("users").select() {
            filter {
                UserEntity::id eq id
            }
        }.decodeSingle<UserEntity>()
        return user
    }

    suspend fun fetchCafe(id: String): CafeEntity {
        val cafe = supabase.from("cafes").select() {
            filter {
                CafeEntity::id eq id
            }
        }.decodeSingle<CafeEntity>()
        return cafe
    }



    var cafeStateFlow = MutableStateFlow(listOf<CafeEntity>())

    suspend fun fetchAllCafe() {
        val cafeList = supabase.from("cafes").select(){
            filter {
                 eq("isPublic", true)
            }
        }.decodeList<CafeEntity>()
        cafeStateFlow.emit(value = cafeList)
    }

    // 모든 카페 데이터 가져오기
    // 현재 화면의 데이터만 가져오기
//    @OptIn(DelicateCoroutinesApi::class)
//    suspend fun fetchNowScreenCafe(bounds: LatLngBounds?){
//        // bounds가 null인 경우 빈 리스트를 반환하여 안전하게 처리합니다.
//        val cafeList = if (bounds != null) {
//            // bounds가 유효할 경우에만 데이터를 가져옵니다.
//            supabase.from(table = "cafes").select {
//                filter {
//                    lte(column = "longitude", value = bounds.northEast.longitude)
//                    lte(column = "latitude", value = bounds.northEast.latitude)
//
//
//                    gte(column = "longitude", value = bounds.southWest.longitude)
//                    gte(column = "latitude", value = bounds.southWest.latitude)
//                }
//            }.decodeList<CafeEntity>()
//        } else {
//            // bounds가 null이면 빈 리스트를 반환합니다.
//            emptyList()
//        }
//
//        cafeStateFlow.emit(value = cafeList)
//    }

//    @OptIn(DelicateCoroutinesApi::class)
//    suspend fun fetchNowScreenCafe(bounds: LatLngBounds?){
//        val cafe = supabase.from("cafes").select {
//            filter {
//                // 위도 범위 필터링
//                gte("latitude", bounds?.southWest?.latitude ?: 0.0)
//                lte("latitude", bounds?.northEast?.latitude ?: 0.0)
//
//                // 경도 범위 필터링
//                gte("longitude", bounds?.southWest?.longitude ?: 0.0)
//                lte("longitude", bounds?.northEast?.longitude ?: 0.0)
//            }
//        }.decodeList<CafeEntity>() // 데이터까지는 정상적으로 받아옴
//        cafeStateFlow.emit(cafe)
//    }

    var tagStateFlow = MutableStateFlow(listOf<TagEntity>())

    suspend fun fetchTags() {
        val tagList = supabase.from("tags").select().decodeList<TagEntity>()
        tagStateFlow.emit(value = tagList)
    }
}