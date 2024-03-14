package com.crossmint.tonilopezmr

import com.crossmint.tonilopezmr.domain.Color.PURPLE
import com.crossmint.tonilopezmr.domain.EmptySpace
import com.crossmint.tonilopezmr.domain.Megaverse
import com.crossmint.tonilopezmr.domain.POLYanet
import com.crossmint.tonilopezmr.domain.SOLoon
import com.crossmint.tonilopezmr.services.MegaverseAPIService
import com.crossmint.tonilopezmr.usecases.CreateMegaverseTest.Companion.CANDIDATE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.kotlin.any
import org.mockito.kotlin.eq
import org.mockito.kotlin.given
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

class TonilopezmrMegaverseAppTest {

  private val testCoroutines = Coroutines(CoroutineScope(Dispatchers.IO), Dispatchers.IO, Int.MAX_VALUE, 0)
  private val apiService: MegaverseAPIService = Mockito.mock(MegaverseAPIService::class.java)

  @Test
  fun `Create Megaverse from Goal App`() {
    val megaverse = Megaverse(
      arrayOf(
        arrayOf(EmptySpace(0, 0), EmptySpace(0, 1), POLYanet(0, 2)),
        arrayOf(EmptySpace(1, 0), POLYanet(1, 1), EmptySpace(1, 2)),
        arrayOf(SOLoon(PURPLE, 2, 0), EmptySpace(2, 1), EmptySpace(2, 2))
      )
    )
    given(apiService.getGoalMap(CANDIDATE)).willReturn(megaverse)
    given(apiService.createPolyanet(eq(CANDIDATE), any())).willReturn(true)
    given(apiService.createSoloon(eq(CANDIDATE), any())).willReturn(true)
    given(apiService.createCometh(eq(CANDIDATE), any())).willReturn(true)

    val app = TonilopezmrMegaverseApp(CANDIDATE, apiService, testCoroutines)
    app.execute()

    verify(apiService, times(1)).getGoalMap(CANDIDATE)
    verify(apiService, times(2)).createPolyanet(eq(CANDIDATE), any())
    verify(apiService, times(1)).createSoloon(eq(CANDIDATE), any())
    verify(apiService, times(0)).createCometh(eq(CANDIDATE), any())
  }
}
