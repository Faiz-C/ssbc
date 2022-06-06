package org.verse.ssbc.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.verse.ssbc.modules.StopWatch
import org.verse.ssbc.modules.TimeSplitCollection
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.common.CONTENT_CARD_BACKGROUND_COLOR
import org.verse.ssbc.ui.common.CONTENT_CARD_BORDER_COLOR

@Composable
fun TimeSplits(
  timeSplitCollection: TimeSplitCollection,
  stopWatch: StopWatch,
  modifier: Modifier,
  lazyListState: LazyListState
) {
  Column(
    modifier = modifier
  ) {
    Header(
      text = "Time Splits",
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )

    ListBox(
      modifier = Modifier.padding(all = BASE_PADDING)
        .weight(1f)
        .align(Alignment.CenterHorizontally),
      lazyListState = lazyListState
    ) {
      items(timeSplitCollection.timeSplits) {
        TimeSplitCard(
          timeSplit = it,
          modifier = Modifier.fillMaxSize()
        )
      }
    }

    StopWatchDisplay(
      time = stopWatch.time,
      modifier = Modifier.align(Alignment.CenterHorizontally)
        .weight(0.2f)
    )
  }
}

@Composable
fun TimeSplitCard(
  timeSplit: String,
  modifier: Modifier
) {
  Card(
    modifier = modifier
      .border(BorderStroke(2.dp, CONTENT_CARD_BORDER_COLOR))
      .background(CONTENT_CARD_BACKGROUND_COLOR)
  ) {
    Row {
      Text(
        text = timeSplit,
        modifier = Modifier.fillMaxSize()
          .padding(all = BASE_PADDING * 2)
          .align(Alignment.CenterVertically),
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp
      )
    }
  }
}