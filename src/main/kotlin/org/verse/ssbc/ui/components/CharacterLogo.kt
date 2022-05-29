package org.verse.ssbc.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.verse.ssbc.model.SmashCharacter

@Composable
fun CharacterLogo(
  character: SmashCharacter,
  modifier: Modifier
) {
  Image(
    bitmap = character.imageBitmap,
    contentDescription = character.name,
    modifier = modifier
  )
}