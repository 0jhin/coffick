package com.example.coffick.manager

import com.example.coffick.model.CafeEntity
import com.example.coffick.model.UserEntity
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

object SupabaseManager {

    val supabase = createSupabaseClient(
        supabaseUrl = "https://extflbrkrhnmunxppnnh.supabase.co",
        supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImV4dGZsYnJrcmhubXVueHBwbm5oIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NTYyNzQzOTYsImV4cCI6MjA3MTg1MDM5Nn0.aXJiJGJ1mx-1gxWJBwsjVydINLsMnifLaejjzyPPRBo"
    ) {
        install(Auth)
        install(Postgrest)
        //install other modules

    }
    @OptIn(DelicateCoroutinesApi::class)
    fun createUsers(emailInput: String, passwordInput: String) {
        GlobalScope.launch {
            val user = supabase.auth.signUpWith(Email) {
                email = emailInput
                password = passwordInput
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun loginUser(emailInput: String, passwordInput: String) {
        GlobalScope.launch {
            supabase.auth.signInWith(Email, redirectUrl = "https://www.naver.com") {
                email = emailInput
                password = passwordInput
            }
        }
    }

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

            }
        }.decodeSingle<CafeEntity>()
        return cafe
    }


    var cafeStateFlow = MutableStateFlow(listOf<CafeEntity>())
    suspend fun fetchAllCafe() {
        val cafe = supabase.from("cafes").select() {
        }.decodeList<CafeEntity>()
        cafeStateFlow.emit(cafe)
    }
}