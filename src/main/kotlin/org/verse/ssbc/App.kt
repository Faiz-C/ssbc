package org.verse.ssbc

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import org.verse.ssbc.utils.bootstrapDb
import org.verse.ssbc.ui.views.IronMan
import org.verse.ssbc.ui.views.Stats
import org.verse.ssbc.ui.views.ViewContainer

fun main() {
  bootstrapDb()
  singleWindowApplication(
    title = "SSB Challenge",
    state = WindowState(width = 800.dp, height = 600.dp),
    resizable = false
  ) {
    MaterialTheme(
      colors = darkColors(
        primary = Color(0xFF3F51B5),
        primaryVariant = Color(0xFF2C387E),
        secondary = Color(0xFFF50057),
        secondaryVariant = Color(0xFFAB003C),
        error = Color(0xFFD32F2F),
        onPrimary = Color.White
      )
    ) {
      ViewContainer(listOf(
        IronMan(),
        Stats()
      )).render()
    }
  }
}