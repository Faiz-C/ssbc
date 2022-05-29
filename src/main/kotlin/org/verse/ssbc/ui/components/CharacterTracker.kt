package org.verse.ssbc.ui.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
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
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.modules.StopWatch
import org.verse.ssbc.modules.TimeSplitCollection
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.common.underline

@Composable
fun CharacterTracker(
  ironManTracker: IronManTracker,
  playedCharactersLazyState: LazyListState,
  timeSplitCollection: TimeSplitCollection,
  timeSplitsLazyState: LazyListState,
  stopWatch: StopWatch,
  modifier: Modifier
) {
  Column(
    modifier = modifier
  ) {
    Header(
      text = "Characters Played",
      modifier = Modifier.align(Alignment.CenterHorizontally)
        .padding(bottom = BASE_PADDING * 2)
    )

    Box(
      modifier = Modifier.wrapContentSize()
        .padding(top = BASE_PADDING * 2)
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
        state = playedCharactersLazyState,
        modifier = Modifier.wrapContentSize()
          .padding(start = BASE_PADDING, end = BASE_PADDING),
        verticalArrangement = Arrangement.spacedBy(BASE_PADDING),
      ) {
        itemsIndexed(ironManTracker.played) { i, character ->
          Row (
            modifier = Modifier.wrapContentSize()
              .align(Alignment.Center)
              .underline(MaterialTheme.colors.primaryVariant)
          ) {
            Text(
              text = "${i + 1}.",
              modifier = Modifier.fillMaxSize()
                .weight(0.10f)
                .padding(start = BASE_PADDING * 2)
                .align(Alignment.CenterVertically),
              fontWeight = FontWeight.SemiBold,
              fontSize = 16.sp
            )

            CharacterLogo(
              character = character,
              modifier = Modifier
                .align(Alignment.CenterVertically)
                .weight(0.15f)
                .fillMaxSize()
            )

            Text(
              text = character.name,
              modifier = Modifier.fillMaxSize()
                .weight(1f)
                .padding(start = BASE_PADDING * 3)
                .align(Alignment.CenterVertically),
              fontWeight = FontWeight.SemiBold,
              fontSize = 16.sp
            )
          }
        }
      }

      VerticalScrollbar(
        modifier = Modifier.align(Alignment.CenterEnd)
          .fillMaxHeight(),
        adapter = rememberScrollbarAdapter(playedCharactersLazyState)
      )
    }

    Controls(
      ironManTracker = ironManTracker,
      characterLazyListState = playedCharactersLazyState,
      timeSplitCollection = timeSplitCollection,
      timeSplitLazyListState = timeSplitsLazyState,
      stopWatch = stopWatch,
      modifier = Modifier.fillMaxSize()
        .weight(0.4f)
    )
  }

}