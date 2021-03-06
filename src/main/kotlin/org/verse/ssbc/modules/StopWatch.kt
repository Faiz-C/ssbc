package org.verse.ssbc.modules

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.*
import org.verse.ssbc.config.Config
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Executors

class StopWatch(
  private val config: Config
) {

  private val dispatcher: CoroutineDispatcher = Executors.newSingleThreadExecutor {
    Thread(it).also { thread ->
      thread.isDaemon = true
    }
  }.asCoroutineDispatcher()

  // Coroutine Stuff
  private val scope: CoroutineScope = CoroutineScope(dispatcher + SupervisorJob())

  // Stopwatch Stuff
  private lateinit var lastTime: Instant
  private var stopWatchJob: Job? = null
  private var elapsedTime: Duration = Duration.ZERO

  var time: String by mutableStateOf(this.zeroState())

  fun start() {

    // If we have started the watch then don't do it again
    this.stopWatchJob?.let {
      return
    }

    this.stopWatchJob = this.scope.launch {
      this@StopWatch.lastTime = Instant.now()

      while (isActive) {
        delay(if (this@StopWatch.config.showMilliseconds) 10L else 1000L) // conditional delay to show millis better if requested

        val now = Instant.now()
        this@StopWatch.elapsedTime += Duration.between(lastTime, now)
        this@StopWatch.lastTime = now
        this@StopWatch.time = this@StopWatch.format(elapsedTime)
      }
    }
  }

  fun stop() {
    // Kill the current job
    this.stopWatchJob?.cancel()
    this.stopWatchJob = null
  }

  fun reset() {

    this.stop()

    // Reset the stopwatch state
    this.time = this.zeroState()
    this.elapsedTime = Duration.ZERO
    this.lastTime = Instant.EPOCH // Just for convention, this will be set properly when the stopwatch starts
  }

  fun isRunning(): Boolean = this.stopWatchJob != null

  private fun format(time: Duration): String {
    val millis = ".%03d".format(time.toMillisPart())

    return "%02d:%02d:%02d".format(
      time.toHoursPart(),
      time.toMinutesPart(),
      time.toSecondsPart()
    ) + if (this.config.showMilliseconds) millis else ""
  }

  private fun zeroState(): String = "00:00:00" + if (this.config.showMilliseconds) ".000" else ""
}