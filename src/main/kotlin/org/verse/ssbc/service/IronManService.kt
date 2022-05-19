package org.verse.ssbc.service

import org.verse.ssbc.dao.CharacterDao
import org.verse.ssbc.dao.IronManDao
import org.verse.ssbc.model.IronMan
import org.verse.ssbc.model.SmashCharacter
import java.time.LocalDateTime

class IronManService(
  private val characterDao: CharacterDao,
  private val ironManDao: IronManDao
) {

  private val characters: Set<SmashCharacter> = characterDao.getAll().toSet()

  var inProgress: Boolean = false
  var currentCharacter: SmashCharacter = this.characters.first()
  private val current: IronMan = IronMan()

  fun startRun(): IronMan {
    this.current.startTime = LocalDateTime.now()
    this.current.charactersPlayed.clear()
    this.inProgress = true
    return current
  }

  fun resetRun() {
    // Need to fix this because this will save without a run happening
    this.stopRun()
    this.startRun()
  }

  private fun stopRun() {
    this.current.endTime = LocalDateTime.now()
    this.inProgress = false
    this.ironManDao.insert(this.current)
  }

  fun next(): SmashCharacter {
    this.currentCharacter = this.characters.minus(this.current.charactersPlayed).random()
    return this.currentCharacter
  }

  fun markCurrentPlayed() {
    this.current.charactersPlayed.add(this.currentCharacter)
  }

  fun complete(): Boolean {
    return this.characters.size == this.current.charactersPlayed.size
  }

  fun getPastRuns(): List<IronMan> {
    return ironManDao.getAll()
  }

  fun playedCharacters(): List<SmashCharacter> {
    return this.current.charactersPlayed.toList()
  }

}