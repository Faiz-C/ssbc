package org.verse.ssbc.dao

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import org.verse.ssbc.model.SmashCharacter

class CharacterDao {

  companion object {
    private val table = Tables.Character
  }

  fun getAll(): List<SmashCharacter> {
    return transaction {
      return@transaction table.selectAll().map {
        mapToSmashCharacter(it)
      }
    }
  }

  fun get(name: String): SmashCharacter {
    return transaction {
      val data: ResultRow = table.select { table.name eq name }.first()
      return@transaction mapToSmashCharacter(data)
    }
  }

  fun get(vararg names: String): List<SmashCharacter> {
    return transaction {
      return@transaction table.select{ table.name inList names.toSet() }.map {
        mapToSmashCharacter(it)
      }
    }
  }

  private fun mapToSmashCharacter(row: ResultRow): SmashCharacter {
    return SmashCharacter(
      row[table.name],
      row[table.imageName],
      row[table.fightersPass]
    )
  }

}