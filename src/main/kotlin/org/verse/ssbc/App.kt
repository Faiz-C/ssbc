package org.verse.ssbc

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import org.verse.ssbc.utils.bootstrapDb
import org.verse.ssbc.ui.IronMan
import org.verse.ssbc.ui.Options
import org.verse.ssbc.ui.Stats
import org.verse.ssbc.ui.ViewContainer

fun main() {
  bootstrapDb()
  singleWindowApplication(
    title = "SSB Challenge",
    state = WindowState(width = 1200.dp, height = 800.dp),
    resizable = false
  ) {
    MaterialTheme(
      colors = darkColors(
        primary = Color(0xFF2C387E), // Standard Dark theme blue primary
        secondary = Color(0xFFAB003C), // Standard Dark theme magenta like secondary
        error = Color(0xFFD32F2F), // Standard Dark theme dark red error
        onPrimary = Color.White
      )
    ) {
      ViewContainer(listOf(
        IronMan(),
        Stats(),
        Options()
      )).render()
    }
  }
}