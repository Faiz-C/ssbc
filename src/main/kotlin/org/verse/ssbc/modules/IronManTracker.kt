package org.verse.ssbc.modules

import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.delay
import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.model.IronMan
import org.verse.ssbc.model.SmashCharacter
import java.time.LocalDateTime

class IronManTracker(
  characterDao: CharacterDao,
  private val ironManDao: IronManDao
) {

  private val characters: Set<SmashCharacter> = characterDao.getAll().toSet()
  private val current: IronMan = IronMan()

  var inProgress: Boolean = false
  val played: SnapshotStateList<SmashCharacter> = mutableStateListOf()
  var currentCharacter: SmashCharacter by mutableStateOf(this.characters.first())
  var spinning: Boolean by mutableStateOf(false)

  suspend fun start() {
    if (this.inProgress) return

    this.current.startTime = LocalDateTime.now()
    this.inProgress = true
    this.next()
  }

  fun reset() {
    if (this.inProgress) {
      this.inProgress = false
      this.current.endTime = LocalDateTime.now()
      this.ironManDao.insert(this.current)
    }

    this.currentCharacter = this.characters.random()
    this.played.clear()
    this.current.charactersPlayed.clear()
  }

  suspend fun next() {
    if (this.complete()) return

    val choices = this.characters.minus(this.played)

    this.spinning = true // maybe want to change to a real lock

    for (i in 0 .. 15) {
      this.currentCharacter = choices.random()
      delay(((i + 1) * 15).toLong())
    }

    this.spinning = false
  }

  fun recordCurrent() {
    this.played.add(this.currentCharacter)
  }

  fun complete(): Boolean {
    return this.played.size == this.characters.size
  }

}