package org.verse.ssbc.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Switch
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
    modifier = modifier
  ) {
    Header(
      text = "Settings",
      modifier = Modifier.align(Alignment.CenterHorizontally)
        .padding(bottom = BASE_PADDING * 4)
    )

    SwitchWithText(
      modifier = Modifier.fillMaxSize()
        .weight(1f)
        .align(Alignment.CenterHorizontally)
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
        .align(Alignment.CenterHorizontally)
        .padding(start = BASE_PADDING * 4),
    text = "Include DCL 1 Characters",
      checked = config.includeDcl1,
      onCheckedChange = {
        config.includeDcl1 = it
        if (!ironManTracker.inProgress) ironManTracker.reset()
        config.save()
      }
    )

    SwitchWithText(
      modifier = Modifier.fillMaxSize()
        .weight(1f)
        .align(Alignment.CenterHorizontally)
        .padding(start = BASE_PADDING * 4),
    text = "Include DCL 2 Characters",
      checked = config.includeDcl2,
      onCheckedChange = {
        config.includeDcl2 = it
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
    modifier = modifier,
    horizontalArrangement = Arrangement.End
  ) {
    Text(
      text = text,
      modifier = Modifier.wrapContentSize()
        .fillMaxSize()
        .weight(1f),
      fontSize = 14.sp
    )

    Switch(
      modifier = Modifier
        .weight(1f),
      checked = checked,
      onCheckedChange = onCheckedChange
    )
  }
}