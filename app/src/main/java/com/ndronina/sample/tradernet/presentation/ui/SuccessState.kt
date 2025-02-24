package com.ndronina.sample.tradernet.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ndronina.sample.tradernet.presentation.model.TickerUiModel

@Composable
fun SuccessState(modifier: Modifier, data: List<TickerUiModel>) {
    LazyColumn(
        modifier = modifier
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        itemsIndexed(data) { idx, uiModel ->
            TickerItem(uiModel)
            if (idx < data.lastIndex) {
                HorizontalDivider(
                    color = Color.LightGray,
                    thickness = 1.dp,
                    modifier = Modifier.padding(horizontal = 10.dp)
                )
            }
        }
    }
}