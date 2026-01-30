package com.yj.coffick.features.map.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yj.coffick.core.domain.entities.TagEntity

@Composable
fun TagList(
    tagList: List<TagEntity>,
    onClick: (ClickEvent.Tag) -> Unit,
    tagButtonColor: (TagEntity) -> Color,
    tagTextColor: (TagEntity) -> Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(60.dp)
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 20.dp)
    ) {
        tagList.forEach { tagEntity ->
            TagCard(
                tagEntity.tag,
                onClick = { onClick(ClickEvent.Tag(tagEntity)) },
                buttonColor = tagButtonColor(tagEntity),
                textColor = tagTextColor(tagEntity)
            )
        }
    }
}