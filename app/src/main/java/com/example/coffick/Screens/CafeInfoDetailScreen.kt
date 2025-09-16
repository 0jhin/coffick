package com.example.coffick.Screens

import android.content.Intent
import android.content.pm.ResolveInfo
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.coffick.components.TagUI
import com.example.coffick.model.CafeImages
import com.example.coffick.model.RecommendedMenuEntity


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CafeInfoDetailScreen(
    modifier: Modifier = Modifier,
    name: String?,
    oneLine: String?,
    tags: List<String?>,
    address: String?,
    isEditorPick: Boolean,
    onClick: () -> Unit,
    images: List<CafeImages>,
    menus: List<RecommendedMenuEntity>
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
//            .padding(16.dp)
            .clickable(onClick = {}, enabled = false)
            .padding(top = 40.dp)

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp)
                    .padding(horizontal = 16.dp)
            ) {
                // 닫기 아이콘
                Icon(Icons.Default.Clear,
                    contentDescription = "닫기",
                    modifier = Modifier
                        .clickable(
                            onClick = {
                                onClick()
                            }
                        )
                        .size(36.dp)
                )
                // 닫기 아이콘
            }


            // 사진

//            LazyRow(
//
//            ) {
//                item {
//                    images.forEach { it
//                        GlideImage(
//                            model = it.imgUrl,
//                            contentDescription = null,
//                            modifier = Modifier
//                                .clip(shape = RoundedCornerShape(20.dp))
//                        )
//                    }
//                }
//            }
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 12.dp)
            ) {
                images.forEach { it
                    GlideImage(
                        model = it.imgUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(shape = RoundedCornerShape(20.dp))
                    )
                }
            }
            // 사진

            // 글자 정보들
            Box(
                modifier = Modifier
                    .padding(16.dp)
//                    .background(Color.Cyan)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
//                        .padding(top = 12.dp)
                ) {
                    Text(name ?: "", fontSize = 24.sp, fontWeight = Bold,

                    ) // 카페 이름

                    Text(oneLine ?: "", fontSize = 16.sp) // 한 줄 소개

                    Text(address ?: "", fontSize = 16.sp) // 주소

                    Row {
                        Text("추천 매뉴 : ", fontSize = 16.sp)
                        menus.forEach { Text(it.menu) }
                    }

                    // 에디터 픽이 있으면
                    if (isEditorPick) {
                        // 에디터 픽을 맨 앞에 고정 배치
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            TagUI("👍에디터 추천")

                            tags.forEach {
                                TagUI("$it", backgroundColor = Color.LightGray, fontColor = Color(0xFF0D0D0D))
                            }
                        }
                        // 에디터 픽을 맨 앞에 고정 배치
                    }
                    // 에디터 픽이 있으면

                    // 에디터 픽이 없으면
                    else {
                        // 에디터 픽 빼고 쭉 배치
                        FlowRow(horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalArrangement = Arrangement.spacedBy(4.dp),
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            tags.forEach {
                                TagUI("$it", backgroundColor = Color.LightGray, fontColor = Color(0xFF0D0D0D))
                            }
                        }
                        // 에디터 픽 빼고 쭉 배치
                    }
                    // 에디터 픽이 없으면




                    Spacer(modifier = Modifier.weight(1f))
                    // 길찾기 버튼
                    Button(
                        onClick = {
                            // 네이버 지도 검색 인텐트
                            val url = "nmap://search?query=${name}&appname=com.example.coffick"

                            // 인텐트
                            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                            intent.addCategory(Intent.CATEGORY_BROWSABLE)

                            // 네이버 지도 설치 여부 확인 <- 여기가 문제? ㄴㄴ 매니페스트에 이거 추가 해야 함
//                            <queries>
//                              <package android:name="com.nhn.android.nmap"/>
//                            </queries>

                                val list:MutableList<ResolveInfo?>? =
                                    context.getPackageManager().queryIntentActivities(
                                        intent,
                                        0
                                    )

                                // 설치가 안되어있으면 플레이스토어로 이동
                                if (list == null || list.isEmpty()) {
                                    context.startActivity(
                                        Intent(
                                            Intent.ACTION_VIEW,
                                            Uri.parse("market://details?id=com.nhn.android.nmap")
                                        )
                                    )
                                } else { // 설치가 되어 있으면 네이버 지도 실행
                                    context.startActivity(intent)
                                }


                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0D0D0D)),
                        modifier = Modifier
                            .fillMaxWidth()

                    ) {
                        Text("길찾기", fontSize = 24.sp)
                    }
                    // 길찾기 버튼
                }
            }
            // 글자 정보들
        }
    }
}

