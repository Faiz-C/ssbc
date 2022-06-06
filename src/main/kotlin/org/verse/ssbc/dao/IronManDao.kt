package org.verse.ssbc.dao

import org.jetbrains.exposed.sql.batchInsert
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAllBatched
import org.jetbrains.exposed.sql.transactions.transaction
import org.verse.ssbc.model.IronMan
import org.verse.ssbc.model.SmashCharacter


class IronManDao {

  companion object {
    private val ironManTable = Tables.IronMan
    private val ironManCharacterTable = Tables.IronManCharacter
    private val characterTable = Tables.Character
  }

  fun insert(ironMan: IronMan): Int {
    return transaction {
      val id: Int = ironManTable.insert {
        it[startTime] = ironMan.startTime
        it[endTime] = ironMan.endTime
        it[complete] = ironMan.complete
      } get ironManTable.id

      ironManCharacterTable.batchInsert(ironMan.charactersPlayed) {
        this[ironManCharacterTable.ironManId] = id
        this[ironManCharacterTable.characterName] = it.name
      }

      id
    }
  }

  fun getAll(): List<IronMan> {
    val ironMans = mutableMapOf<Int, IronMan>()
    transaction {
      ironManTable
        .leftJoin(ironManCharacterTable)
        .leftJoin(characterTable)
        .selectAllBatched()
        .forEach { results ->
          results.forEach { result ->
            val id = result[ironManTable.id]
            ironMans.computeIfAbsent(id) {
              IronMan(
                id = it,
                startTime = result[ironManTable.startTime],
                endTime = result[ironManTable.endTime],
                complete = result[ironManTable.complete]
              )
            }

            // This check is important because there could be a case where there is a
            // 1 -> 0 relationship on a run (no characters completed)
            result.getOrNull(characterTable.name)?.let {
              ironMans[id]!!.charactersPlayed.add(
                SmashCharacter(
                  name = result[characterTable.name],
                  imageName = result[characterTable.imageName],
                  fightersPass = result[characterTable.fightersPass]
                )
              )
            }
          }
        }
    }
    return ironMans.values.toList()
  }

}