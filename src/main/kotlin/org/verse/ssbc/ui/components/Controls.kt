package org.verse.ssbc.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.modules.StopWatch
import org.verse.ssbc.modules.TimeSplitCollection
import org.verse.ssbc.ui.common.BASE_PADDING

@Composable
fun Controls(
  ironManTracker: IronManTracker,
  characterLazyListState: LazyListState,
  timeSplitCollection: TimeSplitCollection,
  timeSplitLazyListState: LazyListState,
  stopWatch: StopWatch,
  modifier: Modifier
) {
  val coroutineScope: CoroutineScope = rememberCoroutineScope()
  var startNext: String by mutableStateOf("Start")

  Row(
    modifier = modifier,
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Center
  ) {
    Button(
      modifier = Modifier.align(Alignment.CenterVertically),
      onClick = {
        if (ironManTracker.spinning) return@Button

        coroutineScope.launch {
          if (!ironManTracker.inProgress) {
            startNext = "Next"
            ironManTracker.start()
            stopWatch.start()
          } else {
            ironManTracker.recordCurrent()
            characterLazyListState.animateScrollToItem(ironManTracker.played.size - 1)

            timeSplitCollection.add(stopWatch.time)

            if (timeSplitCollection.newSplit) {
              timeSplitLazyListState.animateScrollToItem(timeSplitCollection.timeSplits.size - 1)
            }

            ironManTracker.next()
          }
        }
      }
    ) {
      Text(text = startNext)
    }

    Button(
      modifier = Modifier.align(Alignment.CenterVertically)
        .padding(start = BASE_PADDING),
      onClick = {
        if (ironManTracker.spinning) return@Button

        coroutineScope.launch {
          stopWatch.reset()
          timeSplitCollection.clear()
          ironManTracker.reset()
          startNext = "Start"
        }
      }
    ) {
      Text("Reset")
    }
  }
}