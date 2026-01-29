package com.yj.coffick.common.services

import com.yj.coffick.model.CafeImages
import com.yj.coffick.model.RecommendedMenuEntity
import com.yj.coffick.model.TagEntity
import com.yj.coffick.model.UserEntity
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object SupabaseService {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://extflbrkrhnmunxppnnh.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImV4dGZsYnJrcmhubXVueHBwbm5oIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTYyNzQzOTYsImV4cCI6MjA3MTg1MDM5Nn0.aXJiJGJ1mx-1gxWJBwsjVydINLsMnifLaejjzyPPRBo"
    ) {
        install(Auth.Companion)
        install(Postgrest.Companion)
        install(Storage.Companion)
        //install other modules
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


    // 디테일화면에 띄울 카페 사진들
    // 카페 ID로 매칭
    suspend fun fetchCafeImages(cafeId: Int) : List<CafeImages>{
        val imageList = supabase.from("cafe_images").select(){
            filter {
                CafeImages::cafeId eq cafeId
            }
        }.decodeList<CafeImages>()
        return imageList
    }

    // 디테일화면에 띄울 추천 매뉴들
    // 카페 ID로 매칭
    suspend fun fetchRecommendedMenu(cafeId: Int) : List<RecommendedMenuEntity>{
        val menuList = supabase.from("recommend_menu").select(){
            filter {
                RecommendedMenuEntity::cafeId eq cafeId
            }
        }.decodeList<RecommendedMenuEntity>()
        return menuList
    }

    var tagStateFlow = MutableStateFlow(listOf<TagEntity>())

    suspend fun fetchTags() {
        val tagList = supabase.from("tags").select().decodeList<TagEntity>()
        tagStateFlow.emit(value = tagList)
    }
}