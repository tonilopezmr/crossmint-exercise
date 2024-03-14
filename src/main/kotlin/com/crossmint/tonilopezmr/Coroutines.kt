package com.crossmint.tonilopezmr

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlin.coroutines.CoroutineContext

class Coroutines(
  private val scope: CoroutineScope = CoroutineScope(Dispatchers.IO),
  private val context: CoroutineContext = Dispatchers.Default,
  val batchSize: Int = 1,
  val delayInSeconds: Long = 1
) {

  fun async(func: suspend CoroutineScope.() -> Unit) = scope.async(context) {
    func()
  }

  suspend fun delay() {
    kotlinx.coroutines.delay(delayInSeconds * 1000)
  }
}
