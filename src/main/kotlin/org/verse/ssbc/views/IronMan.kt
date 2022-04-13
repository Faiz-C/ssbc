package org.verse.ssbc.views

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

class IronMan : View {

  @Composable
  @Preview
  override fun render() {
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(Color.Red)
        .wrapContentSize(Alignment.Center)
    ) {
      Text(
        text = "IronMan View",
        fontWeight = FontWeight.Bold,
        color = Color.White,
        modifier = Modifier.align(Alignment.CenterHorizontally),
        textAlign = TextAlign.Center,
        fontSize = 25.sp
      )
    }
  }

  override fun name() = "Iron Man"

}