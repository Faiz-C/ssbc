package org.verse.ssbc.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.verse.ssbc.model.SmashCharacter
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.modules.StopWatch
import org.verse.ssbc.modules.TimeSplitCollection
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.common.CONTENT_CARD_BACKGROUND_COLOR
import org.verse.ssbc.ui.common.CONTENT_CARD_BORDER_COLOR

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
    )

    ListBox(
      modifier = Modifier.padding(all = BASE_PADDING)
        .weight(1f)
        .align(Alignment.CenterHorizontally),
      lazyListState = playedCharactersLazyState
    ) {
      itemsIndexed(ironManTracker.played) { i, character ->
        CharacterCard(
          index = i,
          character = character,
          modifier = Modifier.fillMaxSize()
        )
      }
    }

    Controls(
      ironManTracker = ironManTracker,
      characterLazyListState = playedCharactersLazyState,
      timeSplitCollection = timeSplitCollection,
      timeSplitLazyListState = timeSplitsLazyState,
      stopWatch = stopWatch,
      modifier = Modifier.fillMaxSize()
        .weight(0.2f)
    )
  }
}

@Composable
fun CharacterCard(
  index: Int,
  character: SmashCharacter,
  modifier: Modifier
) {
  Card(
    modifier = modifier
      .border(BorderStroke(2.dp, CONTENT_CARD_BORDER_COLOR))
      .background(CONTENT_CARD_BACKGROUND_COLOR)
  ) {
    Row {
      Text(
        text = "${index + 1}.",
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
          .size(60.dp)
          .padding(top = BASE_PADDING / 2, bottom = BASE_PADDING / 2)
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

