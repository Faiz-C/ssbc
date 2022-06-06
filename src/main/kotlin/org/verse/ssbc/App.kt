package org.verse.ssbc

import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.singleWindowApplication
import org.verse.ssbc.config.Config
import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.modules.IronManTracker
import org.verse.ssbc.ui.views.IronMan
import org.verse.ssbc.ui.views.Stats
import org.verse.ssbc.ui.views.ViewContainer
import org.verse.ssbc.utils.bootstrapDb

fun main() {
  bootstrapDb()
  singleWindowApplication(
    title = "SSB Challenge",
    state = WindowState(width = 900.dp, height = 750.dp),
    resizable = false
  ) {
    MaterialTheme(
      colors = darkColors(
        primary = Color(0xFF3F51B5),
        primaryVariant = Color(0xFF29379d),
        secondary = Color(0xFF871505),
        secondaryVariant = Color(0xFF6a0c03),
        error = Color(0xFFD32F2F),
        onPrimary = Color.White
      )
    ) {

      val config: Config = Config.load()
      val ironManTracker: IronManTracker = remember { IronManTracker(CharacterDao(), IronManDao(), config) }

      ViewContainer(listOf(
        IronMan(ironManTracker, config),
        Stats(ironManTracker)
      )).render()
    }
  }
}