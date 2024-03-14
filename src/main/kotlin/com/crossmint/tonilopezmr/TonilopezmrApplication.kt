package com.crossmint.tonilopezmr

import com.crossmint.tonilopezmr.AppLogger.logger
import com.crossmint.tonilopezmr.domain.NoGoalFound
import com.crossmint.tonilopezmr.services.MegaverseAPIService
import com.crossmint.tonilopezmr.usecases.CreateMegaverse
import com.crossmint.tonilopezmr.usecases.GetMegaverseGoal

class TonilopezmrMegaverseApp {

  private val megaverseAPIService = MegaverseAPIService(MEGAVERSE_API)
  private val createMegaverse = CreateMegaverse(megaverseAPIService)
  private val getMegaverseGoal = GetMegaverseGoal(megaverseAPIService)

  fun execute() = try {
    val goal = getMegaverseGoal(TONILOPEZMR_CANDIDATE_ID) ?: throw NoGoalFound
    createMegaverse(TONILOPEZMR_CANDIDATE_ID, goal)
  } catch (ex: Exception) {
    logger.severe("ERROR: ${ex.javaClass}: ${ex.message}")
  }

  companion object {
    const val MEGAVERSE_API = "https://challenge.crossmint.io/api/"
    const val TONILOPEZMR_CANDIDATE_ID = "7e2bdeaa-cb89-41ca-af94-ee60a74b592e"
  }
}

fun main(args: Array<String>) {
  logger.info("\n*** Welcome to tonilopezmr.com Megaverse ***\n\n")

  val startTime = System.currentTimeMillis()

  TonilopezmrMegaverseApp().execute()

  val endTime = System.currentTimeMillis()
  val executionTimeSeconds = (endTime - startTime) / 1000.0

  logger.info("Created goal megaverse, execution time: $executionTimeSeconds seconds")
}
