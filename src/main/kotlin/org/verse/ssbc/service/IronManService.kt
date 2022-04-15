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
  private val current: IronMan = IronMan()

  fun startRun(): IronMan {
    this.current.startTime = LocalDateTime.now()
    this.current.charactersPlayed.clear()
    return current
  }

  fun stopRun() {
    this.current.endTime = LocalDateTime.now()
    this.ironManDao.insert(this.current)
  }

  fun resetRun() {
    // Need to fix this because this will save without a run happening
    this.stopRun()
    this.startRun()
  }

  fun nextCharacter(): SmashCharacter {
    val character: SmashCharacter = this.characters.minus(this.current.charactersPlayed).random()
    this.current.charactersPlayed.add(character)
    return character
  }

  fun getPastRuns(): List<IronMan> {
    return ironManDao.getAll()
  }

}