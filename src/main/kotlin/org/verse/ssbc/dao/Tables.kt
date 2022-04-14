package org.verse.ssbc.dao

import org.jetbrains.exposed.sql.Table

sealed class Tables private constructor() {

  internal object Character: Table() {
    val name = varchar("NAME", 20)
    val imageName = varchar("IMAGE_NAME", 20)
    val in64 = bool("64")
    val inMelee = bool("MELEE")
    val inBrawl = bool("BRAWL")
    val inWiiU = bool("WII_U")
    val inUltimate = bool("ULTIMATE")

    override val primaryKey = PrimaryKey(name, name = "CHARACTER_PK")
  }

  internal object IronManRun: Table() {
    val id = integer("ID").autoIncrement()
    val startTime = long("START_TIME")
    val endTime = long("END_TIME")

    override val primaryKey = PrimaryKey(id, name = "IRONMAN_RUN_PK")
  }

  internal object IronManRunCharacter: Table() {
    val ironManId = integer("IRONMAN_ID") references IronManRun.id
    val characterName = varchar("CHARACTER_NAME", 20) references Character.name

    override val primaryKey = PrimaryKey(ironManId, characterName, name = "IRONMAN_RUN_CHARACTER_PK")
  }

}