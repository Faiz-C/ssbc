package org.verse.ssbc.model

import java.time.LocalDateTime

data class IronMan(
  var id: Int = 0, // This value only matters for existing runs
  var startTime: LocalDateTime = LocalDateTime.now(),
  var endTime: LocalDateTime = LocalDateTime.now(),
  var charactersPlayed: MutableSet<SmashCharacter> = mutableSetOf(),
  var complete: Boolean = false
)
