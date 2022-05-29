package org.verse.ssbc.ui.components

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun Header(
  text: String,
  modifier: Modifier,
  fontSize: TextUnit = 24.sp
) {
  Text(
    text = text,
    modifier = modifier,
    fontWeight = FontWeight.SemiBold,
    fontStyle = FontStyle.Italic,
    fontSize = fontSize
  )
}