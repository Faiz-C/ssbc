package org.verse.ssbc.ui.components

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.common.LIST_BOX_BACKGROUND_COLOR


@Composable
fun ListBox(
  modifier: Modifier,
  lazyListState: LazyListState,
  body: LazyListScope.() -> Unit
) {
  Box(
    modifier = modifier.background(LIST_BOX_BACKGROUND_COLOR)
      .fillMaxSize()
  ) {
    LazyColumn(
      state = lazyListState,
      modifier = Modifier.wrapContentSize()
        .padding(all = BASE_PADDING * 2),
      verticalArrangement = Arrangement.spacedBy(BASE_PADDING),
    ) {
      body()
    }

    VerticalScrollbar(
      modifier = Modifier.align(Alignment.CenterEnd)
        .padding(start = BASE_PADDING / 2, end = BASE_PADDING / 2)
        .fillMaxWidth(0.006f)
        .fillMaxHeight(0.92f),
      adapter = rememberScrollbarAdapter(lazyListState)
    )
  }
}