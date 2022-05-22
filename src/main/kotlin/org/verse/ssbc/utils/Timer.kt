package org.verse.ssbc.utils

import kotlinx.coroutines.*
import java.io.Closeable
import java.time.Duration
import java.time.Instant
import java.util.concurrent.Executors
import java.util.concurrent.atomic.AtomicReference

class Timer: Closeable {

  companion object {
    private val dispatcher: CoroutineDispatcher = Executors.newSingleThreadExecutor {
      val thread = Thread(it)
      thread.isDaemon = true
      thread
    }.asCoroutineDispatcher()
    private val scope: CoroutineScope = CoroutineScope(dispatcher + SupervisorJob())
  }

  private var elapsedTime: AtomicReference<Duration> = AtomicReference<Duration>(Duration.ZERO)
  private var timerJob: Job? = null

  fun time(): String {
    val duration = this.elapsedTime.get()
    return "%02d:%02d:%02d.%03d".format(
      duration.toHoursPart(),
      duration.toMinutesPart(),
      duration.toSecondsPart(),
      duration.toMillisPart()
    )
  }

  fun start() {
    this.timerJob = scope.launch {
      var lastCheckTime: Instant? = Instant.now()
      while (isActive) {
        val currentTime = Instant.now()
        elapsedTime.set(elapsedTime.get().plus(Duration.between(lastCheckTime, currentTime)))
        lastCheckTime = currentTime
      }
    }
  }

  fun stop() {
    this.timerJob?.cancel()
  }

  override fun close() {
    if (dispatcher.isActive) {
      dispatcher.cancel()
    }

    if (scope.isActive) {
      scope.cancel()
    }
  }


}