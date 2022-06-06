package org.verse.ssbc.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import org.verse.ssbc.config.Config
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.modules.StopWatch
import org.verse.ssbc.ui.common.BASE_PADDING

@Composable
fun Settings(
  config: Config,
  stopWatch: StopWatch,
  ironManTracker: IronManTracker,
  modifier: Modifier
) {
  Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(BASE_PADDING)
  ) {
    Header(
      text = "Settings",
      modifier = Modifier.align(Alignment.CenterHorizontally)
        .padding(bottom = BASE_PADDING * 2)
    )

    SwitchWithText(
      modifier = Modifier.fillMaxSize()
        .weight(1f)
        .padding(start = BASE_PADDING * 4),
      text = "Show Milliseconds",
      checked = config.showMilliseconds,
      onCheckedChange = {
        config.showMilliseconds = it
        if (!stopWatch.isRunning()) stopWatch.reset()
        config.save()
      }
    )

    SwitchWithText(
      modifier = Modifier.fillMaxSize()
        .weight(1f)
        .padding(start = BASE_PADDING * 4),
      text = "Include DLC 1 Characters",
      checked = config.includeDlc1,
      onCheckedChange = {
        config.includeDlc1 = it
        if (!ironManTracker.inProgress) ironManTracker.reset()
        config.save()
      }
    )

    SwitchWithText(
      modifier = Modifier.fillMaxSize()
        .weight(1f)
        .padding(start = BASE_PADDING * 4),
      text = "Include DLC 2 Characters",
      checked = config.includeDlc2,
      onCheckedChange = {
        config.includeDlc2 = it
        if (!ironManTracker.inProgress) ironManTracker.reset()
        config.save()
      }
    )
  }
}

@Composable
private fun SwitchWithText(
  modifier: Modifier,
  text: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Row(
    modifier = modifier
  ) {
    Text(
      text = text,
      modifier = Modifier.weight(1f),
      fontSize = 16.sp
    )

    Switch(
      modifier = Modifier.weight(1f),
      checked = checked,
      onCheckedChange = onCheckedChange,
      colors = SwitchDefaults.colors(
        checkedThumbColor = MaterialTheme.colors.primaryVariant
      )
    )
  }
}