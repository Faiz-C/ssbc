package org.verse.ssbc.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.model.SmashCharacter
import org.verse.ssbc.service.IronManService

class IronMan : View {

  companion object {
    private val BASE_PADDING = 4.dp
  }

  private val ironManService: IronManService = IronManService(CharacterDao(), IronManDao())

  @Composable
  @Preview
  override fun render() {
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
        val characterState = remember { mutableStateOf(ironManService.currentCharacter) }
        characterDisplay(this, characterState)
        statusDisplay(this, characterState)
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
  private fun characterDisplay(scope: RowScope, characterState: MutableState<SmashCharacter>) {
    scope.apply {
      Card(
        modifier = Modifier.fillMaxSize()
          .weight(0.65f)
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
            imageBitmap = characterState.value.imageBitmap,
            modifier = Modifier.align(Alignment.CenterHorizontally)
              .fillMaxSize(0.75f)
          )

          Text(
            text = characterState.value.name,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp
          )
        }
      }
    }
  }

  @Composable
  private fun timerDisplay(scope: RowScope) {
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
          Text(
            text = "Section 2.1",
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
          Text(
            text = "Section 2.2",
            modifier = Modifier.align(Alignment.CenterHorizontally)
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
  private fun statusDisplay(scope: RowScope, characterState: MutableState<SmashCharacter>) {
    scope.apply {
      Card(
        modifier = Modifier.fillMaxSize()
          .weight(1f)
          .padding(start = BASE_PADDING)
      ) {
        Column(
          modifier = Modifier.wrapContentSize()
            .fillMaxSize()
        ) {

          title(
            text = "Played",
            modifier = Modifier.align(Alignment.CenterHorizontally)
              .padding(bottom = BASE_PADDING * 2)
          )

          val listState = remember { mutableStateListOf<SmashCharacter>() }

          charactersPlayedStatus(this, listState)

          Row(
            modifier = Modifier.fillMaxSize()
              .weight(0.7f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
          ) {
            val textState = remember { mutableStateOf("Start") }

            Button(
              modifier = Modifier.align(Alignment.CenterVertically),
              onClick = {
                if (!ironManService.inProgress) {
                  ironManService.startRun()
                  textState.value = "Next"
                } else {
                  val next: SmashCharacter = ironManService.next()
                  characterState.value = next
                  listState.add(next)
                }
              }
            ) {
              Text(text = textState.value)
            }

            Button(
              modifier = Modifier.align(Alignment.CenterVertically)
                .padding(start = BASE_PADDING),
              onClick = {
                listState.clear()
                ironManService.resetRun()
                textState.value = "Start"
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
  private fun charactersPlayedStatus(scope: ColumnScope, listState: MutableList<SmashCharacter>) {
    scope.apply {
      Box(
        modifier = Modifier.wrapContentSize()
          .weight(1f)
          .align(Alignment.CenterHorizontally)
          .background(Color.Blue)
          .clip(RoundedCornerShape(10.dp))
          .border(
            BorderStroke(3.dp, MaterialTheme.colors.primaryVariant),
            RoundedCornerShape(10.dp)
          )
          .fillMaxWidth(0.7f)
          .fillMaxHeight()
      ) {
        LazyColumn(
          modifier = Modifier.wrapContentSize()
            .padding(start = BASE_PADDING, end = BASE_PADDING),
          verticalArrangement = Arrangement.spacedBy(BASE_PADDING)
        ) {
          items(listState) {
            Row (
              modifier = Modifier.wrapContentSize()
                .border(
                  BorderStroke(3.dp, Color.White),
                  RoundedCornerShape(10.dp)
                )
                .align(Alignment.Center)
            ) {
              characterLogo(
                imageBitmap = it.imageBitmap,
                modifier = Modifier
                  .align(Alignment.CenterVertically)
                  .weight(0.2f)
                  .fillMaxSize()
              )
              Text(
                text = it.name,
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
      fontWeight = FontWeight.Medium,
      fontStyle = FontStyle.Italic,
      fontSize = 24.sp
    )
  }

  override fun name() = "Iron Man"

}