package com.yj.coffick.Utils

import androidx.compose.ui.graphics.Color
import kotlin.random.Random
import androidx.compose.ui.graphics.Brush

/**
 * 선형 그라데이션(LinearGradient)을 포함하는 랜덤한 Brush를 생성합니다.
 *
 * @param randomColorCount 그라데이션에 사용할 랜덤 색상의 수
 * @return Brush 객체
 */
fun generateRandomLinearGradient(randomColorCount: Int = 2): Brush {
    val colors = List(randomColorCount) {
        // 랜덤한 색상 리스트 생성
        Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat(),
            alpha = 1f
        )
    }

    return Brush.linearGradient(colors = colors)
}

/**
 * 방사형 그라데이션(RadialGradient)을 포함하는 랜덤한 Brush를 생성합니다.
 *
 * @param randomColorCount 그라데이션에 사용할 랜덤 색상의 수
 * @return Brush 객체
 */
fun generateRandomRadialGradient(randomColorCount: Int = 2): Brush {
    val colors = List(randomColorCount) {
        Color(
            red = Random.nextFloat(),
            green = Random.nextFloat(),
            blue = Random.nextFloat(),
            alpha = 1f
        )
    }

    return Brush.radialGradient(colors = colors)
}


fun generateRandomColor(): Color {
    return Color(
        red = Random.nextFloat(),
        green = Random.nextFloat(),
        blue = Random.nextFloat(),
        alpha = 1f // 투명도 100%
    )
}