package org.verse.ssbc.ui.views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.components.PreviousRunsDisplay

class Stats(
  private val ironManTracker: IronManTracker
) : View {

  override val name: String = "Stats"

  @Composable
  @Preview
  override fun render() {
    Column(
      modifier = Modifier.wrapContentSize()
        .padding(all = BASE_PADDING * 2),
    ) {
      Card(
        modifier = Modifier.fillMaxSize()
      ) {
        PreviousRunsDisplay(
          modifier = Modifier.fillMaxSize(),
          ironManTracker = ironManTracker
        )
      }
    }
  }
}