package org.verse.ssbc.ui.views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.verse.ssbc.config.Config
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.modules.StopWatch
import org.verse.ssbc.modules.TimeSplitCollection
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.components.CharacterDisplay
import org.verse.ssbc.ui.components.CharacterTracker
import org.verse.ssbc.ui.components.Settings
import org.verse.ssbc.ui.components.TimeSplits

class IronMan(
  private val ironManTracker: IronManTracker,
  private val config: Config
) : View {

  override val name: String = "Ironman"

  @Composable
  @Preview
  override fun render() {
    val timeSplitCollection: TimeSplitCollection = remember { TimeSplitCollection(timeSplitCondition = {
      ironManTracker.complete || ironManTracker.played.size % 10 == 0
    }) }
    val stopWatch: StopWatch = remember { StopWatch(
      config = config
    ) }
    val timeSplitsLazyState = rememberLazyListState()
    val playedCharactersLazyState = rememberLazyListState()

    Column(
      modifier = Modifier.wrapContentSize()
        .padding(all = BASE_PADDING * 2),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth()
          .weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Card(
          modifier = Modifier.fillMaxSize()
            .weight(0.6f)
            .padding(end = BASE_PADDING)
        ) {
          CharacterDisplay(
            character = ironManTracker.currentCharacter,
            modifier = Modifier.fillMaxSize()
          )
        }

        Card(
          modifier = Modifier.fillMaxSize()
            .padding(start = BASE_PADDING)
            .weight(1f)
        ) {
          CharacterTracker(
            ironManTracker = ironManTracker,
            playedCharactersLazyState = playedCharactersLazyState,
            timeSplitCollection = timeSplitCollection,
            timeSplitsLazyState = timeSplitsLazyState,
            stopWatch = stopWatch,
            modifier = Modifier.fillMaxSize()
          )
        }
      }

      Row(
        modifier = Modifier.fillMaxWidth()
          .weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Card(
          modifier = Modifier.fillMaxSize()
            .weight(1f)
            .padding(top = BASE_PADDING * 2)
        ) {
          TimeSplits(
            timeSplitCollection = timeSplitCollection,
            stopWatch = stopWatch,
            modifier = Modifier.fillMaxSize(),
            lazyListState = timeSplitsLazyState
          )
        }

        Card(
          modifier = Modifier.fillMaxSize()
            .weight(0.5f)
            .padding(top = BASE_PADDING * 2, start = BASE_PADDING * 2)
        ) {
          Settings(
            config = config,
            stopWatch = stopWatch,
            ironManTracker = ironManTracker,
            modifier = Modifier.fillMaxSize()
          )
        }
      }
    }
  }

}