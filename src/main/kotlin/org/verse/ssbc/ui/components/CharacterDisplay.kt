package org.verse.ssbc.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.verse.ssbc.model.SmashCharacter

@Composable
fun CharacterDisplay(
  character: SmashCharacter,
  modifier: Modifier
) {
  Column(
    modifier = modifier
  ) {
    Header(
      text = "Current Character",
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )

    CharacterLogo(
      character = character,
      modifier = Modifier.align(Alignment.CenterHorizontally)
        .fillMaxSize(0.8f)
    )

    Text(
      text = character.name,
      modifier = Modifier.align(Alignment.CenterHorizontally),
      fontWeight = FontWeight.SemiBold,
      fontSize = 20.sp
    )
  }
}