package org.verse.ssbc.utils

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import org.verse.ssbc.dao.Tables
import java.io.File

private const val dbName = "ssbc.db"
private const val characterScript = "src/main/resources/sql/characters.sql"
private val dbPath = "${System.getProperty("user.home")}/ssbc/$dbName"

private fun File.readToString(): String {
  var result = ""
  this.forEachLine {
    result += it + System.lineSeparator()
  }
  return result
}

private fun String.execAndDiscard() {
  TransactionManager.current().exec(this)
}

fun bootstrapDb() {
  // Create DB if doesn't exist
  setupDatabaseFile()

  // Connect to DB
  Database.connect("jdbc:sqlite:$dbPath")

  // Load the character insertion script
  val script: String = File(characterScript).readToString()

  transaction {

    // This creates if they don't exist
    SchemaUtils.create(
      Tables.Character,
      Tables.IronMan,
      Tables.IronManCharacter
    )

    script.execAndDiscard()
  }
}

private fun setupDatabaseFile() {
  val dbFile = File(dbPath)
  if (!dbFile.exists()) {
    dbFile.parentFile.mkdirs()
    dbFile.createNewFile()
    dbFile.setWritable(true, true)
    dbFile.setReadable(true, true)
    dbFile.setExecutable(true, true)
  }
}
