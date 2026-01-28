package com.yj.coffick.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yj.coffick.ui.theme.NotoSansKR

@Composable
fun SplashScreen() {
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF0D0D0D))
            .clickable(onClick = {}, enabled = false)
    ) {
        Text(
            text = "쉽고 빠른 카페 검색",
            fontSize = 40.sp,
            color = Color(0xFFF5F5F5),
            fontFamily = NotoSansKR, // 정의한 패밀리 지정
            fontWeight = FontWeight.ExtraBold, // notosans_extra_bold 연결
            fontStyle = FontStyle.Italic // italic 파일 연결 -> 결과적으로 extra_bold_italic 파일 사용됨
        )
    }
}