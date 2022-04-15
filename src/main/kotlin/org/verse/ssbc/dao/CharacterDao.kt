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

  fun getSmash64Characters(): List<SmashCharacter> {
    return transaction {
      return@transaction table.select { table.in64 eq true }.map {
        mapToSmashCharacter(it)
      }
    }
  }

  fun getMeleeCharacters(): List<SmashCharacter> {
    return transaction {
      return@transaction table.select { table.inMelee eq true }.map {
        mapToSmashCharacter(it)
      }
    }
  }

  fun getBrawlCharacters(): List<SmashCharacter> {
    return transaction {
      return@transaction table.select { table.inBrawl eq true }.map {
        mapToSmashCharacter(it)
      }
    }
  }

  fun getWiiUCharacters(): List<SmashCharacter> {
    return transaction {
      return@transaction table.select { table.inWiiU eq true }.map {
        mapToSmashCharacter(it)
      }
    }
  }

  fun getUltimateCharacters(): List<SmashCharacter> {
    return transaction {
      return@transaction table.select { table.inUltimate eq true }.map {
        mapToSmashCharacter(it)
      }
    }
  }

  private fun mapToSmashCharacter(row: ResultRow): SmashCharacter {
    return SmashCharacter(
      row[table.name],
      row[table.imageName],
      row[table.in64],
      row[table.inMelee],
      row[table.inBrawl],
      row[table.inWiiU],
      row[table.inUltimate]
    )
  }

}