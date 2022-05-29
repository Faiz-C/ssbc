package org.verse.ssbc.modules

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList

class TimeSplitCollection(
  private val timeSplitCondition: () -> Boolean,
  private val timeSplitFormat: String = "[%d Characters Played] -- %s"
) {

  private val timeStamps: MutableList<String> = mutableListOf()

  val timeSplits: SnapshotStateList<String> = mutableStateListOf()
  var newSplit: Boolean = false

  fun add(timeStamp: String) {
    this.timeStamps.add(timeStamp)
    this.newSplit = this.timeSplitCondition()

    if (this.newSplit) {
      this.timeSplits.add(this.timeSplitFormat.format(this.timeStamps.size, timeStamp))
    }
  }

  fun clear() {
    this.timeStamps.clear()
    this.timeSplits.clear()
  }

}
