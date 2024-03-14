package com.crossmint.tonilopezmr.integration.services

import com.crossmint.tonilopezmr.domain.Color.purple
import com.crossmint.tonilopezmr.domain.ComETH
import com.crossmint.tonilopezmr.domain.Direction.up
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import com.crossmint.tonilopezmr.services.MegaverseAPIService
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MegaverseAPIServiceIntegrationTest {

  companion object {
    private val BASE_URL = "https://challenge.crossmint.io/api/"
    private val CANDIDATE_ID = "7e2bdeaa-cb89-41ca-af94-ee60a74b592e"
    private val DEFAULT_POLYANET = POLYanet(0, 0)
    private val DEFAULT_SOLOON = SOLoon(purple, 0, 0)
    private val DEFAULT_COMETH = ComETH(up, 0, 0)
  }

  private val megaverseAPIService: MegaverseAPIService = MegaverseAPIService(BASE_URL)

  @Test
  fun `GET goal should returns the goal Megaverse`() {
    val goal = megaverseAPIService.getGoalMap(CANDIDATE_ID)

    assertNotNull(goal)
  }

  @Test
  fun `POST POLYanet should delete a POLYanet`() {
    val createResponse = megaverseAPIService.createPolyanet(CANDIDATE_ID, DEFAULT_POLYANET)
    assertTrue(createResponse)

    val deleteResponse = megaverseAPIService.deletePolyanet(CANDIDATE_ID, DEFAULT_POLYANET)
    assertTrue(deleteResponse)
  }

  @Test
  fun `POST SOLoons should delete a SOLoons`() {
    val createResponse = megaverseAPIService.createSoloon(CANDIDATE_ID, DEFAULT_SOLOON)
    assertTrue(createResponse)

    val deleteResponse = megaverseAPIService.deleteSoloon(CANDIDATE_ID, DEFAULT_SOLOON)
    assertTrue(deleteResponse)
  }

  @Test
  fun `POST comETHs should delete a comETHs`() {
    val createResponse = megaverseAPIService.createCometh(CANDIDATE_ID, DEFAULT_COMETH)
    assertTrue(createResponse)

    val deleteResponse = megaverseAPIService.deleteCometh(CANDIDATE_ID, DEFAULT_COMETH)
    assertTrue(deleteResponse)
  }
}
