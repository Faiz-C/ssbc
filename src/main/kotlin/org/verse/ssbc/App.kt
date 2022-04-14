package org.verse.ssbc

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import org.verse.ssbc.utils.bootstrapDb
import org.verse.ssbc.views.IronMan
import org.verse.ssbc.views.Options
import org.verse.ssbc.views.Stats
import org.verse.ssbc.views.ViewContainer

fun main() {
  bootstrapDb()
  singleWindowApplication(
    title = "SSB Challenge",
    state = WindowState(width = 1200.dp, height = 800.dp)
  ) {
    ViewContainer(listOf(
      IronMan(),
      Stats(),
      Options()
    )).render()
  }
}
