package org.verse.ssbc.model

data class IronManRun(
  val id: Int,
  val time: Double? = 0.0,
  val charactersPlayed: MutableList<SmashCharacter>
)
