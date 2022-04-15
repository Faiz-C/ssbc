package org.verse.ssbc.views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.service.IronManService

class IronMan : View {

  companion object {
    private val padding = 12.dp
  }

  private val ironManService: IronManService = IronManService(CharacterDao(), IronManDao())


  @Composable
  @Preview
  override fun render() {
    Column(
      modifier = Modifier.fillMaxSize()
        .wrapContentSize(),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth()
          .weight(1f)
          .background(Color.Red),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
        Button(
          modifier = Modifier.align(Alignment.CenterVertically),
          onClick = {
            ironManService.startRun()
          }
        ) {
          Text("Start")
        }
        Spacer(Modifier.size(padding))
        Button(
          modifier = Modifier.align(Alignment.CenterVertically),
          onClick = {
            ironManService.stopRun()
          }
        ) {
          Text("Stop")
        }
      }
      Row(
        modifier = Modifier.fillMaxWidth()
          .weight(1f)
          .background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
      ) {
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
      }
    }
  }

  override fun name() = "Iron Man"

}