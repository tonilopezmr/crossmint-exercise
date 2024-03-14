package com.crossmint.tonilopezmr.usecases

import com.crossmint.tonilopezmr.AppLogger.logger
import com.crossmint.tonilopezmr.Coroutines
import com.crossmint.tonilopezmr.domain.AstralObject
import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.EmptySpace
import com.crossmint.tonilopezmr.domain.Megaverse
import com.crossmint.tonilopezmr.domain.NoPOLYanetAdjacentForSOLoonCreation
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import com.crossmint.tonilopezmr.services.MegaverseAPIService
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.runBlocking
import java.util.concurrent.atomic.AtomicInteger

class CreateMegaverse(
  val apiService: MegaverseAPIService,
  val coroutines: Coroutines = Coroutines()
) {

  /**
   * Create Megaverse only creating the important Astral Objects.
   *
   * They are created in batches of 1 per second by default.
   */
  operator fun invoke(candidateId: String, megaverse: Megaverse) = runBlocking {
    val requestCounter = AtomicInteger(0)

    megaverse.astralObjects.chunked(coroutines.batchSize).forEach { batch ->
      batch.map { astralObject ->
        coroutines.async {
          create(candidateId, megaverse, astralObject)
        }
      }.awaitAll()

      requestCounter.addAndGet(batch.size)

      logger.info("Creating megaverse...")
      coroutines.delay()
    }

    logger.info("Total Astral Objects Created: $requestCounter")
  }

  private fun create(candidateId: String, megaverse: Megaverse, astralObject: AstralObject) = when (astralObject) {
    is ComETH -> apiService.createCometh(candidateId, astralObject)
    is POLYanet -> apiService.createPolyanet(candidateId, astralObject)
    is SOLoon -> create(candidateId, megaverse, astralObject)
    is EmptySpace -> false
  }

  private fun create(candidateId: String, megaverse: Megaverse, soLoon: SOLoon): Boolean {
    val hasPOLYanetAdjacent = megaverse.getNeighbours(soLoon).any { it is POLYanet }

    if (!hasPOLYanetAdjacent) throw NoPOLYanetAdjacentForSOLoonCreation(soLoon)

    return apiService.createSoloon(candidateId, soLoon)
  }
}
