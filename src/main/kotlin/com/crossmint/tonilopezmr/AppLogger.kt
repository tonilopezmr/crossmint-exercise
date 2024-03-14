package com.crossmint.tonilopezmr

import java.util.logging.Level
import java.util.logging.Logger

object AppLogger {
  val logger: Logger = Logger.getLogger("tonilopezmr-logs")
}

fun Logger.severe(message: String?, e: Exception) {
  log(Level.SEVERE, message, e)
}
