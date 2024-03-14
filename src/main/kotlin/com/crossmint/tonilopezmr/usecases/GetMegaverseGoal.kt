package com.crossmint.tonilopezmr.usecases

import com.crossmint.tonilopezmr.domain.Megaverse
import com.crossmint.tonilopezmr.services.MegaverseAPIService

class GetMegaverseGoal(val apiService: MegaverseAPIService) {

  operator fun invoke(candidateId: String): Megaverse? = apiService.getGoalMap(candidateId)
}
