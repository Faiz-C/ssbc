package org.verse.ssbc.model

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import org.jetbrains.skia.Image
import java.io.File

data class SmashCharacter(
  val name: String = "",
  val imageName: String = "",
  val in64: Boolean = false,
  val inMelee: Boolean = false,
  val inBrawl: Boolean = false,
  val inWiiU: Boolean = false,
  val inUltimate: Boolean = false,
  val imageBitmap: ImageBitmap = getImageFromResources(imageName)
)

private fun getImageFromResources(name: String): ImageBitmap {
  val file = File("src/main/resources/images/$name.png")
  return Image.makeFromEncoded(file.readBytes()).toComposeImageBitmap()
}

