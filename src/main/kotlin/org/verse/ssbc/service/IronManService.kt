package org.verse.ssbc.service

import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.model.IronMan
import org.verse.ssbc.model.SmashCharacter
import org.verse.ssbc.utils.Timer
import java.time.LocalDateTime

class IronManService(
  characterDao: CharacterDao,
  private val ironManDao: IronManDao
) {

  private val characters: Set<SmashCharacter> = characterDao.getAll().toSet()
  private val current: IronMan = IronMan()
  private val timer: Timer = Timer()

  var inProgress: Boolean = false
  var currentCharacter: SmashCharacter = this.characters.first()

  fun startRun(): IronMan {
    this.current.startTime = LocalDateTime.now()
    this.current.charactersPlayed.clear()
    this.inProgress = true
    this.timer.start()
    return current
  }

  fun resetRun() {
    // Need to fix this because this will save without a run happening
    this.stopRun()
    this.startRun()
  }

  fun currentTime(): String {
    return this.timer.time()
  }

  fun next(): SmashCharacter {
    this.currentCharacter = this.characters.minus(this.current.charactersPlayed).random()
    return this.currentCharacter
  }

  fun markCurrentPlayed(): String? {
    this.current.charactersPlayed.add(this.currentCharacter)
    val totalCharactersPlayer = this.current.charactersPlayed.size
    return if (totalCharactersPlayer % 10 == 0 || this.complete()) {
      "Characters ${(totalCharactersPlayer - 9)} - ${totalCharactersPlayer}: ${this.timer.time()}"
    } else {
      null
    }
  }

  fun remainingCharacters(): Int {
    return this.characters.size - this.current.charactersPlayed.size
  }

  fun complete(): Boolean {
    return this.remainingCharacters() == 0
  }

  fun getPastRuns(): List<IronMan> {
    return ironManDao.getAll()
  }

  private fun stopRun() {
    this.current.endTime = LocalDateTime.now()
    this.inProgress = false
    this.timer.stop()
    this.ironManDao.insert(this.current)
  }

}