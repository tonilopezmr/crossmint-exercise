package com.crossmint.tonilopezmr

import com.crossmint.tonilopezmr.AppLogger.logger
import com.crossmint.tonilopezmr.domain.NoGoalFound
import com.crossmint.tonilopezmr.services.MegaverseAPIService
import com.crossmint.tonilopezmr.usecases.CreateMegaverse
import com.crossmint.tonilopezmr.usecases.GetMegaverseGoal

class TonilopezmrMegaverseApp(
  private val candidateId: String = TONILOPEZMR_CANDIDATE_ID,
  megaverseAPIService: MegaverseAPIService = MegaverseAPIService(MEGAVERSE_API),
  coroutines: Coroutines = Coroutines()
) {

  private val createMegaverse = CreateMegaverse(megaverseAPIService, coroutines)
  private val getMegaverseGoal = GetMegaverseGoal(megaverseAPIService)

  fun execute() = try {
    val goal = getMegaverseGoal(candidateId) ?: throw NoGoalFound
    createMegaverse(candidateId, goal)
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
