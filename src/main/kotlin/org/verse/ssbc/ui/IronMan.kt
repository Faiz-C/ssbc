package org.verse.ssbc.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.model.SmashCharacter
import org.verse.ssbc.service.IronManService

class IronMan : View {

  companion object {
    private val BASE_PADDING = 4.dp
  }

  private val ironManService: IronManService = IronManService(CharacterDao(), IronManDao())

  private lateinit var startOrNextText: MutableState<String>
  private lateinit var time: MutableState<String>

  private lateinit var timeSplits: SnapshotStateList<String>
  private lateinit var timeSplitsLazyState: LazyListState

  private lateinit var currentCharacter: MutableState<SmashCharacter>
  private lateinit var playedCharacters: SnapshotStateList<SmashCharacter>
  private lateinit var playedCharactersLazyState: LazyListState

  private lateinit var coroutineScope: CoroutineScope



  @Composable
  @Preview
  override fun render() {
    this.startOrNextText = remember { mutableStateOf("Start") }
    this.time = remember { mutableStateOf("00:00:00.000") }
    this.timeSplits = remember { mutableStateListOf() }
    this.timeSplitsLazyState = rememberLazyListState()
    this.currentCharacter = remember { mutableStateOf(ironManService.currentCharacter) }
    this.playedCharacters = remember { mutableStateListOf() }
    this.playedCharactersLazyState = rememberLazyListState()
    this.coroutineScope = rememberCoroutineScope()

    Column(
      modifier = Modifier.wrapContentSize()
        .padding(BASE_PADDING * 3),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth()
          .weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        characterDisplay(this)
      }
      Row(
        modifier = Modifier.fillMaxWidth()
          .weight(0.8f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        timerDisplay(this)
        optionsDisplay(this)
      }
    }
  }

  @Composable
  private fun characterDisplay(scope: RowScope) {
    scope.apply {
      Card(
        modifier = Modifier.fillMaxSize()
          .weight(0.6f)
          .padding(end = BASE_PADDING)
      ) {
        Column(
          modifier = Modifier.wrapContentSize()
            .fillMaxSize()
        ) {
          title(
            text = "Current Character",
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )

          characterLogo(
            imageBitmap = currentCharacter.value.imageBitmap,
            modifier = Modifier.align(Alignment.CenterHorizontally)
              .fillMaxSize(0.75f)
          )

          Text(
            text = currentCharacter.value.name,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
          )
        }
      }

      Card(
        modifier = Modifier.fillMaxSize()
          .padding(start = BASE_PADDING)
          .weight(1f)
      ) {
        Column(
          modifier = Modifier.wrapContentSize()
            .fillMaxSize()
        ) {

          title(
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
              itemsIndexed(playedCharacters) {i, character ->
                Row (
                  modifier = Modifier.wrapContentSize()
                    .align(Alignment.Center)
                    .drawBehind {
                      val strokeWidth = (1.dp * density).value
                      val y = size.height - strokeWidth / 2

                      drawLine(
                        brush = SolidColor(Color.White),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Square,
                        start = Offset.Zero.copy(y = y),
                        end = Offset(x = size.width, y = y)
                      )
                    }
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

                  characterLogo(
                    imageBitmap = character.imageBitmap,
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

          Row(
            modifier = Modifier.fillMaxSize()
              .weight(0.4f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {

            Button(
              modifier = Modifier.align(Alignment.CenterVertically),
              onClick = {
                coroutineScope.launch {
                  if (!ironManService.inProgress) {
                    ironManService.startRun()
                    startOrNextText.value = "Next"
                  } else {
                    // will return a split if we have made it there
                    ironManService.markCurrentPlayed()?.let {
                      timeSplits.add(it)
                      timeSplitsLazyState.animateScrollToItem(timeSplits.size - 1)
                    }
                    playedCharacters.add(ironManService.currentCharacter)
                    playedCharactersLazyState.animateScrollToItem(playedCharacters.size - 1)
                  }

                  if (ironManService.complete()) return@launch

                  for (i in 0 .. 15) {
                    currentCharacter.value = ironManService.next()
                    delay(((i + 1) * 15).toLong())
                  }

                }
              }
            ) {
              Text(text = startOrNextText.value)
            }

            Button(
              modifier = Modifier.align(Alignment.CenterVertically)
                .padding(start = BASE_PADDING),
              onClick = {
                playedCharacters.clear()
                ironManService.resetRun()
                startOrNextText.value = "Start"
              }
            ) {
              Text("Reset")
            }
          }
        }
      }
    }
  }

  @Composable
  private fun timerDisplay(scope: RowScope) {
    // Needs to be a more efficient way to do this
    // this runs cpu like mad (as expected)
    coroutineScope.launch {
      while (isActive) {
        time.value = ironManService.currentTime()
        delay(10L)
      }
    }

    scope.apply {
      Card(
        modifier = Modifier.fillMaxSize()
          .weight(1f)
          .padding(top = BASE_PADDING * 2)
      ) {
        Column(
          modifier = Modifier.wrapContentSize()
            .fillMaxSize()
        ) {

          title(
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
              state = timeSplitsLazyState,
              modifier = Modifier.wrapContentSize(),
              verticalArrangement = Arrangement.spacedBy(BASE_PADDING),
            ) {
              items(timeSplits) {
                Row(
                  modifier = Modifier.wrapContentSize()
                    .align(Alignment.Center)
                    .drawBehind {
                      val strokeWidth = (1.dp * density).value
                      val y = size.height - strokeWidth / 2

                      drawLine(
                        brush = SolidColor(Color.White),
                        strokeWidth = strokeWidth,
                        cap = StrokeCap.Square,
                        start = Offset.Zero.copy(y = y),
                        end = Offset(x = size.width, y = y)
                      )
                    }
                ) {
                  Text(
                    text = it,
                    modifier = Modifier.fillMaxSize()
                      .padding(start = BASE_PADDING * 2)
                      .align(Alignment.CenterVertically),
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp
                  )
                }
              }
            }
          }

          Text(
            text = time.value,
            modifier = Modifier.align(Alignment.CenterHorizontally)
              .weight(0.2f),
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp
          )
        }
      }
    }
  }

  @Composable
  private fun optionsDisplay(scope: RowScope) {
    scope.apply {
      Card(
        modifier = Modifier.fillMaxSize()
          .weight(0.4f)
          .padding(top = BASE_PADDING * 2, start = BASE_PADDING * 2)
      ) {
        Column(
          modifier = Modifier.wrapContentSize()
            .fillMaxSize()
        ) {
          Text(
            text = "Section 3.1",
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
          Text(
            text = "Section 3.2",
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
        }
      }
    }
  }

  @Composable
  private fun characterLogo(imageBitmap: ImageBitmap, modifier: Modifier) {
    Image(
      bitmap = imageBitmap,
      contentDescription = "Character Logo",
      modifier = modifier
    )
  }

  @Composable
  private fun title(text: String, modifier: Modifier) {
    Text(
      text = text,
      modifier = modifier,
      fontWeight = FontWeight.SemiBold,
      fontStyle = FontStyle.Italic,
      fontSize = 24.sp
    )
  }

  override fun name() = "Iron Man"

}