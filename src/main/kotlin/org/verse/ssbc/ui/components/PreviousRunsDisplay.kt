package org.verse.ssbc.ui.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.verse.ssbc.model.IronMan
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.ui.common.BASE_PADDING
import org.verse.ssbc.ui.common.CONTENT_CARD_BACKGROUND_COLOR
import org.verse.ssbc.ui.common.CONTENT_CARD_BORDER_COLOR
import java.time.Duration
import java.time.format.DateTimeFormatter

@Composable
fun PreviousRunsDisplay(
  modifier: Modifier,
  ironManTracker: IronManTracker
) {
  val lazyListState = rememberLazyListState()

  Column(
    modifier = modifier
  ) {
    Header(
      text = "Past Runs",
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )

    ListBox(
      modifier = Modifier.padding(all = BASE_PADDING * 2)
        .align(Alignment.CenterHorizontally),
      lazyListState = lazyListState
    ) {
      items(ironManTracker.previousRuns) {
        ExpandableIronManCard(
          run = it,
          modifier = Modifier.fillMaxWidth()
        )
      }
    }
  }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ExpandableIronManCard(
  run: IronMan,
  modifier: Modifier
) {
  val dateTimeFormatter = remember { DateTimeFormatter.ofPattern("yyyy-MM-dd") }
  val expandState = remember { mutableStateOf(false) }
  val rotationState = animateFloatAsState(
    targetValue = if (expandState.value) 180f else 0f
  )

  Card(
    modifier = modifier
      .border(BorderStroke(2.dp, CONTENT_CARD_BORDER_COLOR))
      .background(CONTENT_CARD_BACKGROUND_COLOR)
      .animateContentSize(
        animationSpec = tween(
          durationMillis = 300,
          easing = LinearEasing
        )
      ),
    onClick = {
      expandState.value = !expandState.value
    }
  ) {
    Column(
      modifier = Modifier.fillMaxSize()
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
      ) {
        IconButton(
          modifier = Modifier.weight(0.5f)
            .alpha(ContentAlpha.medium)
            .rotate(rotationState.value),
          onClick = {
            expandState.value = !expandState.value
          }
        ) {
          Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "Characters Played"
          )
        }

        val startTime = run.startTime.toLocalTime().withNano(0)
        val endTime = run.endTime.toLocalTime().withNano(0)
        val timing = if (run.startTime.dayOfWeek == run.endTime.dayOfWeek) {
          "[${dateTimeFormatter.format(run.startTime)}, $startTime -- $endTime: ${getFormattedElapsedTime(run)}]"
        } else {
          "[${dateTimeFormatter.format(run.startTime)}, $startTime -- ${dateTimeFormatter.format(run.endTime)}, $endTime: ${getFormattedElapsedTime(run)}"
        }

        Text(
          text = "${run.id}: $timing ${run.charactersPlayed.size} Characters Completed ${if (run.complete) "(*)" else ""}",
          modifier = Modifier.fillMaxSize()
            .weight(6f),
          fontSize = 18.sp
        )
      }

      if (expandState.value) {
        Column {
          run.charactersPlayed.chunked(14).map { chunk ->
            Row(
              horizontalArrangement = Arrangement.SpaceEvenly
            ) {
              chunk.map {
                CharacterLogo(
                  character = it,
                  modifier = Modifier.size(60.dp)
                    .padding(bottom = BASE_PADDING)
                )
              }
            }
          }
        }
      }
    }
  }
}

fun getFormattedElapsedTime(run: IronMan): String {
  val elapsedTime = Duration.between(run.startTime, run.endTime)
  return "%02d:%02d:%02d".format(
    elapsedTime.toHoursPart(),
    elapsedTime.toMinutesPart(),
    elapsedTime.toSecondsPart()
  )
}
