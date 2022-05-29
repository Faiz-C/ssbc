package org.verse.ssbc.ui.common

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


val BASE_PADDING: Dp = 4.dp

fun Modifier.underline(color: Color): Modifier {
  return this.drawBehind {
    val strokeWidth = (1.dp * density).value
    val y = size.height - strokeWidth / 2

    drawLine(
      brush = SolidColor(color),
      strokeWidth = strokeWidth,
      cap = StrokeCap.Square,
      start = Offset.Zero.copy(y = y),
      end = Offset(x = size.width, y = y)
    )
  }
}