package org.verse.ssbc.modules

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import kotlinx.coroutines.delay
import org.verse.ssbc.config.Config
import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.model.IronMan
import org.verse.ssbc.model.SmashCharacter
import java.time.LocalDateTime

class IronManTracker(
  private val characterDao: CharacterDao,
  private val ironManDao: IronManDao,
  private val config: Config
) {

  private var characters: Set<SmashCharacter> = this.loadCharacters()
  private val current: IronMan = IronMan()

  var inProgress: Boolean = false
  val played: SnapshotStateList<SmashCharacter> = mutableStateListOf()
  var currentCharacter: SmashCharacter by mutableStateOf(this.characters.first())
  var spinning: Boolean by mutableStateOf(false)
  var previousRuns: SnapshotStateList<IronMan> = mutableStateListOf<IronMan>().also {
    it.addAll(this.ironManDao.getAll())
  }

  var complete: Boolean by mutableStateOf(false)

  suspend fun start() {
    if (this.inProgress) return

    this.current.startTime = LocalDateTime.now()
    this.inProgress = true
    this.next()
  }

  fun reset() {
    this.end()
    this.characters = this.loadCharacters()
    this.currentCharacter = this.characters.random()
    this.played.clear()
  }

  suspend fun next() {
    if (this.complete) return

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
    this.complete = this.played.size == this.characters.size
  }

  private fun end() {
    if (!this.inProgress) return

    this.inProgress = false
    this.current.endTime = LocalDateTime.now()
    this.current.charactersPlayed = this.played.toMutableSet()
    this.current.complete = this.complete

    val id = this.ironManDao.insert(this.current)
    this.previousRuns.add(this.current.copy(
      id = id
    ))

    this.complete = false
  }

  private fun loadCharacters(): Set<SmashCharacter> {
    var characters = this.characterDao.getAll()

    if (!config.includeDlc1) {
      characters = characters.filter {
        it.fightersPass != 1
      }
    }

    if (!config.includeDlc2) {
      characters = characters.filter {
        it.fightersPass != 2
      }
    }

    return characters.toMutableSet()
  }

}