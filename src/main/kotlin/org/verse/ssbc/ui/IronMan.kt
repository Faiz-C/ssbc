package org.verse.ssbc.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.service.IronManService

class IronMan : View {

  companion object {
    private val BASE_PADDING = 4.dp
  }

  private val ironManService: IronManService = IronManService(CharacterDao(), IronManDao())

  // Move the Sections to their own methods
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
        characterDisplay(this)
        timerDisplay(this)
      }
      Row(
        modifier = Modifier.fillMaxWidth()
          .weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        statusDisplay(this)
      }
    }
  }

  @Composable
  private fun characterDisplay(scope: RowScope) {
    scope.apply {
      Card(
        modifier = Modifier.fillMaxSize()
          .weight(1f)
          .padding(end = BASE_PADDING)
      ) {
        Column(
          modifier = Modifier.wrapContentSize()
            .fillMaxSize()
        ) {
          Text(
            text = "Section 1.1",
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
          Text(
            text = "Section 1.2",
            modifier = Modifier.align(Alignment.CenterHorizontally)
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
          .padding(start = BASE_PADDING)
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
  private fun statusDisplay(scope: RowScope) {
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
            text = "Section 3.1",
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
          Text(
            text = "Section 3.2",
            modifier = Modifier.align(Alignment.CenterHorizontally)
          )
          /*
          val textState = remember { mutableStateOf(TextFieldValue()) }
          TextField(
          value = textState.value,
          onValueChange = {
            textState.value = it
          },
          modifier = Modifier.align(Alignment.CenterVertically)
            .background(Color.White)
          )
          Spacer(Modifier.size(padding))
          Button(
          modifier = Modifier.align(Alignment.CenterVertically),
          onClick = {
            textState.value = TextFieldValue(ironManService.getPastRuns().map {
              it.id
            }.joinToString(","))
          }
          ) {
            Text("Past Runs")
          }
          */
        }
      }
    }
  }



  override fun name() = "Iron Man"

}