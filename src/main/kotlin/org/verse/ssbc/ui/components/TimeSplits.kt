package org.verse.ssbc.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.verse.ssbc.modules.StopWatch
import org.verse.ssbc.modules.TimeSplitCollection
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.common.underline

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
        .weight(0.2f)
    )

    Box(
      modifier = Modifier.wrapContentSize()
        .padding(top = BASE_PADDING, bottom = BASE_PADDING)
        .weight(1f)
        .align(Alignment.CenterHorizontally)
        .clip(RoundedCornerShape(10.dp))
        .border(
          BorderStroke(3.dp, MaterialTheme.colors.primaryVariant),
          RoundedCornerShape(10.dp)
        )
        .background(Color(0xFF181726))
        .fillMaxWidth(0.9f)
        .fillMaxHeight()
    ) {
      LazyColumn(
        state = lazyListState,
        modifier = Modifier.wrapContentSize()
          .padding(start = BASE_PADDING, end = BASE_PADDING),
        verticalArrangement = Arrangement.spacedBy(BASE_PADDING),
      ) {
        items(timeSplitCollection.timeSplits) {
          Row(
            modifier = Modifier.wrapContentSize()
              .align(Alignment.Center)
              .fillMaxSize()
              .underline(MaterialTheme.colors.primaryVariant)
          ) {
            Text(
              text = it,
              modifier = Modifier.fillMaxSize()
                .padding(all = BASE_PADDING * 2)
                .align(Alignment.CenterVertically),
              fontWeight = FontWeight.Normal,
              fontSize = 18.sp
            )
          }
        }
      }

      VerticalScrollbar(
        modifier = Modifier.align(Alignment.CenterEnd)
          .fillMaxHeight(),
        adapter = rememberScrollbarAdapter(lazyListState)
      )
    }

    StopWatchDisplay(
      time = stopWatch.time,
      modifier = Modifier.align(Alignment.CenterHorizontally)
        .weight(0.2f)
    )

  }


}