package org.verse.ssbc.model

import org.jetbrains.skia.Image
import java.io.File

data class SmashCharacter(
  var name: String = "",
  var imageName: String = "",
  var in64: Boolean = false,
  var inMelee: Boolean = false,
  var inBrawl: Boolean = false,
  var inWiiU: Boolean = false,
  var inUltimate: Boolean = false,
  var image: Image = getImageFromResources(imageName)
)

private fun getImageFromResources(name: String): Image {
  val file = File("src/main/resources/images/$name.png")
  return Image.makeFromEncoded(file.readBytes())
}

