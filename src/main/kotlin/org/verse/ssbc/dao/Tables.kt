package org.verse.ssbc.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime

sealed class Tables private constructor() {

  internal object Character: Table() {
    val name = varchar("NAME", 20)
    val imageName = varchar("IMAGE_NAME", 20)
    val fightersPass = integer("FIGHTERS_PASS") // 0 = Base Game, 1 = Fighters Pass 1, 2 = Fighters Pass 2

    override val primaryKey = PrimaryKey(name, name = "CHARACTER_PK")
  }

  internal object IronMan: Table() {
    val id = integer("ID").autoIncrement()
    val startTime = datetime("START_TIME")
    val endTime = datetime("END_TIME")

    override val primaryKey = PrimaryKey(id, name = "IRONMAN_RUN_PK")
  }

  internal object IronManCharacter: Table() {
    val ironManId = integer("IRONMAN_ID") references IronMan.id
    val characterName = varchar("CHARACTER_NAME", 20) references Character.name

    override val primaryKey = PrimaryKey(ironManId, characterName, name = "IRONMAN_RUN_CHARACTER_PK")
  }

}